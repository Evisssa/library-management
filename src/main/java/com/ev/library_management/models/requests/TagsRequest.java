package com.ev.library_management.models.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TagsRequest {

    private Set<String> tags;
}
