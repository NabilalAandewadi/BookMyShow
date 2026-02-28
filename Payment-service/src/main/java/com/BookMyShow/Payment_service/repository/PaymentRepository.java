package com.BookMyShow.Payment_service.repository;

import com.BookMyShow.Payment_service.entity.Payment;

import java.util.UUID;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    boolean existsByBookingId(UUID bookingId);

    Optional<Payment> findByBookingId(UUID bookingId);
}
