package com.hanghae.justpotluck.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateResponse {
    private Long postId;
    private List<String> saveImages = new ArrayList<>();
}
