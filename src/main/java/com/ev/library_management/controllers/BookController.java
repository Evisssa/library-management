package com.ev.library_management.controllers;

import com.ev.library_management.models.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ev.library_management.services.BookService;

import java.util.Set;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/store")
    public String store(@RequestBody BookRequest bookRequest) {


        return bookService.store(bookRequest.getIsbn(), bookRequest.getTags());


    }

}
