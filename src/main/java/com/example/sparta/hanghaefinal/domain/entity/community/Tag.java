package com.example.sparta.hanghaefinal.domain.entity.community;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag;

    @JsonBackReference
    @JoinColumn(name = "postId")
    @ManyToOne
    private Posts posts;
}
