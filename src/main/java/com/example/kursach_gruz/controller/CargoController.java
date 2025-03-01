package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.dto.CargoDTO;
import com.example.kursach_gruz.model.dto.showdto.CargoShowDTO;
import com.example.kursach_gruz.service.impl.CargoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargo")
public class CargoController {
    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    // Эндпоинт для создания нового груза
    @PostMapping
    public ResponseEntity<CargoDTO> createCargo(@RequestBody CargoDTO cargoDTO, HttpServletRequest request) {
        CargoDTO createdCargo = cargoService.create(cargoDTO, request);
        return new ResponseEntity<>(createdCargo, HttpStatus.CREATED);
    }

    // Эндпоинт для обновления существующего груза
    @PutMapping("/{id}")
    public ResponseEntity<CargoDTO> updateCargo(@PathVariable("id") Long cargoId, @RequestBody CargoDTO cargoDTO) {
        CargoDTO updatedCargo = cargoService.updateCargo(cargoId, cargoDTO);
        return new ResponseEntity<>(updatedCargo, HttpStatus.OK);
    }

    // Эндпоинт для получения груза по его идентификатору
    @GetMapping("/{id}")
    public ResponseEntity<CargoShowDTO> getCargoById(@PathVariable("id") Long cargoId) {
        CargoShowDTO cargoShowDTO = cargoService.getCargoById(cargoId);
        return new ResponseEntity<>(cargoShowDTO, HttpStatus.OK);
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<CargoShowDTO>> getAllCargoByApplicationId(@PathVariable("applicationId") Long applicationId) {
        List<CargoShowDTO> cargoDTOList = cargoService.getAllCargoByApplicationId(applicationId);
        return new ResponseEntity<>(cargoDTOList, HttpStatus.OK);
    }

//    // Эндпоинт для получения груза по идентификатору заявки и его идентификатору груза
//    @GetMapping("/application/{applicationId}/cargo/{cargoId}")
//    public ResponseEntity<CargoDTO> getCargoByApplicationAndCargoId(
//            @PathVariable("applicationId") Long applicationId,
//            @PathVariable("cargoId") Long cargoId) {
//        CargoDTO cargoDTO = cargoService.getCargoByApplicationAndCargoId(applicationId, cargoId);
//        return new ResponseEntity<>(cargoDTO, HttpStatus.OK);
//    }

    // Эндпоинт для удаления груза по его идентификатору
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable("id") Long cargoId) {
        cargoService.deleteCargo(cargoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
