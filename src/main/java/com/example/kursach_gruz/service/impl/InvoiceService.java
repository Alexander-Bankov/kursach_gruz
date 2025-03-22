package com.example.kursach_gruz.service.impl;

import com.example.kursach_gruz.model.converter.InvoiceConvertToInvoiceShowDTO;
import com.example.kursach_gruz.model.dto.InvoiceDTO;
import com.example.kursach_gruz.model.dto.showdto.InvoiceShowDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.enums.InvoiceStatus;
import com.example.kursach_gruz.model.repository.InvoiceRepository;
import com.example.kursach_gruz.service.userService.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceConvertToInvoiceShowDTO convertToInvoiceShowDTO;
    private final AuthorizationService authorizationService;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          InvoiceConvertToInvoiceShowDTO convertToInvoiceShowDTO,
                          AuthorizationService authorizationService) {
        this.invoiceRepository = invoiceRepository;
        this.convertToInvoiceShowDTO = convertToInvoiceShowDTO;
        this.authorizationService = authorizationService;
    }

    public InvoiceShowDTO createInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setDateCreate(LocalDateTime.now());
        invoice.setDescriptionInvoice(invoiceDTO.getDescriptionInvoice());
        invoice.setDocument(invoiceDTO.getDocument());
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
        return invoices.stream().map(convertToInvoiceShowDTO::convert).toList();
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
            existingInvoice.setDocument(invoiceDTO.getDocument());
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
