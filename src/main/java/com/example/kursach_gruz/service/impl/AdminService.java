package com.example.kursach_gruz.service.impl;

import com.example.kursach_gruz.model.entity.Application;
import com.example.kursach_gruz.model.entity.Invoice;
import com.example.kursach_gruz.model.entity.User;
import com.example.kursach_gruz.model.enums.ApplicationStatus;
import com.example.kursach_gruz.model.enums.InvoiceStatus;
import com.example.kursach_gruz.model.repository.ApplicationRepository;
import com.example.kursach_gruz.model.repository.InvoiceRepository;
import com.example.kursach_gruz.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AdminService {
    private final ApplicationRepository applicationRepository;

    private final InvoiceRepository invoiceRepository;

    private final UserRepository userRepository;

    public AdminService(ApplicationRepository applicationRepository,
                        InvoiceRepository invoiceRepository,
                        UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateStatusApplication(Long idApplication, ApplicationStatus status) {
        Application application = applicationRepository.findById(idApplication)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        applicationRepository.save(application);
        if (status == ApplicationStatus.ACCEPT && invoiceRepository.findInvoiceByApplicationId(idApplication).isEmpty()){
            Invoice invoice = new Invoice();
            invoice.setDateCreate(LocalDateTime.now());
            invoice.setDescriptionInvoice(application.getDescription());
            invoice.setStatus(InvoiceStatus.CREATE);
            invoice.setPointOfReceipt(application.getDesiredPointOfReceipt());
            invoice.setPointOfDeparture(application.getDesiredPointOfDeparture());
            invoice.setApplication(application);
            invoiceRepository.save(invoice);
            application.setStatus(ApplicationStatus.INVOICE_CREATED);
            applicationRepository.save(application);
        }
        else {throw new RuntimeException("На данную заявку уже есть накладная");}
    }

    @Transactional
    public void confirmedInvoice(Long idApplication,HttpServletRequest request) {

        HttpSession session = request.getSession(false); // Получаем текущую сессию
        String mail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
        if (mail == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }

        // Находим пользователя по email
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        Invoice invoice = invoiceRepository.findInvoiceByApplicationId(idApplication)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        invoice.setStatus(InvoiceStatus.SEND_TO_ORDER);
        invoice.setUserConfirmed(user.getUserId());
        invoiceRepository.save(invoice);
    }

    @Transactional
    public void changeCostInvoice(Long idApplication,BigDecimal cost, HttpServletRequest request) {

        HttpSession session = request.getSession(false); // Получаем текущую сессию
        String mail = session != null ? (String) session.getAttribute("userEmail") : null; // Извлекаем email из сессии
        if (mail == null) {
            throw new IllegalStateException("Пользователь не авторизован");
        }

        // Находим пользователя по email
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new IllegalStateException("Пользователь с таким email не найден"));

        Invoice invoice = invoiceRepository.findInvoiceByApplicationId(idApplication)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        invoice.setStatus(InvoiceStatus.SEND_TO_ORDER);
        invoice.setUserConfirmed(user.getUserId());
        invoiceRepository.save(invoice);
    }
}
