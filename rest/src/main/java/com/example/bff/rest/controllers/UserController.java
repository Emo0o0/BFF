package com.example.bff.rest.controllers;

import com.example.bff.api.inputoutput.cart.addItemToCart.AddItemToCartInput;
import com.example.bff.api.inputoutput.cart.addItemToCart.AddItemToCartOutput;
import com.example.bff.api.inputoutput.cart.addItemToCart.CartAddItemOperation;
import com.example.bff.api.inputoutput.cart.changeItemQuantity.CartChangeItemQuantityOperation;
import com.example.bff.api.inputoutput.cart.changeItemQuantity.ChangeItemQuantityInput;
import com.example.bff.api.inputoutput.cart.changeItemQuantity.ChangeItemQuantityOutput;
import com.example.bff.api.inputoutput.cart.dropCart.CartDropOperation;
import com.example.bff.api.inputoutput.cart.dropCart.DropCartInput;
import com.example.bff.api.inputoutput.cart.dropCart.DropCartOutput;
import com.example.bff.api.inputoutput.cart.getAllItemsFromCart.CartGetAllItemsOperation;
import com.example.bff.api.inputoutput.cart.getAllItemsFromCart.GetAllItemsFromCartInput;
import com.example.bff.api.inputoutput.cart.getAllItemsFromCart.GetAllItemsFromCartListOutput;
import com.example.bff.api.inputoutput.cart.purchase.CartPurchaseOperation;
import com.example.bff.api.inputoutput.cart.purchase.PurchaseInput;
import com.example.bff.api.inputoutput.cart.purchase.PurchaseOutput;
import com.example.bff.api.inputoutput.cart.removeItemFromCart.CartRemoveItemOperation;
import com.example.bff.api.inputoutput.cart.removeItemFromCart.RemoveItemFromCartInput;
import com.example.bff.api.inputoutput.cart.removeItemFromCart.RemoveItemFromCartOutput;
import com.example.bff.api.inputoutput.cart.returnitems.ReturnListInput;
import com.example.bff.api.inputoutput.cart.returnitems.ReturnItemsOperation;
import com.example.bff.api.inputoutput.cart.returnitems.ReturnItemsOutput;
import com.example.bff.api.inputoutput.user.changePassword.ChangeUserPasswordInput;
import com.example.bff.api.inputoutput.user.changePassword.ChangeUserPasswordOutput;
import com.example.bff.api.inputoutput.user.changePassword.UserChangePasswordOperation;
import com.example.bff.api.inputoutput.user.editUser.EditUserPropertiesInput;
import com.example.bff.api.inputoutput.user.editUser.EditUserPropertiesOutput;
import com.example.bff.api.inputoutput.user.editUser.UserEditPropertiesOperation;
import com.example.bff.api.inputoutput.user.loginUser.LoginUserInput;
import com.example.bff.api.inputoutput.user.loginUser.LoginUserOutput;
import com.example.bff.api.inputoutput.user.loginUser.UserLoginOperation;
import com.example.bff.api.inputoutput.user.registerUser.RegisterUserInput;
import com.example.bff.api.inputoutput.user.registerUser.RegisterUserOutput;
import com.example.bff.api.inputoutput.user.registerUser.UserRegisterOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRegisterOperation userRegisterOperation;
    private final UserEditPropertiesOperation userEditPropertiesOperation;
    private final UserLoginOperation loginOperation;
    private final UserChangePasswordOperation userChangePasswordOperation;
    private final CartAddItemOperation cartAddItemOperation;
    private final CartChangeItemQuantityOperation cartChangeItemQuantityOperation;
    private final CartDropOperation cartDropOperation;
    private final CartRemoveItemOperation cartRemoveItemOperation;
    private final CartGetAllItemsOperation cartGetAllItemsOperation;
    private final CartPurchaseOperation cartPurchaseOperation;
    private final ReturnItemsOperation returnItemsOperation;

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterUserOutput> registerUser(@Valid @RequestBody RegisterUserInput input) {
        return ResponseEntity.status(201).body(userRegisterOperation.process(input));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginUserOutput> loginUser(@RequestBody @Valid LoginUserInput input) {
        LoginUserOutput result = loginOperation.process(input);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", result.getJwt());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PatchMapping(path = "/edit")
    public ResponseEntity<EditUserPropertiesOutput> editUser(@RequestBody EditUserPropertiesInput input) {
        return ResponseEntity.status(200).body(userEditPropertiesOperation.process(input));
    }

    @PatchMapping(path = "/newPassword")
    public ResponseEntity<ChangeUserPasswordOutput> changePassword(@RequestBody ChangeUserPasswordInput input) {
        return ResponseEntity.status(200).body(userChangePasswordOperation.process(input));
    }

    @GetMapping
    public String hello() {
        return "Hello user";
    }

    @PatchMapping(path = "/addItem")
    public ResponseEntity<AddItemToCartOutput> addItemToCart(@Valid @RequestBody AddItemToCartInput input) {
        return ResponseEntity.status(200).body(cartAddItemOperation.process(input));
    }

    @PatchMapping(path = "/changeQuantity")
    public ResponseEntity<ChangeItemQuantityOutput> changeItemQuantity(@Valid @RequestBody ChangeItemQuantityInput input) {
        return ResponseEntity.status(200).body(cartChangeItemQuantityOperation.process(input));
    }

    @DeleteMapping(path = "/deleteCart")
    public ResponseEntity<DropCartOutput> dropCart() {
        DropCartInput input = DropCartInput.builder().build();
        return ResponseEntity.status(200).body(cartDropOperation.process(input));
    }

    @DeleteMapping(path = "/deleteItem")
    public ResponseEntity<RemoveItemFromCartOutput> removeItemFromCart(@RequestBody RemoveItemFromCartInput input) {
        return ResponseEntity.status(200).body(cartRemoveItemOperation.process(input));
    }

    @GetMapping(path = "/cart")
    public ResponseEntity<GetAllItemsFromCartListOutput> getCart() {
        GetAllItemsFromCartInput input = GetAllItemsFromCartInput.builder().build();
        return ResponseEntity.status(200).body(cartGetAllItemsOperation.process(input));
    }

    @PostMapping(path = "/purchase")
    public ResponseEntity<PurchaseOutput> purchase(@RequestBody PurchaseInput input) {
        return ResponseEntity.status(200).body(cartPurchaseOperation.process(input));
    }

    @PostMapping(path = "/return")
    public ResponseEntity<ReturnItemsOutput> returnItems(@RequestBody ReturnListInput input) {
        return ResponseEntity.status(200).body(returnItemsOperation.process(input));
    }
}
