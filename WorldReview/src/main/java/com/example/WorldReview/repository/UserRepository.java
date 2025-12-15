package com.example.WorldReview.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.WorldReview.dto.UserDTO;
@Repository
public class UserRepository {

    private final SqlSessionTemplate sql;

    public UserRepository(SqlSessionTemplate sql) {
        this.sql = sql;
    }

    // 아이디 중복 확인
    public boolean checkByUsername(String username) {
        Integer count = sql.selectOne("User.checkByUsername", username);
        return count != null && count > 0;
    }

    // 회원 저장
    public void save(UserDTO userDTO) {
        sql.insert("User.save", userDTO);
    }

    // username으로 회원 조회
    public UserDTO findByUsername(String username) {
        return sql.selectOne("User.findByUsername", username);
    }
}
