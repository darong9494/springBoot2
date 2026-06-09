package com.tenco.csr_blog_v1.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user_tb")
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, length = 20, nullable = false)
    private String username;
    @Column(length = 60, nullable = false)
    private String password;
    @Column(length = 30, nullable = false)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    // JPA 가 뒤에서 쿼리를 짜서 데이터베이스에 테이블을 자동으로 만들어 줌(굳이 ROLE 엔티티 선언 불 필요)
    @CollectionTable(name = "user_role_tb", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Builder.Default
    private List<String> roles = new ArrayList<>(List.of("USER"));

    @CreationTimestamp
    private Timestamp createdAt;

    public void update(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void updateRoles(List<String> roles) {
        this.roles = roles;
    }

    // 스프링 시큐리티 권한 처리
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 규칙 접두사 ROLE_ <-- 기반으로 넣어 주어야 한다.
        for(String role : this.roles) {
            authorities.add( () -> "ROLE_"+ role);
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (true 만료 안됨)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 여부 (true 잠기지 않음)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부(true 만료 안됨)
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 (true 활성화 됨)
    }
}
