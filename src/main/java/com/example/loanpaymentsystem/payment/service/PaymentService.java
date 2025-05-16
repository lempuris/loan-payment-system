package com.example.loanpaymentsystem.payment.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.loanpaymentsystem.loan.service.LoanService;
import com.example.loanpaymentsystem.payment.model.Payment;
import com.example.loanpaymentsystem.payment.repository.PaymentRepository;

@Service
public class PaymentService {

	private PaymentRepository paymentRepository;
	private LoanService loanService;

	@Autowired
	public PaymentService(PaymentRepository paymentRepository, LoanService loanService) {
		this.paymentRepository = paymentRepository;
		this.loanService = loanService;
	}
	
	@Transactional
	public Payment makePayment(Long loanId, BigDecimal amount) {
		// Update loan balance first (this will validate the loan exists and payment
		// amount is valid)

		loanService.updateLoanBalance(loanId, amount);

		Payment payment = Payment.builder().loanId(loanId).paymentAmount(amount).paymentDate(LocalDateTime.now())
				.build();

		return paymentRepository.save(payment);
	}

}
