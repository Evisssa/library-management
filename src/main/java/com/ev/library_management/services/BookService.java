package com.ev.library_management.services;

import com.ev.library_management.models.Book;
import com.ev.library_management.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    //calls a method in the service to process the request.
    @Autowired
    private BookRepository bookRepository;

    public String store(String isbn, Set<String> tags) {

            if(isbn == null){
                return "Error in : ISBN is mandatory.";
            }

            if (isbn.length() != 13 || !isbn.matches("\\d{13}")) {
                return "Error: ISBN number is in wrong format.";
            }

            if (tags == null || tags.isEmpty()) {
                return "Error: No tags provided.";
            }
            bookRepository.save(new Book(isbn, tags));
            return "Ok";




    }


    public Set<String> searchByTag(Set<String> tags) {

        List<String> isbn = bookRepository.findByTags(tags, tags.size());

        System.out.println(isbn);

        return isbn.stream().collect(Collectors.toSet());
                /*
                books.stream()
                .filter(book -> book.getTags().containsAll(tags))
                .map(Book::getIsbn)
                .collect(Collectors.toSet());

                 */
    }
}
