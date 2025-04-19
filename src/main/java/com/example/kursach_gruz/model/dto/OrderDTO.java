package com.example.kursach_gruz.model.dto;

import com.example.kursach_gruz.model.enums.RecordStatus;
import com.example.kursach_gruz.model.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO implements BaseDTO{

    private LocalDateTime dateStartExecution;

    private LocalDateTime endDateExecution;

    private Long idApplication;

    private Long idInvoice;

    private RecordStatus status;
}
