package com.example.kursach_gruz.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceDTO implements BaseDTO{

    private String descriptionInvoice;

    private String document;

    private BigDecimal cost;

    private String pointOfDeparture;

    private String pointOfReceipt;

    private Long applicationId;
}
