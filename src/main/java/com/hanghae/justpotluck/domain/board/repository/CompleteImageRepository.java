package com.hanghae.justpotluck.domain.board.repository;

import com.hanghae.justpotluck.domain.board.entity.CompleteImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompleteImageRepository extends JpaRepository<CompleteImage, Long>, CompleteImageRepositoryCustom {
}
