package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.dto.showdto.UserShowDTO;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.enums.ApplicationStatus;
import com.example.kursach_gruz.model.enums.InvoiceStatus;
import com.example.kursach_gruz.model.enums.RecordStatus;
import com.example.kursach_gruz.model.enums.Role;
import com.example.kursach_gruz.service.impl.AdminService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/get-personal-info")
    public ResponseEntity<UserShowDTO> getPersonalAdminInfo() {
        UserShowDTO userShowDTO = adminService.getPersonalAdminInfo(); // Получаем DTO
        return ResponseEntity.ok(userShowDTO); // Оборачиваем его в ResponseEntity
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<List<UserShowDTO>> getPersonalInfoUsers() {
        List<UserShowDTO> userShowDTOs = adminService.getPersonalUsersInfo(); // Получаем DTO
        return ResponseEntity.ok(userShowDTOs); // Оборачиваем его в ResponseEntity
    }

    @GetMapping("/get-user-by-email")
    public ResponseEntity<List<UserShowDTO>> getPersonalInfoUsersByMail(String email) {
        List<UserShowDTO> userShowDTOs = adminService.getPersonalUsersInfoByEmail(email); // Получаем DTO
        return ResponseEntity.ok(userShowDTOs); // Оборачиваем его в ResponseEntity
    }

    @PutMapping("/change-role")
    public ResponseEntity<Void> getPersonalInfoUsers(String email) {
        adminService.changeRoleUser(email); // Получаем DTO
        return ResponseEntity.ok().build(); // Оборачиваем его в ResponseEntity
    }

    @PostMapping("create-invoice/{applicationid}")
    public ResponseEntity<String> createInvoice(@PathVariable Long applicationid) {
        try {
            adminService.createInvoice(applicationid);
            return ResponseEntity.ok("Накладная успешно создана."); // Можно вернуть успешное сообщение
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Возвращаем сообщение из исключения
        }
    }

    @PutMapping("/change-status-order/{id}/{status}")
    public ResponseEntity<Void> changeStatusOrder(@PathVariable Long id, @PathVariable RecordStatus status) {
        adminService.updateStatusOrder(id, status);
        return ResponseEntity.ok().build();
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

    @PutMapping("/change-cost-invoice/{id}")
    public ResponseEntity<Void> updateCostInvoice(@PathVariable Long id) {
        adminService.changeCostInvoice(id);
        return ResponseEntity.ok().build();
    }
}
