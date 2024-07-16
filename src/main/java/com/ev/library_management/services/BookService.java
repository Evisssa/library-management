package com.ev.library_management.services;

import com.ev.library_management.models.Book;
import com.ev.library_management.models.Tag;
import com.ev.library_management.repositories.BookRepository;
import com.ev.library_management.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TagRepository tagRepository;


    public String store(String isbn, Set<String> tagNames) {

        try{

            if (isbn == null || isbn.isEmpty()) {
                return "Error: ISBN is mandatory.";
            }

            if (isbn.length() != 13 || !isbn.matches("\\d{13}")) {
                return "Error: ISBN number is in the wrong format.";
            }

            if (tagNames == null || tagNames.isEmpty()) {
                return "Error: No tags provided.";


            }

            bookRepository.deleteBookTagsByIsbn(isbn);
            Book book = new Book();
            book.setIsbn(isbn);

            Set<Tag> tags = new HashSet<>();
            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByTagName(tagName);
                if (tag == null) {
                    tag = new Tag();
                    tag.setTagName(tagName);
                    tagRepository.save(tag);
                }
                tags.add(tag);
            }
            book.setTags(tags);
            bookRepository.save(book);

            return "OK";

        }
        catch (Exception e){
            return "ERROR IN STORING! ";

        }






    }


    public Set<String> searchByTag(Set<String> tags) {

        try {
            List<String> isbn = bookRepository.findByTags(tags, tags.size());

            if (tags == null || tags.isEmpty()|| isbn.isEmpty()) {
                    return Collections.singleton("Error: No tags provided.");
                }
                return isbn.stream().collect(Collectors.toSet());
        }
        catch (Exception e){
            return Collections.singleton("ERROR IN SEARCHING BY TAGS");
        }


    }
}
