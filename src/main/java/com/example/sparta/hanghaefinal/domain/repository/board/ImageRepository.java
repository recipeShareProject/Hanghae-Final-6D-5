package com.example.sparta.hanghaefinal.domain.repository.board;

import com.example.sparta.hanghaefinal.domain.entity.board.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
