package com.example.kursach_gruz.service.impl;

import com.example.kursach_gruz.model.converter.InvoiceConvertToInvoiceShowDTO;
import com.example.kursach_gruz.model.dto.InvoiceDTO;
import com.example.kursach_gruz.model.dto.showdto.InvoiceShowDTO;
import com.example.kursach_gruz.model.dto.showdto.ShowApplicationDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.entity.User;
import com.example.kursach_gruz.model.enums.InvoiceStatus;
import com.example.kursach_gruz.model.repository.InvoiceRepository;
import com.example.kursach_gruz.model.repository.UserRepository;
import com.example.kursach_gruz.service.userService.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceConvertToInvoiceShowDTO convertToInvoiceShowDTO;
    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          InvoiceConvertToInvoiceShowDTO convertToInvoiceShowDTO,
                          AuthorizationService authorizationService,
                          UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.convertToInvoiceShowDTO = convertToInvoiceShowDTO;
        this.authorizationService = authorizationService;
        this.userRepository = userRepository;
    }

    public InvoiceShowDTO createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setDateCreate(LocalDateTime.now());
        invoice.setDescriptionInvoice(invoiceDTO.getDescriptionInvoice());
        invoice.setCost(invoiceDTO.getCost());
        invoice.setPointOfDeparture(invoiceDTO.getPointOfDeparture());
        invoice.setPointOfReceipt(invoiceDTO.getPointOfReceipt());
        invoice.setApplication(new Application(invoiceDTO.getApplicationId()));
        invoice.setStatus(InvoiceStatus.CREATE);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return convertToInvoiceShowDTO.convert(savedInvoice);
    }

    public List<InvoiceShowDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices
                .stream()
                .map(convertToInvoiceShowDTO::convert)
                .sorted(Comparator.comparing(InvoiceShowDTO::getInvoiceId).reversed())
                .toList();
    }

    public List<InvoiceShowDTO> getAllInvoicesForUser() {
        String mail = authorizationService.getCurrentUserEmail();
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        // Получаем все накладные для текущего пользователя по его ID
        List<Invoice> invoices = invoiceRepository.findInvoicesByUserId(user.getUserId())
                .orElseGet(Collections::emptyList); // Если не найдено, возвращаем пустой список

        return invoices
                .stream()
                .map(convertToInvoiceShowDTO::convert)
                .sorted(Comparator.comparing(InvoiceShowDTO::getInvoiceId).reversed())
                .toList();
    }

    public InvoiceShowDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Нет накладной с таким id"));
        return convertToInvoiceShowDTO.convert(invoice);
    }

    public InvoiceShowDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        Optional<Invoice> existingInvoiceOpt = invoiceRepository.findById(id);
        if (existingInvoiceOpt.isPresent()) {
            Invoice existingInvoice = existingInvoiceOpt.get();
            existingInvoice.setDescriptionInvoice(invoiceDTO.getDescriptionInvoice());
            existingInvoice.setCost(invoiceDTO.getCost());
            existingInvoice.setPointOfDeparture(invoiceDTO.getPointOfDeparture());
            existingInvoice.setPointOfReceipt(invoiceDTO.getPointOfReceipt());
            return convertToInvoiceShowDTO.convert(invoiceRepository.save(existingInvoice));
        }
        throw new RuntimeException("Нет накладной с таким id");
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
