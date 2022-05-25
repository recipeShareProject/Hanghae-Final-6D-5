package com.hanghae.justpotluck.domain.board.dto.response.board;

import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto extends Timestamped {
    private Long id;
    private String title;
//    private String contents;
    private ArrayList<String> processList;
    private String category;
//    private User user;
//    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<String> processImageUrl;
    private List<String> completeImageUrl;

    private List<Review> reviews;
    private String quantity;
    private int viewCount;
//    private List<Bookmark> bookmarkList;

    public BoardResponseDto(Board entity, List<String> processImages, List<String> completeImages) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.processList = entity.getProcess();
//        this.user = entity.getUser();
        this.quantity = entity.getQuantity();
        this.viewCount = entity.getViewCount();
//        this.imageUrl = images;
        this.processImageUrl = processImages;
        this.completeImageUrl = completeImages;
//        this.bookmarkList = entity.getBookmarkList();
        this.reviews = entity.getReviewList();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }
}
