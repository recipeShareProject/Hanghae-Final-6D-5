package com.example.sparta.hanghaefinal.web.controller.login;

import com.example.sparta.hanghaefinal.advice.exception.ResourceNotFoundException;
import com.example.sparta.hanghaefinal.domain.entity.user.User;
import com.example.sparta.hanghaefinal.domain.repository.user.UserRepository;
import com.example.sparta.hanghaefinal.security.CurrentUser;
import com.example.sparta.hanghaefinal.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}