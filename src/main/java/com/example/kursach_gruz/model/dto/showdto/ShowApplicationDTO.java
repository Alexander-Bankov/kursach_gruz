package com.example.kursach_gruz.model.dto.showdto;

import com.example.kursach_gruz.model.dto.BaseDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowApplicationDTO implements BaseDTO {

    private Long id;

    private LocalDateTime createDate;

    private LocalDateTime desiredDepartureDate;

    private LocalDateTime desiredDateOfReceipt;

    private String desiredPointOfDeparture;

    private String desiredPointOfReceipt;

    private String description;

    private  String applicationStatus;
}
