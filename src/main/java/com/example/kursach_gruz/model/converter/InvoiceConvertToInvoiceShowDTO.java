package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.showdto.InvoiceShowDTO;
import com.example.kursach_gruz.model.dto.showdto.ShowApplicationDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoiceConvertToInvoiceShowDTO implements EntityToDTOConverter<Invoice, InvoiceShowDTO> {

    @Override
    public InvoiceShowDTO convert(Invoice invoice) {
        InvoiceShowDTO dto = new InvoiceShowDTO();
        dto.setDateCreate(invoice.getDateCreate());
        dto.setDescriptionInvoice(invoice.getDescriptionInvoice());
        dto.setDocument(invoice.getDocument());
        dto.setStatus(invoice.getStatus());
        dto.setUserConfirmed(invoice.getUserConfirmed());
        dto.setCost(invoice.getCost());
        dto.setPointOfDeparture(invoice.getPointOfDeparture());
        dto.setPointOfReceipt(invoice.getPointOfReceipt());
        dto.setApplication(invoice.getApplication());
        return dto;
    }
}
