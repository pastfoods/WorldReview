package com.example.WorldReview.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ReviewDTO {
    private int id;
    private String country_code;
    private int user_id;
    private String user_nickname;    // JOIN해서 가져오기용
    private double rating;
    private String content;
    private LocalDateTime created;
    private LocalDateTime updated;
}
