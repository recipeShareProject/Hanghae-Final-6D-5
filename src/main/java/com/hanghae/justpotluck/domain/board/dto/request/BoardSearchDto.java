package com.hanghae.justpotluck.domain.board.dto.request;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchDto {
    private String order;
    private String category;
    private ArrayList<String> include;
    private ArrayList<String> exclude;
}