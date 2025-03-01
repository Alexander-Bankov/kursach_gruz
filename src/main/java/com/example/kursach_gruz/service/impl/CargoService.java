package com.example.kursach_gruz.service.impl;

import com.example.kursach_gruz.model.converter.CargoConvertToCargoDTO;
import com.example.kursach_gruz.model.dto.CargoDTO;
import com.example.kursach_gruz.model.dto.showdto.CargoShowDTO;
import com.example.kursach_gruz.model.entity.Cargo;
import com.example.kursach_gruz.model.entity.RelationshipBetweenApplicationAndCargo;
import com.example.kursach_gruz.model.entity.User;
import com.example.kursach_gruz.model.repository.ApplicationRepository;
import com.example.kursach_gruz.model.repository.CargoApplicationRelationshipRepository;
import com.example.kursach_gruz.model.repository.CargoRepository;
import com.example.kursach_gruz.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService  {
    private final CargoConvertToCargoDTO cargoConvertToCargoDTO;
    private final UserRepository userRepository;
    private final CargoRepository cargoRepository;
    private final ApplicationRepository applicationRepository;
    private final CargoApplicationRelationshipRepository relationshipRepository;


    public CargoService(
            CargoConvertToCargoDTO cargoConvertToCargoDTO,
            UserRepository userRepository,
            CargoRepository cargoRepository,
            ApplicationRepository applicationRepository,
            CargoApplicationRelationshipRepository relationshipRepository) {
        this.cargoConvertToCargoDTO = cargoConvertToCargoDTO;
        this.userRepository = userRepository;
        this.cargoRepository = cargoRepository;
        this.applicationRepository = applicationRepository;
        this.relationshipRepository = relationshipRepository;
    }

    public CargoDTO create(CargoDTO dto, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Получаем текущую сессию
        String mail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
        if (mail == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));
        if (!applicationRepository.existsByIdApplication(dto.getApplicationId())) {
            throw new RuntimeException("Заявки с такой ид нет");
        }
        Cargo cargo = new Cargo();
        cargo.setUserId(user.getUserId());
        cargo.setWeight(dto.getWeight());
        cargo.setLength(dto.getLength());
        cargo.setHeight(dto.getHeight());
        cargo.setWidth(dto.getWidth());
        cargo.setContent(dto.getContent());
        Cargo savedCargo = cargoRepository.save(cargo); // Сохраняем груз и получаем сохраненный объект

        // Создание записи в таблице связи
        RelationshipBetweenApplicationAndCargo relationship = new RelationshipBetweenApplicationAndCargo();
        relationship.setApplicationId(dto.getApplicationId()); // Устанавливаем id заявки
        relationship.setCargoId(savedCargo.getIdCargo()); // Устанавливаем id сохраненного груза

        relationshipRepository.save(relationship); // Сохраняем связь

        return dto;
    }

    public CargoDTO updateCargo(Long cargoId, CargoDTO dto) {
        Cargo cargo = cargoRepository.findById(cargoId)
                .orElseThrow(() -> new RuntimeException("Груз с таким ID не найден"));
        cargo.setWeight(dto.getWeight());
        cargo.setLength(dto.getLength());
        cargo.setHeight(dto.getHeight());
        cargo.setWidth(dto.getWidth());
        cargo.setContent(dto.getContent());
        cargoRepository.save(cargo);

        return dto; // вернуть обновленный DTO при необходимости
    }
    public CargoShowDTO getCargoById(Long cargoId) {
        CargoShowDTO cargoShowDTO = cargoRepository.findCargoShowByCargoId(cargoId);
        if (cargoShowDTO == null) {
            throw new RuntimeException("Груз с таким ID не найден");
        }
        return cargoShowDTO;
    }


    public List<CargoShowDTO> getAllCargoByApplicationId(Long applicationId) {
        return cargoRepository.findCargoShowByApplicationId(applicationId);
    }

    // Поиск груза по идентификатору заявки и его идентификатору груза
//    public CargoDTO getCargoByApplicationAndCargoId(Long applicationId, Long cargoId) {
//        if (!relationshipRepository.existsByApplicationIdAndCargoId(applicationId, cargoId)) {
//            throw new RuntimeException("Груз с таким ID не найден для данной заявки");
//        }
//        return getCargoById(cargoId);
//    }



    public void deleteCargo(Long cargoId) {
        // Сначала удаляем связи с этим грузом
        cargoRepository.deleteRelationshipsByCargoId(cargoId);
        // Затем удаляем сам груз
        cargoRepository.deleteByCargoId(cargoId);
    }



}
