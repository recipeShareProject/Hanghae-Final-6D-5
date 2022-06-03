package com.hanghae.justpotluck.domain.process.repository;

import com.hanghae.justpotluck.domain.process.entity.ProcessImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessImageRepository extends JpaRepository<ProcessImage, Long>, ProcessImageRepositoryCustom {
}
