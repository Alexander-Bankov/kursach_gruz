package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.converter.InvoiceConvertToInvoiceShowDTO;
import com.example.kursach_gruz.model.dto.InvoiceDTO;
import com.example.kursach_gruz.model.dto.showdto.InvoiceShowDTO;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.service.impl.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceShowDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        InvoiceShowDTO createdInvoice = invoiceService.createInvoice(invoiceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInvoice);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceShowDTO>> getAllInvoices() {
        List<InvoiceShowDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/user")
    public ResponseEntity<List<InvoiceShowDTO>> getAllInvoicesByUser() {
        List<InvoiceShowDTO> invoices = invoiceService.getAllInvoicesForUser();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceShowDTO> getInvoiceById(@PathVariable Long id) {
        InvoiceShowDTO invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceShowDTO> updateInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoiceDTO) {
        InvoiceShowDTO updatedInvoice = invoiceService.updateInvoice(id, invoiceDTO);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
