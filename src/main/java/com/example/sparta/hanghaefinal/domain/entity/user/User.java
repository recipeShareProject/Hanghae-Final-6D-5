package com.example.sparta.hanghaefinal.domain.entity.user;


import com.example.sparta.hanghaefinal.audit.AuditListener;
import com.example.sparta.hanghaefinal.audit.Auditable;
import com.example.sparta.hanghaefinal.audit.TimeEntity;
import com.example.sparta.hanghaefinal.domain.entity.comment.Comments;
import com.example.sparta.hanghaefinal.domain.entity.community.Posts;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Setter
@Getter
@Entity
@EntityListeners(AuditListener.class)
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
public class User implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String imageUrl;

    private Double longitude;

    private Double latitude;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @Embedded
    private TimeEntity timeEntity;

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<Posts> postList;

    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<Comments> commentList;

    @Builder(builderClassName= "social", builderMethodName = "socialBuilder")
    private User(String name, @Email String email, String imageUrl, @NotNull AuthProvider provider, String providerId) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.providerId = providerId;
    }

    @Builder(builderClassName = "local",builderMethodName = "localBuilder")
    public User(String name, @Email String email, String imageUrl, String password, @NotNull AuthProvider provider, String providerId) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void updateNameAndImage(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void addComment(Comments comment){
        this.commentList.add(comment);
    }

    public void addPost(Posts post) {this.postList.add(post);}
}