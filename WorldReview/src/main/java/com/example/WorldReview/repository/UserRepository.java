package com.example.WorldReview.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.WorldReview.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final SqlSessionTemplate sql;

    /** 회원가입 (저장) */
    public void save(UserDTO userDTO) {
        sql.insert("User.save", userDTO);   // "User" = XML namespace
    }

    /** 아이디(username)로 회원 1명 조회 (로그인용) */
    public UserDTO findByUsername(String username) {
        return sql.selectOne("User.findByUsername", username);
    }
    
    /** 아이디(username) 중복 여부 확인 */
    public boolean checkByUsername(String username) {
        Integer count = sql.selectOne("User.checkByUsername", username);
        return count != null && count > 0;
    }
}
