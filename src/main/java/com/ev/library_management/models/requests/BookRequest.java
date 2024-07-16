package com.ev.library_management.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "ISBN is mandatory")
    private String isbn;
    private Set<String> tags;
}
