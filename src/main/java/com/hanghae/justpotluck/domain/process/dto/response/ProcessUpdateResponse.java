package com.hanghae.justpotluck.domain.process.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessUpdateResponse {
    private Long processId;
    private List<String> saveImages = new ArrayList<>();
}
