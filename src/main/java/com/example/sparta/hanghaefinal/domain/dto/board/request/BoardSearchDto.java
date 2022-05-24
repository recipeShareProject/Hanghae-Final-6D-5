package com.example.sparta.hanghaefinal.domain.dto.board.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchDto {

    private String order;
    private String search;
    private String nation;
    private List<String> include;
    private List<String> exclude;
}
