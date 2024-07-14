package com.ev.library_management.repositories;

import com.ev.library_management.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagsRepository extends JpaRepository<Tag,Integer> {


    Tag findByTagName(String tagName);
}
