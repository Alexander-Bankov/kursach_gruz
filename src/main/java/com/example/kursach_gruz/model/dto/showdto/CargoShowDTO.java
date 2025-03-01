package com.example.kursach_gruz.model.dto.showdto;

import com.example.kursach_gruz.model.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoShowDTO implements BaseDTO {
    private Long cargoId;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private String content;
    private Long applicationId;
    private Long userId;
}
