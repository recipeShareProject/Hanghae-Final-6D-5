package com.hanghae.justpotluck.domain.community.service;

import com.hanghae.justpotluck.domain.community.dto.request.PostRequestDto;
import com.hanghae.justpotluck.domain.community.dto.request.PostThumbnailDto;
import com.hanghae.justpotluck.domain.community.dto.request.PostUpdateDto;
import com.hanghae.justpotluck.domain.community.dto.response.PostResponseDto;
import com.hanghae.justpotluck.domain.community.dto.response.PostSaveReponse;
import com.hanghae.justpotluck.domain.community.dto.response.PostUpdateResponse;
import com.hanghae.justpotluck.domain.community.entity.PostImage;
import com.hanghae.justpotluck.domain.community.entity.Posts;
import com.hanghae.justpotluck.domain.community.repository.PostImageRepository;
import com.hanghae.justpotluck.domain.community.repository.PostRepository;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.global.aws.S3Uploader;
import com.hanghae.justpotluck.global.exception.RestException;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;
    private final UserUtil userUtil;
    private final S3Uploader s3Uploader;

    @Scheduled(cron = "0 0 00 * * *")
    public void updateExpired() {
        List<Posts> posts = postRepository.findAllByExpiredAtBefore(LocalDateTime.now());
        for (Posts post : posts) {
            post.update("나눔완료");
        }
    }

    //    반경 쿼리만 작성하면 완료
    @Transactional
    public List<PostThumbnailDto> findPosts(int pagingCnt, Double longitude, Double latitude) {

        Pageable pageRequest = PageRequest.of(pagingCnt, 10, Sort.by("createdAt").descending());
//        위치반경 내 필터링 하여 게시글 목록 불러오기(확실치 않음)--> 쿼리 에러뜨네;;
        Page<Posts> posts = postRepository.findPostsToUser(longitude, latitude, pageRequest);
//        Page<Posts> posts = postRepository.findAll(pageRequest);
        List<PostThumbnailDto> responseDto = new ArrayList<>();
        for (Posts post : posts) {
            PostThumbnailDto postThumbnailDto = new PostThumbnailDto(post);
            responseDto.add(postThumbnailDto);
        }
        return responseDto;
    }

    @Transactional
    public List<PostResponseDto> getAllPost() {
        List<PostResponseDto> listPost = new ArrayList<>();
        List<Posts> posts = postRepository.findAllByOrderByExpiredAtDesc();

        for (Posts post : posts) {
            List<String> postImages = postImageRepository.findBySavedImageUrl(post.getPostId())
                    .stream()
                    .map(image -> image.getImageUrl())
                    .collect(Collectors.toList());
            listPost.add(new PostResponseDto(post, postImages));
        }
        return listPost;
    }


    @Transactional
    public PostResponseDto findOne(Long postId) {
        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );
        List<String> postImages = post.getImages()
                .stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
        return new PostResponseDto(post, postImages);
    }

    @Transactional
    public PostSaveReponse savePost(PostRequestDto requestDto) {
//        유저 DB 확인 후 수정
        User user = userUtil.findCurrentUser();
        Posts post = postRepository.save(Posts.createPost(requestDto, user));
        List<String> postImages = uploadPostImages(requestDto, post);
        return new PostSaveReponse(requestDto.getPostId(), postImages);
    }

    private List<String> uploadPostImages(PostRequestDto requestDto, Posts post) {
        return requestDto.getImages().stream()
                .map(image -> s3Uploader.upload(image, "posts"))
                .map(postUrl -> savePostImage(post, postUrl))
                .map(postImage -> postImage.getImageUrl())
                .collect(Collectors.toList());
    }


    private PostImage savePostImage(Posts post, String postUrl) {
        return postImageRepository.save(PostImage.builder()
                .imageUrl(postUrl)
                .storeFileName(StringUtils.getFilename(postUrl))
                .posts(post)
                .build());
//        return postImageRepository.save(PostImage.builder()
//                .imageUrl(url)
//                .storeFileName(StringUtils.getFilename(url))
//                .posts(posts)
//                .build());
    }

    @Transactional
    public void delete(Long postId) {
        postRepository.deleteByPostId(postId);
    }

    @Transactional
    public PostUpdateResponse modify(Long postId, PostUpdateDto requestDto) {

        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );
        validateDeletedImages(requestDto);
        uploadPostImages(requestDto, post);
        List<String> saveImages = getSaveImages(requestDto);
        post.update(requestDto);
        return new PostUpdateResponse(post.getPostId(), saveImages);
//        if (post.getUser().getName().equals(username)) {
//            post.update(requestDto);
//        } else {
//            throw new RestException(HttpStatus.BAD_REQUEST, "username이 일치하지 않습니다.");
//        }
    }

    private void uploadPostImages(PostUpdateDto requestDto, Posts post) {
        requestDto.getImages()
                .stream()
                .forEach(file -> {
                    String url = s3Uploader.upload(file, "posts");
                    savePostImage(post, url);
                });
    }

    private List<String> getSaveImages(PostUpdateDto requestDto) {
        return postImageRepository.findBySavedImageUrl(requestDto.getPostId())
                .stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }

    private void validateDeletedImages(PostUpdateDto requestDto) {
        postImageRepository.findBySavedImageUrl(requestDto.getPostId()).stream()
                .filter(image -> !requestDto.getSaveImageUrl().stream().anyMatch(Predicate.isEqual(image.getImageUrl())))
                .forEach(url -> {
                    postImageRepository.delete(url);
                    s3Uploader.deleteImage(url.getImageUrl());
                });
    }

    @Transactional
    public void modify(Long postId, PostUpdateDto requestDto, String username) {
        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );

        if (post.getUser().getName().equals(username)) {
            post.update(requestDto.getCategory());
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, "username이 일치하지 않습니다.");
        }
    }
}
