package com.example.sparta.hanghaefinal.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends Result{
    private T data;
}
