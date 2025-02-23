package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.BaseDTO;
import com.example.kursach_gruz.model.entity.BaseEntity;

public interface EntityToDTOConverter<E extends BaseEntity, D extends BaseDTO> {
    D convert(E entity);
}
