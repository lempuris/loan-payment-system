package com.example.loanpaymentsystem.payment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.loanpaymentsystem.loan.model.Loan;
import com.example.loanpaymentsystem.loan.service.LoanService;
import com.example.loanpaymentsystem.payment.model.Payment;
import com.example.loanpaymentsystem.payment.repository.PaymentRepository;

public class PaymentServiceTest {

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	LoanService loanService;

	@InjectMocks
	private PaymentService paymentService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void makePayment_ShouldCreatePaymentAndUpdateLoan() {
		Long loanId = 1L;
		BigDecimal paymentAmount = new BigDecimal("200.00");

		Loan updatedLoan = Loan.builder().loanId(loanId).loanAmount(new BigDecimal("1000.00"))
				.remainingBalance(new BigDecimal("800.00")).term(12).status(Loan.LoanStatus.ACTIVE).build();

		Payment expectedPayment = Payment.builder().paymentId(1L).loanId(loanId).paymentAmount(paymentAmount)
				.paymentDate(LocalDateTime.now()).build();

		when(loanService.updateLoanBalance(loanId, paymentAmount)).thenReturn(updatedLoan);
		when(paymentRepository.save(any(Payment.class))).thenReturn(expectedPayment);

		Payment result = paymentService.makePayment(loanId, paymentAmount);

		assertNotNull(result);
		assertEquals(result.getPaymentAmount(), expectedPayment.getPaymentAmount());
		assertEquals(result.getPaymentId(), expectedPayment.getPaymentId());
		assertEquals(result.getPaymentAmount(), expectedPayment.getPaymentAmount());

		verify(loanService, times(1)).updateLoanBalance(loanId, paymentAmount);
		verify(paymentRepository, times(1)).save(any(Payment.class));

	}

}
