package com.example.kursach_gruz.service;

import com.example.kursach_gruz.model.dto.BaseDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BaseService<T, ID> {
    T create(T dto, HttpServletRequest request);

    T update(ID id, T dto);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();
}
