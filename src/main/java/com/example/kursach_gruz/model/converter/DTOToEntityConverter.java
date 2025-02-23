package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.BaseDTO;
import com.example.kursach_gruz.model.entity.BaseEntity;

public interface DTOToEntityConverter<D extends BaseDTO, E extends BaseEntity, T extends Long> {
    E convert(D dto,T Long);
}
