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

import java.math.BigDecimal;

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

    @PutMapping("/confirmed-invoice/{id}")
    public ResponseEntity<Void> updateStatusInvoice(@PathVariable Long id,HttpServletRequest request) {
        adminService.confirmedInvoice(id, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-cost-invoice/{id}/{cost}")
    public ResponseEntity<Void> updateCostInvoice(@PathVariable Long id, @PathVariable BigDecimal cost, HttpServletRequest request) {
        adminService.changeCostInvoice(id, cost, request);
        return ResponseEntity.ok().build();
    }
}
