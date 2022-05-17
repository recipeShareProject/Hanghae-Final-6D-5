package com.example.sparta.hanghaefinal.domain.service.board;

import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.board.RecipeProcess;
import com.example.sparta.hanghaefinal.domain.repository.board.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProcessService {

    private final ProcessRepository processRepository;

    @Transactional
    public void saveRecipe(Board board, List<String> processes, List<String> images){
        for (int i = 0; i < processes.size(); i++)
        {
//            String image = uploadToAWS(images.get(i));
//            이미지 업로드후 url가져오기
            RecipeProcess process = RecipeProcess.builder()
                    .process(processes.get(i))
                    .image(images.get(i))
                    .board(board)
                    .processNumber(i+1)
                    .build();

            processRepository.save(process);
        }
    }

//    public String uploadToAWS(MultipartFile file) {
//        String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
//        try {
//
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentType(file.getContentType());
//            PutObjectRequest request = new PutObjectRequest(bucketName, key, file.getInputStream(), metadata);
//            request.withCannedAcl(CannedAccessControlList.AuthenticatedRead); // 접근권한 체크
//            PutObjectResult result = s3Client.putObject(request);
//            return key;
//        } catch (AmazonServiceException e) {
//            // The call was transmitted successfully, but Amazon S3 couldn't process
//            // it, so it returned an error response.
//            log.error("uploadToAWS AmazonServiceException filePath={}, yyyymm={}, error={}", e.getMessage());
//        } catch (SdkClientException e) {
//            // Amazon S3 couldn't be contacted for a response, or the client
//            // couldn't parse the response from Amazon S3.
//            log.error("uploadToAWS SdkClientException filePath={}, error={}", e.getMessage());
//        } catch (Exception e) {
//            // Amazon S3 couldn't be contacted for a response, or the client
//            // couldn't parse the response from Amazon S3.
//            log.error("uploadToAWS SdkClientException filePath={}, error={}", e.getMessage());
//        }
//
//        return "";
//    }

    @Transactional
    public void modifyRecipe(Board board, List<String> processes, List<String> images){
        deleteRecipe(board.getId());
        saveRecipe(board, processes, images);
    }

    @Transactional
    public void deleteRecipe(Long boardId) {processRepository.deleteAllById(boardId);}

    //    private List<String> uploadBoardImages(BoardRequestDto requestDto, Board board) {
//        return requestDto.getImages().stream()
//                .map(image -> s3Uploader.upload(image, "board"))
//                .map(url -> saveBoardImage(board, url))
//                .map(boardImage -> boardImage.getImageUrl())
//                .collect(Collectors.toList());
//    }
//
//    private Image saveBoardImage(Board board, String url) {
//        return boardImageRepository.save(Image.builder()
//                .imageUrl(url)
//                .storeFileName(StringUtils.getFilename(url))
//                .board(board)
//                .build());
//    }
}
