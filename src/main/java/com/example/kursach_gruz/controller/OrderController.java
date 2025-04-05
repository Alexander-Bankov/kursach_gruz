package com.example.kursach_gruz.controller;

import com.example.kursach_gruz.model.dto.OrderDTO;
import com.example.kursach_gruz.model.dto.showdto.OrderShowDTO;
import com.example.kursach_gruz.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderShowDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderShowDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderShowDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        OrderShowDTO updatedOrder = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderShowDTO> getOrderById(@PathVariable Long id) {
        OrderShowDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<OrderShowDTO>> getOrdersByApplicationId(@PathVariable Long applicationId) {
        List<OrderShowDTO> orders = orderService.getOrdersByApplicationId(applicationId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<OrderShowDTO>> getAllOrders() {
        List<OrderShowDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
