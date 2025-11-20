package com.example.WorldReview.dto;

import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import lombok.Getter;


@Getter
@Setter
@ToString
public class UserDTO {
	
	private int id;
	private String username;
	private String password;
	private String nickname;
	private LocalDateTime created;

}
