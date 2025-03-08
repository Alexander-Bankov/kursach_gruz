package com.example.kursach_gruz.model.converter;

import com.example.kursach_gruz.model.dto.showdto.InvoiceShowDTO;
import com.example.kursach_gruz.model.dto.showdto.OrderShowDTO;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderShowDTOConverter implements EntityToDTOConverter<Order, OrderShowDTO>{

    @Override
    public OrderShowDTO convert(Order order) {
        OrderShowDTO dto = new OrderShowDTO();
        dto.setDateStartExecution(order.getDateStartExecution());
        dto.setEndDateExecution(order.getEndDateExecution());
        dto.setStatus(order.getStatus());
        dto.setIdApplication(order.getIdApplication());
        return dto;
    }
}
