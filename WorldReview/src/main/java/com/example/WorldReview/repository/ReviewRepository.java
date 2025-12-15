package com.example.WorldReview.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.WorldReview.dto.ReviewDTO;

@Repository
public class ReviewRepository {

    private final SqlSessionTemplate sql;

    public ReviewRepository(SqlSessionTemplate sql) {
        this.sql = sql;
    }

    public List<ReviewDTO> findByCountry(String countryCode) {
        return sql.selectList("Review.findByCountry", countryCode);
    }

    public Double getAverageRating(String countryCode) {
        return sql.selectOne("Review.getAverageRating", countryCode);
    }

    public ReviewDTO findById(int id) {
        return sql.selectOne("Review.findById", id);
    }

    public void save(ReviewDTO review) {
        sql.insert("Review.save", review);
    }

    public void update(ReviewDTO review) {
        sql.update("Review.update", review);
    }

    public void delete(int id) {
        sql.delete("Review.delete", id);
    }
}
