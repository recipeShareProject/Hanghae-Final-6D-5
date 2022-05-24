package com.example.sparta.hanghaefinal.domain.service.board;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.sparta.hanghaefinal.aws.S3Uploader;
import com.example.sparta.hanghaefinal.domain.dto.board.request.BoardRequestDto;
import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.board.Image;
import com.example.sparta.hanghaefinal.domain.entity.board.RecipeProcess;
import com.example.sparta.hanghaefinal.domain.repository.board.ImageRepository;
import com.example.sparta.hanghaefinal.domain.repository.board.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProcessService {

    private final ProcessRepository processRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void saveRecipe(BoardRequestDto requestDto, Board board){
        List<String> processes = requestDto.getProcess();
        List<MultipartFile> images = requestDto.getImages();
                
        for (int i = 0; i < processes.size(); i++)
        {
            List<String> imagesUrl = uploadBoardImages(images, board);
//            이미지 업로드후 url가져오기
            RecipeProcess process = RecipeProcess.builder()
                    .process(processes.get(i))
//                    .image(imagesUrl)
                    .board(board)
                    .processNumber(i+1)
                    .build();

            processRepository.save(process);
        }
    }


    @Transactional
    public void modifyRecipe(Board board, List<String> processes, List<String> images){
        deleteRecipe(board.getId());
//        이미지 업로드 함수 구현 후 마무리
        saveRecipe(board, processes, images);
    }

    @Transactional
    public void deleteRecipe(Long boardId) {processRepository.deleteAllById(boardId);}


    private List<String> uploadBoardImages(List<MultipartFile> images, Board board) {
        return images.stream()
                .map(image -> s3Uploader.upload(image, "board"))
                .map(url -> saveBoardImage(board, url))
                .map(boardImage -> boardImage.getImageUrl())
                .collect(Collectors.toList());
    }

    private Image saveBoardImage(Board board, String url) {
        return ImageRepository.save(Image.builder()
                .imageUrl(url)
                .storeFileName(StringUtils.getFilename(url))
                .board(board)
                .build());
    }
}
