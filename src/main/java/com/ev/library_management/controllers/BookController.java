package com.ev.library_management.controllers;

import com.ev.library_management.models.BookRequest;
import com.ev.library_management.models.TagsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ev.library_management.services.BookService;

import java.util.Collections;
import java.util.Set;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/store")
    public String store(@RequestBody BookRequest bookRequest) {
        System.out.println("bookRequest::"+bookRequest.getIsbn());
        return bookService.store(bookRequest.getIsbn(), bookRequest.getTags());
    }

    @PostMapping("/searchByTag")
    public Set<String> searchByTag(@RequestBody TagsRequest tagsReq) {

        System.out.println("tags::"+tagsReq.getTags());
        try{

            return bookService.searchByTag(tagsReq.getTags());

        }
        catch (Exception e){
            //return exception nr and message
            System.out.println("Error: "+e.getMessage());
            return Collections.singleton(e.getMessage());

        }

    }



}
