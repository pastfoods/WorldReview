package com.example.WorldReview.dto;

import java.time.LocalDateTime;
import lombok.Setter;
import lombok.ToString;
import lombok.Getter;


@Getter
@Setter
@ToString
public class CountryReviewDTO {

	private int id;
	private char country;
	private int user_id;
	private String title;
	private String content;
	private int rating;
	private LocalDateTime created;

}