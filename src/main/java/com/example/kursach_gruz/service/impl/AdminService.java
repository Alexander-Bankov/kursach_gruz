package com.example.kursach_gruz.service.impl;

import com.example.kursach_gruz.model.dto.showdto.CargoShowDTO;
import com.example.kursach_gruz.model.dto.showdto.UserShowDTO;
import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.entity.Order;
import com.example.kursach_gruz.model.entity.User;
import com.example.kursach_gruz.model.enums.ApplicationStatus;
import com.example.kursach_gruz.model.enums.InvoiceStatus;
import com.example.kursach_gruz.model.enums.RecordStatus;
import com.example.kursach_gruz.model.enums.Role;
import com.example.kursach_gruz.model.repository.*;
import com.example.kursach_gruz.service.userService.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    private final ApplicationRepository applicationRepository;

    private final InvoiceRepository invoiceRepository;

    private final CargoService cargoService;

    private final UserRepository userRepository;

    private final AuthorizationService authorizationService;

    private final CargoRepository cargoRepository;

    private final OrderRepository orderRepository;

    public AdminService(ApplicationRepository applicationRepository,
                        InvoiceRepository invoiceRepository,
                        UserRepository userRepository,
                        AuthorizationService authorizationService,
                        CargoService cargoService, CargoRepository cargoRepository,OrderRepository orderRepository) {
        this.applicationRepository = applicationRepository;
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
        this.cargoService = cargoService;
        this.cargoRepository = cargoRepository;
        this.orderRepository = orderRepository;
    }

    public UserShowDTO getPersonalAdminInfo() {
        String email = authorizationService.getCurrentUserEmail();

        // Находим пользователя по email и возвращаем DTO
        return userRepository.findUserShowDTOByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));
    }

    public List<UserShowDTO> getPersonalUsersInfo() {
        String email = authorizationService.getCurrentUserEmail();

        // Находим пользователя по email и возвращаем DTO
        return userRepository.findUser();
    }

    public List<UserShowDTO> getPersonalUsersInfoByEmail(String email) {
        try{

            // Находим пользователя по email и возвращаем DTO
            return userRepository.findUsersByEmail(email);
        }
        catch (Exception e){
            throw new RuntimeException("Пользователи не найдены");
        }
    }

    public void changeRoleAdmin(String email){
        try{
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            user.setRole(Role.ADMINISTRATOR);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new RuntimeException("Не получилось изменить роль пользователя");
        }
    }

    public void changeRoleUser(String email){
        try{
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            user.setRole(Role.USER);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new RuntimeException("Не получилось изменить роль пользователя");
        }
    }

    @Transactional
    public void updateStatusOrder(Long idOrder, RecordStatus status) {
        Order order = orderRepository.findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if(status == RecordStatus.DELIVERED){
            order.setStatus(RecordStatus.DELIVERED);
            order.setEndDateExecution(LocalDateTime.now());
        }
        else {
            order.setStatus(status);
        }
        orderRepository.save(order);

    }

    @Transactional
    public void updateStatusApplication(Long idApplication, ApplicationStatus status) {
        Application application = applicationRepository.findById(idApplication)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        applicationRepository.save(application);
//        if (status == ApplicationStatus.ACCEPT && invoiceRepository.findInvoiceByApplicationId(idApplication).isEmpty()){
//            Invoice invoice = new Invoice();
//            invoice.setDateCreate(LocalDateTime.now());
//            invoice.setDescriptionInvoice(application.getDescription());
//            invoice.setStatus(InvoiceStatus.CREATE);
//            invoice.setPointOfReceipt(application.getDesiredPointOfReceipt());
//            invoice.setPointOfDeparture(application.getDesiredPointOfDeparture());
//            invoice.setApplication(application);
//            invoiceRepository.save(invoice);
//            application.setStatus(ApplicationStatus.INVOICE_CREATED);
//            applicationRepository.save(application);
//        }
//        else {throw new RuntimeException("На данную заявку уже есть накладная");
//        }
    }

    @Transactional
    public void confirmedInvoice(Long idApplication,HttpServletRequest request) {
        String mail = authorizationService.getCurrentUserEmail();
        // Находим пользователя по email
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        Invoice invoice = invoiceRepository.findInvoiceByApplicationId(idApplication)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        //invoice.setStatus(InvoiceStatus.SEND_TO_ORDER);
        invoice.setUserConfirmed(user.getUserId());
        invoiceRepository.save(invoice);
    }

    @Transactional
    public void changeCostInvoice(Long idApplication) {
        String mail = authorizationService.getCurrentUserEmail();

        // Находим пользователя по email
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        Application application = applicationRepository.findById(idApplication)
                .orElseThrow(() -> new IllegalStateException("Заявка не найдена"));

        List<CargoShowDTO> cargoShowDTOList = cargoRepository.findCargoShowByApplicationId(idApplication);

        Double distance = application.getDistance();
        BigDecimal cost = BigDecimal.valueOf(0.00);

        // Рассчитываем стоимость грузов
        for (CargoShowDTO cargo : cargoShowDTOList) {
            // Для каждого груза добавляем стоимость за его вес
            cost = cost.add(BigDecimal.valueOf(cargo.getWeight() * 50.00));
        }

        // Добавляем стоимость за расстояние
        cost = cost.add(BigDecimal.valueOf(distance * 35.00));

        // Получаем или создаем счет
        Invoice invoice = invoiceRepository.findInvoiceByApplicationId(idApplication)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        // Устанавливаем статус и другие параметры счета
        invoice.setStatus(InvoiceStatus.SEND_TO_ORDER);
        invoice.setCost(cost); // Установите общую стоимость
        invoiceRepository.save(invoice);
    }

    public void createInvoice(Long applicationId){
        // Получаем список грузов по ID заявки
        List<CargoShowDTO> cargoList = cargoService.getAllCargoByApplicationId(applicationId);

        // Проверяем, есть ли грузы
        if (cargoList.isEmpty()) {
            throw new RuntimeException("Невозможно создать накладную, так как нет грузов по данной заявке");
        }
        applicationRepository.findUserIdByApplicationId(applicationId);
        Application application = applicationRepository.findById(applicationId)
                .orElse(new Application());

        Invoice invoice = new Invoice();
        invoice.setDateCreate(LocalDateTime.now());
        invoice.setDescriptionInvoice(application.getDescription());
        invoice.setPointOfReceipt(application.getDesiredPointOfReceipt());
        invoice.setPointOfDeparture(application.getDesiredPointOfDeparture());
        invoice.setApplication(application);
        invoice.setStatus(InvoiceStatus.CREATE);
        invoiceRepository.save(invoice);
        application.setStatus(ApplicationStatus.INVOICE_CREATED);
        applicationRepository.save(application);

    }
}
