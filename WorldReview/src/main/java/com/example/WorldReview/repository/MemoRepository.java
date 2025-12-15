package com.example.WorldReview.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.WorldReview.dto.MemoDTO;

@Repository
public class MemoRepository {

    private final SqlSessionTemplate sql;

    public MemoRepository(SqlSessionTemplate sql) {
        this.sql = sql;
    }

    // 특정 유저의 메모 목록
    public List<MemoDTO> findByUserId(int userId) {
        return sql.selectList("Memo.findByUserId", userId);
    }

    // 메모 한 건
    public MemoDTO findById(int id) {
        return sql.selectOne("Memo.findById", id);
    }

    // 메모 저장
    public void save(MemoDTO memo) {
        sql.insert("Memo.save", memo);
    }

    // 메모 수정
    public void update(MemoDTO memo) {
        sql.update("Memo.update", memo);
    }

    // 메모 삭제
    public void delete(int id) {
        sql.delete("Memo.delete", id);
    }
}
