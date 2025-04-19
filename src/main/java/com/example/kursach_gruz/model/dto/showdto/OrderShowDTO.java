package com.example.kursach_gruz.model.dto.showdto;

import com.example.kursach_gruz.model.dto.BaseDTO;
import com.example.kursach_gruz.model.enums.RecordStatus;
import com.example.kursach_gruz.model.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderShowDTO implements BaseDTO {
    private Long id;

    private LocalDateTime dateStartExecution;

    private LocalDateTime endDateExecution;

    private RecordStatus status;

    private Long invoiceId;

    private Long idApplication;
}
