package com.tenco.csr_blog_v1.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("""
            select b from Board b 
            join fetch b.user 
            left join fetch b.replies r 
            left join fetch r.user 
            where b.id = :boardId
            """)
    Optional<Board> findByIdJoinUserAndReplies(@Param("boardId") Integer boardId);
}
