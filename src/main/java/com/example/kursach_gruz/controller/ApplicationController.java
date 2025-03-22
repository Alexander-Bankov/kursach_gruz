package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.dto.ApplicationDTO;
import com.example.kursach_gruz.model.dto.showdto.ShowApplicationDTO;
import com.example.kursach_gruz.service.impl.ApplicationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ShowApplicationDTO> createApplication(@RequestBody ApplicationDTO applicationDTO, HttpServletRequest request) {
        ShowApplicationDTO createdApplication = applicationService.create(applicationDTO, request);
        return ResponseEntity.status(201).body(createdApplication);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowApplicationDTO> getApplication(@PathVariable Long id) {
        ShowApplicationDTO applicationDTO = applicationService.findById(id);
        return ResponseEntity.ok(applicationDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowApplicationDTO> updateApplication(@PathVariable Long id, @RequestBody ApplicationDTO applicationDTO) {
        ShowApplicationDTO updatedApplication = applicationService.update(id, applicationDTO);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ShowApplicationDTO>> getAllApplications() {
        List<ShowApplicationDTO> applications = applicationService.findAll();
        return ResponseEntity.ok(applications);
    }
}
