package com.tenco.csr_blog_v1.board;

import com.tenco.csr_blog_v1.reply.Reply;
import com.tenco.csr_blog_v1.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "board_tb")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 500, nullable = false)
    private String Content;

    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id")  <-- 기본값으로 설정 됨
    private User user;

    // 양방향 맵핑
    // mappedBy = "board" <--- 내 쪽에서 조인컬럼을 생성하지 마 !!! 즉, 조인 컬럼은 Reply 테이블에 board_id 가 생성되어야 해
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Reply> replies = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdAt;


    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        Content = content;
        this.user = user;
        this.createdAt = createdAt;
    }
}


