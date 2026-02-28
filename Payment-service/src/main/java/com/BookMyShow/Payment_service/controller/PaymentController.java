package com.BookMyShow.Payment_service.controller;



import com.ticketing.payment.dto.PaymentCommand;
import com.ticketing.payment.dto.RefundCommand;
import com.ticketing.payment.entity.Payment;
import com.ticketing.payment.repository.PaymentRepository;
import com.ticketing.payment.service.PaymentService;
import com.ticketing.payment.service.RefundService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final RefundService refundService;
    private final PaymentRepository paymentRepository;


    @PostMapping("/process")
    public ResponseEntity<String> processPayment(
            @RequestBody PaymentCommand command) {

        paymentService.processPayment(command);

        return ResponseEntity.ok("Payment processing started");
    }

    @PostMapping("/refund")
    public ResponseEntity<String> refund(
            @RequestBody RefundCommand command) {

        refundService.refund(command);

        return ResponseEntity.ok("Refund initiated");
    }


    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getPaymentByBooking(
            @PathVariable UUID bookingId) {

        Payment payment = paymentRepository
                .findByBookingId(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));

        return ResponseEntity.ok(payment);
    }

    // 🔹 4️⃣ Health Check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service Running");
    }
}
