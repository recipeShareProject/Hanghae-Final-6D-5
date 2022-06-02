package com.hanghae.justpotluck.domain.process.service;

import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.repository.BoardRepository;
import com.hanghae.justpotluck.domain.process.dto.request.ProcessSaveRequest;
import com.hanghae.justpotluck.domain.process.dto.request.ProcessUpdateRequestDto;
import com.hanghae.justpotluck.domain.process.dto.response.ProcessResponseDto;
import com.hanghae.justpotluck.domain.process.entity.ProcessImage;
import com.hanghae.justpotluck.domain.process.entity.RecipeProcess;
import com.hanghae.justpotluck.domain.process.repository.ProcessImageRepository;
import com.hanghae.justpotluck.domain.process.repository.RecipeProcessRepository;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.global.aws.S3Uploader;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeProcessService {
    private final UserUtil userUtil;
    private final RecipeProcessRepository processRepository;
    private final BoardRepository boardRepository;
    private final S3Uploader s3Uploader;
    private final ProcessImageRepository processImageRepository;

    @Transactional
    public ProcessResponseDto saveProcess(ProcessSaveRequest requestDto, Long boardId) {
        User user = userUtil.findCurrentUser();
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 레시피가 존재하지 않습니다.")
        );
        RecipeProcess process = processRepository.save(RecipeProcess.createProcess(requestDto, board));
        List<String> processImage = uploadProcessImage(requestDto, process);

        return new ProcessResponseDto(process, processImage);

    }

    private List<String> uploadProcessImage(ProcessSaveRequest requestDto, RecipeProcess process) {
        return requestDto.getProcessImages().stream()
                .map(ProcessImage -> s3Uploader.upload(ProcessImage, "process"))
                .map(url -> saveProcessImage(process, url))
                .map(processImage -> processImage.getImageUrl())
                .collect(Collectors.toList());

    }

    private ProcessImage saveProcessImage(RecipeProcess process, String url) {
        return processImageRepository.save(ProcessImage.builder()
                .imageUrl(url)
                .storeFileName(StringUtils.getFilename(url))
                .process(process)
                .build());
    }
    @Transactional
    public ProcessResponseDto updateProcess(Long processId, ProcessUpdateRequestDto requestDto) {
        User user = userUtil.findCurrentUser();
        RecipeProcess process = processRepository.findById(processId).orElseThrow(
                () -> new IllegalArgumentException("해당 조리과정이 없습니다.")
        );
        validateDeletedImages(requestDto);
        uploadProcessImages(requestDto, process);
        List<String> saveImages = getSaveImages(requestDto);
        process.update(requestDto, user);
        return new ProcessResponseDto(process, saveImages);
    }

    private void validateDeletedImages(ProcessUpdateRequestDto requestDto) {
        processImageRepository.findBySavedImageUrl(requestDto.getProcessId()).stream()
                .filter(image -> !requestDto.getSaveImageUrl().stream().anyMatch(Predicate.isEqual(image.getImageUrl())))
                .forEach(url -> {
                    processImageRepository.delete(url);
                    s3Uploader.deleteImage(url.getImageUrl());
                });
    }
    private void uploadProcessImages(ProcessUpdateRequestDto requestDto, RecipeProcess process) {
        requestDto.getProcessImages()
                .stream()
                .forEach(file -> {
                    String url = s3Uploader.upload(file, "process");
                    saveProcessImage(process, url);
                });
    }
    private List<String> getSaveImages(ProcessUpdateRequestDto requestDto) {
        return processImageRepository.findBySavedImageUrl(requestDto.getProcessId())
                .stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteProcess(Long processId) {
        User user = userUtil.findCurrentUser();
        RecipeProcess process = processRepository.findById(processId).orElseThrow(
                () -> new IllegalArgumentException("해당 조리과정이 없습니다.")
        );
        processRepository.delete(process);
    }
}
