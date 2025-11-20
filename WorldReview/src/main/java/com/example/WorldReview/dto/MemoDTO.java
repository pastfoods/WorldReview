package com.example.WorldReview.dto;

import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import lombok.Getter;


@Getter
@Setter
@ToString
public class MemoDTO {

	private int id;
	private int user_id;
	private String title;
	private String content;
	private LocalDateTime created;
	private LocalDateTime updated;

}
