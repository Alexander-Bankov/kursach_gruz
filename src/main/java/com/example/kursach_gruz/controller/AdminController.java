package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.enums.ApplicationStatus;
import com.example.kursach_gruz.model.enums.InvoiceStatus;
import com.example.kursach_gruz.service.impl.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/change-status-application/{id}/{status}")
    public ResponseEntity<Void> updateStatusApplication(@PathVariable Long id, @PathVariable ApplicationStatus status) {
        adminService.updateStatusApplication(id, status);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-status-invoice/{id}/{status}")
    public ResponseEntity<Void> updateStatusInvoice(@PathVariable Long id, @PathVariable InvoiceStatus status,  HttpServletRequest request) {
        adminService.updateStatusInvoice(id, status, request);
        return ResponseEntity.ok().build();
    }
}
