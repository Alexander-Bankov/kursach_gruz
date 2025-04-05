package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.dto.showdto.UserShowDTO;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.enums.ApplicationStatus;
import com.example.kursach_gruz.model.enums.InvoiceStatus;
import com.example.kursach_gruz.service.impl.AdminService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/get-personal-info")
    public ResponseEntity<UserShowDTO> getPersonalAdminInfo(HttpServletRequest request) {
        UserShowDTO userShowDTO = adminService.getPersonalAdminInfo(request); // Получаем DTO
        return ResponseEntity.ok(userShowDTO); // Оборачиваем его в ResponseEntity
    }

    @PostMapping("create-invoice/{applicationid}")
    public ResponseEntity<Void> createInvoice(@PathVariable Long applicationid) {
        adminService.createInvoice(applicationid); // Получаем DTO
        return ResponseEntity.ok().build(); // Оборачиваем его в ResponseEntity
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
