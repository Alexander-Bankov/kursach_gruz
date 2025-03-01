package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.CargoDTO;
import com.example.kursach_gruz.model.entity.Cargo;
import org.springframework.stereotype.Component;

@Component
public class CargoConvertToCargoDTO implements EntityToDTOConverter<Cargo, CargoDTO>{

    @Override
    public CargoDTO convert(Cargo cargo) {
        CargoDTO dto = new CargoDTO();
        dto.setWeight(cargo.getWeight());
        dto.setLength(cargo.getLength());
        dto.setWidth(cargo.getWidth());
        dto.setHeight(cargo.getHeight());
        dto.setContent(cargo.getContent());
        // здесь можно добавить другие поля, если нужно
        return dto;
    }
}
