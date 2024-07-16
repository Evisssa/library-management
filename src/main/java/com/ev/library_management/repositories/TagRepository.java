package com.ev.library_management.repositories;

import com.ev.library_management.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {


    Tag findByTagName(String tagName);
}
