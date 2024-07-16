package com.ev.library_management.controllers;
import com.ev.library_management.models.requests.BookRequest;
import com.ev.library_management.models.requests.TagsRequest;
import com.ev.library_management.services.BookService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; //Edhe map edhe result
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;


    private final BookRequest validBookRequest = new BookRequest("2222233222222", new HashSet<>(Arrays.asList("Science", "Fiction")));
    private final BookRequest invalidIsbnRequest = new BookRequest("1234567890", new HashSet<>(Arrays.asList("Science")));
    private final BookRequest emptyTagsRequest = new BookRequest("3333444455556", new HashSet<>());
    private final BookRequest nullTagsRequest = new BookRequest("4444555566667", null);



    private final TagsRequest validTags = new TagsRequest( new HashSet<>(Arrays.asList("Science")));
    private final TagsRequest validTagsSF = new TagsRequest(new HashSet<>(Arrays.asList("Science", "Fiction")));
    private final TagsRequest emptyTags = new TagsRequest(new HashSet<>());
    private final TagsRequest nullTags = new TagsRequest(null);

    @Test
    public void testStoreValidBook() throws Exception {
        //Mocking calling the method  bookService.store(bookRequest.getIsbn(), bookRequest.getTags())
        when(bookService.store(validBookRequest.getIsbn(), validBookRequest.getTags())).thenReturn("OK");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\": \"2222233222222\", \"tags\": [\"Science\", \"Fiction\"]}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    @Test
    public void testStoreInvalidIsbn() throws Exception {
        when(bookService.store(invalidIsbnRequest.getIsbn(), invalidIsbnRequest.getTags())).thenReturn("Error: ISBN number is in the wrong format.");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\": \"1234567890\", \"tags\": [\"Science\"]}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Error: ISBN number is in the wrong format."));
    }


    @Test
    public void testStoreEmptyTags() throws Exception {
        when(bookService.store(emptyTagsRequest.getIsbn(), emptyTagsRequest.getTags())).thenReturn("Error: No tags provided.");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\": \"3333444455556\", \"tags\": []}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Error: No tags provided."));
    }

    @Test
    public void testStoreNullTags() throws Exception {
        when(bookService.store(nullTagsRequest.getIsbn(), nullTagsRequest.getTags())).thenReturn("Error: No tags provided.");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\": \"4444555566667\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Error: No tags provided."));
    }


    @Test
    public void testSearchByValidTags() throws Exception {

        Set<String> expectedISBNs =  new HashSet<>();
        expectedISBNs.add("1111111111111");
        expectedISBNs.add("2222200222222");

        when(bookService.searchByTag(validTags.getTags())).thenReturn(expectedISBNs);

        mockMvc.perform(MockMvcRequestBuilders.post("/searchByTag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tags\": [\"Science\"]}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0]").value("1111111111111"))
                .andExpect(jsonPath("$.[1]").value("2222200222222"));




    }
    @Test
    public void testSearchByValidTagsSF() throws Exception {

        Set<String> expectedISBNs =  new HashSet<>();
        expectedISBNs.add("1111111111111");
        expectedISBNs.add("2222200222222");
        expectedISBNs.add("4444444444444");

        when(bookService.searchByTag(validTagsSF.getTags())).thenReturn(expectedISBNs);


        mockMvc.perform(MockMvcRequestBuilders.post("/searchByTag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tags\": [\"Science\", \"Fiction\"]}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[0]").value("1111111111111"))
                .andExpect(jsonPath("$.[1]").value("2222200222222"))
                .andExpect(jsonPath("$.[2]").value("4444444444444"));
    }



    @Test
    public void testSearchByEmptyTags() throws Exception {
        when(bookService.searchByTag(emptyTags.getTags())).thenReturn(Collections.emptySet());

        mockMvc.perform(MockMvcRequestBuilders.post("/searchByTag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tags\": []}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }


    @Test
    public void testSearchByNullTags() throws Exception {
        when(bookService.searchByTag(nullTags.getTags())).thenReturn(Collections.emptySet());

        mockMvc.perform(MockMvcRequestBuilders.post("/searchByTag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }




}
