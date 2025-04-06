package com.example.kursach_gruz.model.dto.showdto;

import com.example.kursach_gruz.model.dto.BaseDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceShowDTO implements BaseDTO {

    private Long id;

    private LocalDateTime dateCreate;

    private String descriptionInvoice;

    private InvoiceStatus status;

    private Long userConfirmed;

    private BigDecimal cost;

    private String pointOfDeparture;

    private String pointOfReceipt;

    private Application application;
}
