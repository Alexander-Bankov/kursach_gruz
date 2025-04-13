package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.showdto.InvoiceShowDTO;
import com.example.kursach_gruz.model.dto.showdto.ShowApplicationDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InvoiceConvertToInvoiceShowDTO implements EntityToDTOConverter<Invoice, InvoiceShowDTO> {

    private final UserRepository userRepository;

    public InvoiceConvertToInvoiceShowDTO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public InvoiceShowDTO convert(Invoice invoice) {
        InvoiceShowDTO dto = new InvoiceShowDTO();
        dto.setInvoiceId(invoice.getIdInvoice());
        dto.setDateCreate(invoice.getDateCreate());
        dto.setDescriptionInvoice(invoice.getDescriptionInvoice());
        dto.setStatus(invoice.getStatus());
        dto.setUserConfirmed(Optional.ofNullable(invoice.getUserConfirmed()) // Оборачиваем ID в Optional
                .flatMap(userRepository::findFullNameById)     // Ищем полное имя по ID
                .orElse(null));
//        dto.setUserConfirmed(userRepository.findFullNameById(invoice.getUserConfirmed())
//                .orElseThrow(() -> new IllegalStateException("Пользователь с таким id не найден")));
        dto.setCost(invoice.getCost());
        dto.setPointOfDeparture(invoice.getPointOfDeparture());
        dto.setPointOfReceipt(invoice.getPointOfReceipt());
        dto.setApplication(invoice.getApplication());
        dto.setApplicationId(invoice.getApplication().getIdApplication());
        return dto;
    }
}
