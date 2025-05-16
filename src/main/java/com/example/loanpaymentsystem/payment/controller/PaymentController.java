package com.example.loanpaymentsystem.payment.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loanpaymentsystem.payment.model.Payment;
import com.example.loanpaymentsystem.payment.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping
	public ResponseEntity<Payment> makePayment(@Valid @RequestBody PaymentRequest request) {
		Payment payment = paymentService.makePayment(request.getLoanId(), request.getPaymentAmount());
		return new ResponseEntity<>(payment, HttpStatus.CREATED);
	}

	private static class PaymentRequest {
		private Long loanId;
		private BigDecimal paymentAmount;

		public Long getLoanId() {
			return loanId;
		}

		public void setLoanId(Long loanId) {
			this.loanId = loanId;
		}

		public BigDecimal getPaymentAmount() {
			return paymentAmount;
		}

		public void setPaymentAmount(BigDecimal paymentAmount) {
			this.paymentAmount = paymentAmount;
		}

		

	}

}