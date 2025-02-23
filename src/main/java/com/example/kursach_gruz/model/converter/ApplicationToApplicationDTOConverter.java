package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.ApplicationDTO;
import com.example.kursach_gruz.model.entity.Application;

public class ApplicationToApplicationDTOConverter implements EntityToDTOConverter<Application, ApplicationDTO>{

    @Override
    public ApplicationDTO convert(Application application) {
        if (application == null) {
            return null;
        }

        ApplicationDTO dto = new ApplicationDTO();
        dto.setCreateDate(application.getCreateDate());
        dto.setDesiredDepartureDate(application.getDesiredDepartureDate());
        dto.setDesiredDateOfReceipt(application.getDesiredDateOfReceipt());
        dto.setDesiredPointOfDeparture(application.getDesiredPointOfDeparture());
        dto.setDesiredPointOfReceipt(application.getDesiredPointOfReceipt());
        dto.setDescription(application.getDescription());


        return dto;
    }
}
