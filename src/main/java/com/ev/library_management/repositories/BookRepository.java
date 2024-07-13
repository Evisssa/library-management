package com.ev.library_management.repositories;

import com.ev.library_management.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book, String> {


    @Query(value = "SELECT b.isbn FROM book b " +
            "JOIN book_tags bt ON b.isbn = bt.book_isbn " +
            "WHERE bt.tag IN :tags " +
            "GROUP BY b.isbn " +
            "HAVING COUNT(DISTINCT bt.tag) = :tagCount", nativeQuery = true)
    List<String> findByTags(Set<String> tags, long tagCount);
}
