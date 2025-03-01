package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.showdto.ShowApplicationDTO;
import com.example.kursach_gruz.model.entity.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationToShowApplicationDTOConverter implements EntityToDTOConverter<Application, ShowApplicationDTO>{

    @Override
    public ShowApplicationDTO convert(Application application) {
        if (application == null) {
            return null;
        }

        ShowApplicationDTO dto = new ShowApplicationDTO();
        dto.setCreateDate(application.getCreateDate());
        dto.setDesiredDepartureDate(application.getDesiredDepartureDate());
        dto.setDesiredDateOfReceipt(application.getDesiredDateOfReceipt());
        dto.setDesiredPointOfDeparture(application.getDesiredPointOfDeparture());
        dto.setDesiredPointOfReceipt(application.getDesiredPointOfReceipt());
        dto.setDescription(application.getDescription());
        dto.setStatus(application.getStatus()); // Установка статуса

        return dto;
    }
}
