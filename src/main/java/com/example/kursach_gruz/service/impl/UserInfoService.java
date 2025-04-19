package com.example.kursach_gruz.service.impl;

import com.example.kursach_gruz.model.dto.UserDTO;
import com.example.kursach_gruz.model.dto.showdto.UserShowDTO;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.entity.Order;
import com.example.kursach_gruz.model.entity.User;
import com.example.kursach_gruz.model.enums.ApplicationStatus;
import com.example.kursach_gruz.model.enums.InvoiceStatus;
import com.example.kursach_gruz.model.enums.RecordStatus;
import com.example.kursach_gruz.model.repository.*;
import com.example.kursach_gruz.service.userService.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
    private final ApplicationRepository applicationRepository;

    private final InvoiceRepository invoiceRepository;

    private final CargoService cargoService;

    private final CargoRepository cargoRepository;
    private final OrderRepository orderRepository;

    public UserShowDTO getCurrentUserInfo() {
        String mail = authorizationService.getCurrentUserEmail();

        // Находим пользователя по email
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        // Преобразовываем User в UserShowDTO
        return new UserShowDTO(user.getFullName(), user.getMail(), user.getPassword(), user.getPhone(), user.getRole());
    }

    public UserShowDTO updateUserInfo(UserDTO userDTO) {
        String mail = authorizationService.getCurrentUserEmail();

        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        // Обновляем поля пользователя
        user.setFullName(userDTO.getFullName());
        user.setMail(userDTO.getEmail());
        user.setPhone(userDTO.getPhoneNumber());

        userRepository.save(user);

        return new UserShowDTO(user.getFullName(), user.getMail(), user.getPassword(), user.getPhone(), user.getRole());
    }

    public void createOrderByInvoice(Long invoiceId){
        String mail = authorizationService.getCurrentUserEmail();

        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalStateException("Накладная с таким id не найдена"));
        Order order = new Order();
        order.setIdApplication(invoice.getApplication().getIdApplication());
        order.setIdInvoice(invoiceId);
        order.setDateStartExecution(LocalDateTime.now());
        order.setStatus(RecordStatus.CREATED);
        orderRepository.save(order);

    }

    public void cancelAll(Long invoiceId){
        String mail = authorizationService.getCurrentUserEmail();

        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalStateException("Накладная с таким id не найдена"));
        invoice.setStatus(InvoiceStatus.REJECTED);
        invoice.getApplication().setStatus(ApplicationStatus.REJECTED);
        invoiceRepository.save(invoice);
    }
}
