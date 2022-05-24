package com.example.sparta.hanghaefinal.domain.dto.board.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardProcessRequest {
    private Long boardId;
    private Integer processId;
    private String process;
    private String images;
}
