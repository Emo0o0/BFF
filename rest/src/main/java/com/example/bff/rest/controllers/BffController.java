package com.example.bff.rest.controllers;

import com.example.bff.api.inputoutput.getItemById.GetItemInput;
import com.example.bff.api.inputoutput.getItemById.GetItemOutput;
import com.example.bff.api.inputoutput.getItemById.ItemGetByIdOperation;
import com.example.bff.api.inputoutput.getItemsByTag.GetItemsByTagInput;
import com.example.bff.api.inputoutput.getItemsByTag.GetItemsByTagListOutput;
import com.example.bff.api.inputoutput.getItemsByTag.ItemsGetByTagOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bff")
public class BffController {

    private final ItemGetByIdOperation itemGetByIdOperation;
    private final ItemsGetByTagOperation itemsGetByTagOperation;

    @GetMapping(path = "/item/{id}")
    public ResponseEntity<GetItemOutput> getItemById(@Valid @PathVariable String id) {
        GetItemInput input = GetItemInput.builder()
                .itemId(id)
                .build();
        return ResponseEntity.status(200).body(itemGetByIdOperation.process(input));
    }

    @GetMapping(path = "/itemsByTag")
    public ResponseEntity<GetItemsByTagListOutput> getItemsByTag(@RequestParam(name = "title") String title,
                                                                 @RequestParam(name = "itemsPerPage") Integer itemsPerPage,
                                                                 @RequestParam(name = "currentPage") Integer currentPage) {
        GetItemsByTagInput input = GetItemsByTagInput.builder()
                .tagTitle(title)
                .itemsPerPage(itemsPerPage)
                .currentPage(currentPage)
                .build();
        return ResponseEntity.status(200).body(itemsGetByTagOperation.process(input));
    }
}
