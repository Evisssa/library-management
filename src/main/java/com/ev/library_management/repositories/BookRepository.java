package com.ev.library_management.repositories;

import com.ev.library_management.models.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface BookRepository extends JpaRepository<Book,String> {


    @Query(value="SELECT b " +
            "FROM book b " +
            "JOIN book_tags bt ON b.isbn = bt.book_isbn " +
            "JOIN tag t ON bt.tag_id = t.tag_id " +
            "WHERE t.tag_name IN :tags " +
            "GROUP BY b.isbn " +
            "HAVING COUNT(DISTINCT t.tag_name) = :tagCount "+
            "ORDER BY b",nativeQuery = true)
    List<String> findByTags(Set<String> tags, long tagCount);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM book_tags bt " +
            "WHERE bt.book_isbn = :isbn", nativeQuery = true)
    void deleteBookTagsByIsbn(String isbn);
}
