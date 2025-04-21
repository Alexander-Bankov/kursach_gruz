package com.example.kursach_gruz.service.impl;

import com.example.kursach_gruz.model.converter.ApplicationDTOToApplicationConverter;
import com.example.kursach_gruz.model.converter.ApplicationToShowApplicationDTOConverter;
import com.example.kursach_gruz.model.dto.ApplicationDTO;
import com.example.kursach_gruz.model.dto.showdto.ShowApplicationDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.User;
import com.example.kursach_gruz.model.enums.ApplicationStatus;
import com.example.kursach_gruz.model.repository.ApplicationRepository;
import com.example.kursach_gruz.model.repository.UserRepository;
import com.example.kursach_gruz.service.userService.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ApplicationService /*implements BaseService<ApplicationDTO, Long>*/ {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ApplicationToShowApplicationDTOConverter applicationToShowApplicationDTOConverter;
    private final ApplicationDTOToApplicationConverter applicationDTOToApplicationConverter;
    private final AuthorizationService authorizationService;

    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository,
                              ApplicationToShowApplicationDTOConverter applicationToShowApplicationDTOConverter,
                              ApplicationDTOToApplicationConverter applicationDTOToApplicationConverter,
                              AuthorizationService authorizationService) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.applicationToShowApplicationDTOConverter = applicationToShowApplicationDTOConverter;
        this.applicationDTOToApplicationConverter = applicationDTOToApplicationConverter;
        this.authorizationService = authorizationService;
    }

    //@Override
    public ShowApplicationDTO create(ApplicationDTO dto, HttpServletRequest request) {
        String mail = authorizationService.getCurrentUserEmail();

        // Находим пользователя по email
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        // Преобразуем DTO в сущность Application
        Application application = applicationDTOToApplicationConverter.convert(dto, user.getUserId());
        application.setUser(user); // Устанавливаем пользователя
        application.setStatus(ApplicationStatus.CREATE); // Устанавливаем статус на CREATE
        application.setCreateDate(LocalDateTime.now());
        // Сохраняем заявку в базе
        Application savedApplication = applicationRepository.save(application);
        return applicationToShowApplicationDTOConverter.convert(savedApplication); // Возвращаем DTO
    }

   // @Override
    public ShowApplicationDTO update(Long id, ApplicationDTO dto) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Заявка не найдена"));

        application.setCreateDate(dto.getCreateDate());
        application.setDesiredDepartureDate(dto.getDesiredDepartureDate());
        application.setDesiredDateOfReceipt(dto.getDesiredDateOfReceipt());
        application.setDesiredPointOfDeparture(dto.getDesiredPointOfDeparture());
        application.setDesiredPointOfReceipt(dto.getDesiredPointOfReceipt());
        application.setDescription(dto.getDescription());
        application.setDistance(dto.getDistance());

        Application updatedApplication = applicationRepository.save(application);
        return applicationToShowApplicationDTOConverter.convert(updatedApplication);
    }

    //@Override
    public void delete(Long id) {
        applicationRepository.deleteById(id);
    }

    //@Override
    public ShowApplicationDTO findById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Заявка не найдена"));
        return applicationToShowApplicationDTOConverter.convert(application);
    }

   // @Override
    public List<ShowApplicationDTO> findAll() {
        return applicationRepository.findAll()
                .stream()
                .map(applicationToShowApplicationDTOConverter::convert)
                .sorted(Comparator.comparing(ShowApplicationDTO::getId).reversed())
                .toList();
    }

    public List<ShowApplicationDTO> findAllByUserId() {
        String mail = authorizationService.getCurrentUserEmail();
        User user = userRepository.findByEmail(mail)
                    .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));
        return applicationRepository.findAllByUser(user)
                .stream()
                .map(applicationToShowApplicationDTOConverter::convert)
                .sorted(Comparator.comparing(ShowApplicationDTO::getId).reversed())
                .toList();
    }

//    public List<ShowApplicationDTO> findAllByUserId() {
//        try {
//            String mail = authorizationService.getCurrentUserEmail();
//
//            // Находим пользователя по email
//            User user = userRepository.findByEmail(mail)
//                    .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));
//            List<ShowApplicationDTO> a = applicationRepository.findAllByUserId(user.getUserId());
//            return applicationRepository.findAllByUserId(user.getUserId());
//        } catch (Exception e) {
//            // Логируем ошибку (можно использовать любой механизм логирования, например, SLF4J)
//            System.err.println("Произошла ошибка при получении заявок пользователя: " + e.getMessage());
//
//            // В зависимости от вашей логики, вы можете либо пробросить исключение далее, либо вернуть пустой список, например:
//            // return Collections.emptyList();
//
//            // Если хотите пробросить исключение
//            throw new RuntimeException("Ошибка при получении заявок пользователя", e);
//        }
//    }

}