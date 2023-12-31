package com.example.bff.core.services.cart;

import com.example.bff.api.inputoutput.cart.purchase.CartPurchaseOperation;
import com.example.bff.api.inputoutput.cart.purchase.PurchaseInput;
import com.example.bff.api.inputoutput.cart.purchase.PurchaseOutput;
import com.example.bff.core.exceptions.*;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.Coupon;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.CouponRepository;
import com.example.bff.persistence.repositories.UserRepository;
import com.example.zoostorestorage.api.inputoutput.item.getfromstorage.GetItemFromStorageOutput;
import com.example.zoostorestorage.api.inputoutput.order.CreateOrderRecordInput;
import com.example.zoostorestorage.api.inputoutput.order.OrderRecordCreateOperation;
import com.example.zoostorestorage.api.inputoutput.order.OrderRecordItemOutput;
import com.example.zoostorestorage.restexport.ZooStorageRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartPurchaseOperationProcessor implements CartPurchaseOperation {

    private final UserRepository userRepository;
    private final ZooStorageRestClient zooStorageRestClient;
    private final CouponRepository couponRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public PurchaseOutput process(PurchaseInput purchaseInput) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if (user.getCartItems().isEmpty()) {
            throw new NoItemsInCartException("Your cart is empty");
        }

        Double couponDiscount = 0.0d;
        String couponTitle = "";
        if (!purchaseInput.getCoupon().isBlank()) {
            Coupon coupon = couponRepository.findByTitle(purchaseInput.getCoupon())
                    .orElseThrow(() -> new CouponNotFoundException("This coupon does not exist or is expired"));
            couponDiscount = coupon.getDiscount();
            couponTitle = coupon.getTitle();
        }

        checkStorageQuantity(user);

        zooStorageRestClient.createOrderRecord(inputForStorageRecord(user, couponTitle, couponDiscount));


        cartItemRepository.deleteAll(user.getCartItems());
        user.getCartItems().clear();
        userRepository.save(user);

        PurchaseOutput output = PurchaseOutput.builder()
                .success(true)
                .build();

        return output;
    }

    private BigDecimal cartTotalPrice() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        BigDecimal totalPrice = user.getCartItems().stream()
                .map(cartItem -> cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalPrice;
    }

    private Boolean checkStorageQuantity(User user) {

        user.getCartItems().stream().forEach(item -> {
            GetItemFromStorageOutput storageItem;
            try {
                storageItem = zooStorageRestClient.getItemFromStorage(item.getItemId());
            } catch (Exception e) {
                throw new ItemNotFoundException("Item was not found in storage");
            }

            if (item.getQuantity() > Integer.parseInt(storageItem.getQuantity())) {
                throw new NotEnoughQuantityException("Not enough quantity of item with id: " + item.getItemId());
            }
        });
        return true;
    }

    private CreateOrderRecordInput inputForStorageRecord(User user, String couponTitle, Double couponDiscount) {

        return CreateOrderRecordInput.builder()
                .userId(user.getId().toString())
                .items(user.getCartItems().stream()
                        .map(o -> OrderRecordItemOutput.builder()
                                .itemId(o.getItemId())
                                .quantity(String.valueOf(o.getQuantity()))
                                .price(o.getPrice().toString())
                                .build())
                        .collect(Collectors.toList()))
                .coupon(couponTitle)
                .discount(String.valueOf(cartTotalPrice().multiply(BigDecimal.valueOf(couponDiscount / 100))))
                .totalPrice(cartTotalPrice().multiply(BigDecimal.valueOf(1 - (couponDiscount / 100))).toString())
                .build();
    }
}
