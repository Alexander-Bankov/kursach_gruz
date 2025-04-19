package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.ApplicationDTO;
import com.example.kursach_gruz.model.dto.OrderDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDTOToOrderConverter implements DTOToEntityConverter<OrderDTO, Order,Long>{

    @Override
    public Order convert(OrderDTO dto, Long id) {
        Order order = new Order();
        order.setDateStartExecution(dto.getDateStartExecution());
        order.setEndDateExecution(dto.getEndDateExecution());
        order.setIdApplication(dto.getIdApplication());
        order.setIdInvoice(dto.getIdInvoice());
        order.setStatus(dto.getStatus());
        return order;
    }
}
