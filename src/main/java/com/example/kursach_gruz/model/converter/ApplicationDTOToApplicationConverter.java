package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.ApplicationDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.User;
import com.example.kursach_gruz.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationDTOToApplicationConverter implements DTOToEntityConverter<ApplicationDTO, Application,Long>{
    private final UserRepository userRepository;

    @Override
    public Application convert(ApplicationDTO dto, Long id) {
        if (dto == null) {
            return null;
        }

        Application application = new Application();
        application.setCreateDate(dto.getCreateDate());
        application.setDesiredDepartureDate(dto.getDesiredDepartureDate());
        application.setDesiredDateOfReceipt(dto.getDesiredDateOfReceipt());
        application.setDesiredPointOfDeparture(dto.getDesiredPointOfDeparture());
        application.setDesiredPointOfReceipt(dto.getDesiredPointOfReceipt());
        application.setDescription(dto.getDescription());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
        application.setUser(user);

        // Мы можем установить объект User позже, когда пользователь будет загружен из базы данных
        // Для этого можно использовать UserService или аналогичный компонент
        // application.setUser(getUserById(dto.getUser())); // предполагается метод получения User по ID

        return application;
    }

}
