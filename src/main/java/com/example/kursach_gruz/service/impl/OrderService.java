package com.example.kursach_gruz.service.impl;

import com.example.kursach_gruz.model.converter.OrderDTOToOrderConverter;
import com.example.kursach_gruz.model.converter.OrderToOrderShowDTOConverter;
import com.example.kursach_gruz.model.dto.OrderDTO;
import com.example.kursach_gruz.model.dto.showdto.InvoiceShowDTO;
import com.example.kursach_gruz.model.dto.showdto.OrderShowDTO;
import com.example.kursach_gruz.model.entity.Order;
import com.example.kursach_gruz.model.entity.User;
import com.example.kursach_gruz.model.repository.OrderRepository;
import com.example.kursach_gruz.model.repository.UserRepository;
import com.example.kursach_gruz.service.userService.AuthorizationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDTOToOrderConverter orderDTOToOrderConverter;
    private final OrderToOrderShowDTOConverter orderToOrderShowDTOConverter;
    private final AuthorizationService authorizationService;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderDTOToOrderConverter orderDTOToOrderConverter,
                        OrderToOrderShowDTOConverter orderToOrderShowDTOConverter,
                        AuthorizationService authorizationService,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDTOToOrderConverter = orderDTOToOrderConverter;
        this.orderToOrderShowDTOConverter = orderToOrderShowDTOConverter;
        this.authorizationService = authorizationService;
        this.userRepository = userRepository;
    }

    public OrderShowDTO createOrder(OrderDTO orderDTO) {
        Order order = orderDTOToOrderConverter.convert(orderDTO, null);
        order = orderRepository.save(order);
        return orderToOrderShowDTOConverter.convert(order);
    }

    public OrderShowDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        // Обновляем поля заказа
        order.setDateStartExecution(orderDTO.getDateStartExecution());
        order.setEndDateExecution(orderDTO.getEndDateExecution());
        order.setIdApplication(orderDTO.getIdApplication());
        order.setIdInvoice(orderDTO.getIdInvoice());
        order.setStatus(orderDTO.getStatus());
        order = orderRepository.save(order);
        return orderToOrderShowDTOConverter.convert(order);
    }

    public OrderShowDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderToOrderShowDTOConverter.convert(order);
    }

    public List<OrderShowDTO> getOrdersByApplicationId(Long applicationId) {
        List<Order> orders = orderRepository.findAllByIdApplication(applicationId);
        return orders.stream()
                .map(orderToOrderShowDTOConverter::convert)
                .sorted(Comparator.comparing(OrderShowDTO::getId).reversed())
                .collect(Collectors.toList());
    }

    public List<OrderShowDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderToOrderShowDTOConverter::convert)
                .sorted(Comparator.comparing(OrderShowDTO::getId).reversed())
                .collect(Collectors.toList());
    }

    public List<OrderShowDTO> getAllOrdersForUser() {
        String mail = authorizationService.getCurrentUserEmail();
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        // Получаем все заказы для текущего пользователя по его ID
        List<Order> orders = orderRepository.findOrdersByUserId(user.getUserId())
                .orElseGet(Collections::emptyList); // Если не найдено, возвращаем пустой список

        return orders.stream()
                .map(orderToOrderShowDTOConverter::convert)
                .sorted(Comparator.comparing(OrderShowDTO::getId).reversed())
                .collect(Collectors.toList());
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }
}
