package com.example.sparta.hanghaefinal.domain.service.community;

import com.example.sparta.hanghaefinal.advice.RestException;
import com.example.sparta.hanghaefinal.domain.dto.community.PostRequestDto;
import com.example.sparta.hanghaefinal.domain.dto.community.PostResponseDto;
import com.example.sparta.hanghaefinal.domain.dto.community.PostThumbnailDto;
import com.example.sparta.hanghaefinal.domain.dto.community.PostUpdateDto;
import com.example.sparta.hanghaefinal.domain.entity.community.Posts;
import com.example.sparta.hanghaefinal.domain.entity.user.User;
import com.example.sparta.hanghaefinal.domain.repository.community.PostRepository;
import com.example.sparta.hanghaefinal.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Scheduled(cron="0 0 00 * * *")
    public void updateExpired(){
        List<Posts> posts = postRepository.findAllByExpiredAtBefore(LocalDateTime.now());
        for (Posts post : posts){
            post.update("나눔완료");
        }
    }

//    반경 쿼리만 작성하면 완료
    @Transactional
    public List<PostThumbnailDto> findPosts(int pagingCnt, Double longitude, Double latitude) {

        Pageable pageRequest = PageRequest.of(pagingCnt, 10, Sort.by("createdAt").descending());
//        위치반경 내 필터링 하여 게시글 목록 불러오기(확실치 않음)--> 쿼리 에러뜨네;;
        Page<Posts> posts = postRepository.findPostsToUser(longitude, latitude);
//        Page<Posts> posts = postRepository.findAll(pageRequest);
        List<PostThumbnailDto> responseDto = new ArrayList<>();
        for (Posts post : posts) {
            PostThumbnailDto postThumbnailDto = new PostThumbnailDto(post);
            responseDto.add(postThumbnailDto);
        }
        return responseDto;
    }


    @Transactional
    public PostResponseDto findOne(Long postId) {
        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public void save(PostRequestDto requestDto, String username) {
//        유저 DB 확인 후 수정
        User result = userRepository.findByName(username).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 username이 존재하지 않습니다.")
        );
        Posts post = Posts.builder()
                .content(requestDto.getContent())
                .image(requestDto.getImagePath())
                .category(requestDto.getCategory())
                .expiredAt(LocalDateTime.parse(requestDto.getExpiredAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
                .title(requestDto.getTitle())
                .build();
        postRepository.save(post);
        result.addPost(post);
    }

    @Transactional
    public void delete(Long postId) {
        postRepository.deleteByPostId(postId);
    }

    @Transactional
    public void modify(Long postId, PostRequestDto requestDto, String username) {

        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );

        if (post.getUser().getName().equals(username)) {
            post.update(requestDto);
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, "username이 일치하지 않습니다.");
        }
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
