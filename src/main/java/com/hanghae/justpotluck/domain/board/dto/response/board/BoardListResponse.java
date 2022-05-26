package com.hanghae.justpotluck.domain.board.dto.response.board;


import com.hanghae.justpotluck.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardListResponse {

    private Long id;
    private String title;
    private String cookTime;
    private List<String> images;
    private Boolean isBookmark;
    private int viewCount;

    public BoardListResponse(Board entity, List<String> images) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.cookTime = entity.getCookTime();
        this.images = images;
        this.isBookmark = entity.getIsBookmark();
    }
}
