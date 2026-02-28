package com.BookMyShow.Payment_service.service;

package com.ticketing.payment.service;

import com.ticketing.payment.dto.RefundCommand;
import com.ticketing.payment.entity.Payment;
import com.ticketing.payment.entity.PaymentStatus;
import com.ticketing.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public void refund(RefundCommand command) {

        Payment payment = paymentRepository
                .findByBookingId(command.getBookingId())
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);
    }
}
