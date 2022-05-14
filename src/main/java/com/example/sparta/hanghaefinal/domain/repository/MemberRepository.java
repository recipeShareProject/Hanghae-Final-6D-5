package com.example.sparta.hanghaefinal.domain.repository;

import com.example.sparta.hanghaefinal.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByEmailAndProvider(String email, String provider);
}
