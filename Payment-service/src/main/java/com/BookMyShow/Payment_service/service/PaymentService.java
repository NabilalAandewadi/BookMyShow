package com.BookMyShow.Payment_service.service;


import com.ticketing.payment.dto.PaymentCommand;
import com.ticketing.payment.entity.Payment;
import com.ticketing.payment.entity.PaymentStatus;
import com.ticketing.payment.repository.PaymentRepository;
import com.ticketing.payment.kafka.PaymentEventProducer;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer producer;

    @Transactional
    public void processPayment(PaymentCommand command) {

        //  Idempotency check
        if (paymentRepository.existsByBookingId(command.getBookingId())) {
            return;
        }

        //  Step 1: Save initial payment
        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .bookingId(command.getBookingId())
                .amount(command.getAmount())
                .status(PaymentStatus.INITIATED)
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        try {
            //  Step 2: Call gateway (Protected by circuit breaker)
            boolean success = callPaymentGateway(command.getAmount());

            payment.setStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);

        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);

        //  Step 3: Publish event
        producer.publishEvent(payment);
    }

    //  Circuit Breaker protects ONLY external call
    @CircuitBreaker(name = "paymentGateway", fallbackMethod = "gatewayFallback")
    public boolean callPaymentGateway(BigDecimal amount) {

        // Simulate external call latency
        if (Math.random() < 0.3) {
            throw new RuntimeException("Gateway Timeout");
        }

        return true;
    }

    //  Fallback method
    public boolean gatewayFallback(BigDecimal amount, Throwable ex) {

        System.out.println("Payment gateway down. Fallback triggered.");
        return false;  // mark payment as FAILED
    }
}