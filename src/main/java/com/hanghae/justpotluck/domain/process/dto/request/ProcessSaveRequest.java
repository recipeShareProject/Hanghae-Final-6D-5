package com.hanghae.justpotluck.domain.process.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSaveRequest {

    private Long boardId;
    private String process;
    private List<MultipartFile> processImages;

}
