package com.ev.library_management.models;


import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @NotNull
    private String isbn;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "book_tags", joinColumns = @JoinColumn(name = "book_isbn"))
    @Column(name = "tag")
    private Set<String> tags;


}
