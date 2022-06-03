package com.hanghae.justpotluck.domain.process.repository;

import com.hanghae.justpotluck.domain.process.entity.ProcessImage;

import java.util.List;

public interface ProcessImageRepositoryCustom {
    List<ProcessImage> findBySavedImageUrl(Long processId);
}
