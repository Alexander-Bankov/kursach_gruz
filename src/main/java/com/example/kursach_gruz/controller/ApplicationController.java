package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.dto.ApplicationDTO;
import com.example.kursach_gruz.service.impl.ApplicationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(@RequestBody ApplicationDTO applicationDTO, HttpServletRequest request) {
        ApplicationDTO createdApplication = applicationService.create(applicationDTO, request);
        return ResponseEntity.status(201).body(createdApplication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long id) {
        ApplicationDTO applicationDTO = applicationService.findById(id);
        return ResponseEntity.ok(applicationDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable Long id, @RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO updatedApplication = applicationService.update(id, applicationDTO);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        List<ApplicationDTO> applications = applicationService.findAll();
        return ResponseEntity.ok(applications);
    }
}
