package com.example.kursach_gruz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO implements BaseDTO{
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private String content;
    private Long applicationId;
}
