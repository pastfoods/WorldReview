package com.example.WorldReview.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CountryDTO {
    private String code;
    private String name_ko;
    private String name_en;
    private Integer center_x;
    private Integer center_y;
}
