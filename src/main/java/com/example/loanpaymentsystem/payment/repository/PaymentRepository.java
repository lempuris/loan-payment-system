package com.example.loanpaymentsystem.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loanpaymentsystem.payment.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByLoanId(Long loanId);

}
