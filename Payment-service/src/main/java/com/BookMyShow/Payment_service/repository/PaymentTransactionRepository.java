package com.BookMyShow.Payment_service.repository;

import com.BookMyShow.Payment_service.entity.Payment;
import com.BookMyShow.Payment_service.entity.PaymentTransaction;

import java.util.UUID;


public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID> {
}