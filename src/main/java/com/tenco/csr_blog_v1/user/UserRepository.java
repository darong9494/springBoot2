package com.tenco.csr_blog_v1.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 유저 이름으로 회원정보 조회
    @Query("select u from User u where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    // 유저네임 중복 여부 확인
    // count로 사용하면 좀 더 성능이 빠름
    @Query("select count(u) > 0 from User u where u.username = :username")
    boolean existsByUsername(@Param("username") String username);
}


