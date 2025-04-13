package com.example.kursach_gruz.model.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationDTO implements BaseDTO {

    private LocalDateTime createDate;

    private LocalDateTime desiredDepartureDate;

    private LocalDateTime desiredDateOfReceipt;

    private String desiredPointOfDeparture;

    private String desiredPointOfReceipt;

    private String description;

    private Double distance;
}
