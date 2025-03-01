package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.ApplicationDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDTOToApplicationConverter implements DTOToEntityConverter<ApplicationDTO, Application,Long>{
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
        application.setUser(new User(id));

        // Мы можем установить объект User позже, когда пользователь будет загружен из базы данных
        // Для этого можно использовать UserService или аналогичный компонент
        // application.setUser(getUserById(dto.getUser())); // предполагается метод получения User по ID

        return application;
    }

}
