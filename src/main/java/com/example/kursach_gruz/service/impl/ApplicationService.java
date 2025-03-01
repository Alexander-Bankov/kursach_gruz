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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService /*implements BaseService<ApplicationDTO, Long>*/ {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final ApplicationToShowApplicationDTOConverter applicationToShowApplicationDTOConverter;
    private final ApplicationDTOToApplicationConverter applicationDTOToApplicationConverter;

    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository,
                              ApplicationToShowApplicationDTOConverter applicationToShowApplicationDTOConverter,
                              ApplicationDTOToApplicationConverter applicationDTOToApplicationConverter) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.applicationToShowApplicationDTOConverter = applicationToShowApplicationDTOConverter;
        this.applicationDTOToApplicationConverter = applicationDTOToApplicationConverter;
    }

    //@Override
    public ShowApplicationDTO create(ApplicationDTO dto, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Получаем текущую сессию
        String mail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
        if (mail == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }

        // Находим пользователя по email
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        // Преобразуем DTO в сущность Application
        Application application = applicationDTOToApplicationConverter.convert(dto, user.getUserId());
        application.setUser(user); // Устанавливаем пользователя
        application.setStatus(ApplicationStatus.CREATE); // Устанавливаем статус на CREATE

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
                .toList();
    }

}