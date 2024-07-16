
package com.ev.library_management.services;

import com.ev.library_management.models.Book;
import com.ev.library_management.models.Tag;
import com.ev.library_management.repositories.BookRepository;
import com.ev.library_management.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStoreNewBook() {
        String isbn = "2041232349872";
        Set<String> tags = new HashSet<>();
        tags.add("Science");

        when(tagRepository.findByTagName(anyString())).thenReturn(null);

        // Calling the service method
        String result = bookService.store(isbn, tags);

        // Verifying repository interactions
        verify(tagRepository, times(1)).findByTagName("Science");
        verify(tagRepository, times(1)).save(any(Tag.class));
        verify(bookRepository, times(1)).save(any(Book.class));

        assertEquals("OK", result);
    }

    @Test
    public void testStoreExistingBook() {
        String isbn = "2222222222222";
        Set<String> tags = new HashSet<>();
        tags.add("Science");

        //Mock tag repository response
        Tag existingTag = new Tag();

        existingTag.setTagName("Science");
        when(tagRepository.findByTagName(anyString())).thenReturn(existingTag);

        // Mock book repository response
        Book existingBook = new Book();
        existingBook.setIsbn(isbn);

        when(bookRepository.findById(isbn)).thenReturn(java.util.Optional.of(existingBook));

        // Calling the service method
        String result = bookService.store(isbn, tags);

        // Verifying repository interactions
        verify(tagRepository, times(1)).findByTagName("Science");
        assertEquals("OK", result);

    }

    @Test
    public void testSearchByTag() {
        Set<String> tags = new HashSet<>();
        tags.add("Science");

        List<String> isbnList = Arrays.asList("1111111111111", "2222222222222");

        // Mock book repository response
        when(bookRepository.findByTags(tags, tags.size())).thenReturn(isbnList);

        // Call the service method
        Set<String> result = bookService.searchByTag(tags);

        // Verifying repository interactions
        verify(bookRepository, times(1)).findByTags(tags, tags.size());

        assertEquals(new HashSet<>(isbnList), result);
    }

    @Test
    public void testStoreNullIsbn() {
        String result = bookService.store(null, new HashSet<>(Arrays.asList("Science")));
        assertEquals("Error: ISBN is mandatory.", result);
    }

    @Test
    public void testStoreEmptyIsbn() {
        String result = bookService.store("", new HashSet<>(Arrays.asList("Science")));
        assertEquals("Error: ISBN is mandatory.", result);
    }

    @Test
    public void testStoreInvalidIsbn() {
        String result = bookService.store("invalid_isbn", new HashSet<>(Arrays.asList("Science")));
        assertEquals("Error: ISBN number is in the wrong format.", result);
    }

    @Test
    public void testStoreNullTags() {
        String result = bookService.store("2041232349872", null);
        assertEquals("Error: No tags provided.", result);
    }

    @Test
    public void testStoreEmptyTags() {
        String result = bookService.store("2041232349872", new HashSet<>());
        assertEquals("Error: No tags provided.", result);
    }

    @Test
    public void testSearchByTagNullTags() {
        Set<String> result = bookService.searchByTag(null);
        assertTrue(result.contains("ERROR IN SEARCHING BY TAGS"));
    }

    @Test
    public void testSearchByTagEmptyTags() {
        Set<String> result = bookService.searchByTag(new HashSet<>());

        assertTrue(result.contains("Error: No tags provided."));
    }


}

