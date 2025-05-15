package com.example.loanpaymentsystem.loan.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.loanpaymentsystem.loan.model.Loan;
import com.example.loanpaymentsystem.loan.model.Loan.LoanStatus;
import com.example.loanpaymentsystem.loan.repository.LoanRepository;

import jakarta.persistence.EntityNotFoundException;

public class LoanService {

	LoanRepository loanRepository;

	@Autowired
	public LoanService(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@Transactional
	public Loan createLoan(BigDecimal amount, int term) {
		Loan loan = Loan.builder().loanAmount(amount).remainingBalance(amount).term(term).status(Loan.LoanStatus.ACTIVE)
				.build();
		return loanRepository.save(loan);

	}

	@Transactional(readOnly = true)
	public Loan getLoanById(Long loanId) {
		return loanRepository.findById(loanId)
				.orElseThrow(() -> new EntityNotFoundException("Loan with id:" + loanId + "not found"));

	}

	@Transactional
	public Loan updateLoanBalance(Long loanId, BigDecimal paymentAmount) {
		Loan loan = getLoanById(loanId);

		if (paymentAmount.compareTo(loan.getRemainingBalance()) > 0) {
			throw new IllegalArgumentException("Payment amount exceeds remaining balance");
		}

		BigDecimal newBalance = loan.getRemainingBalance().subtract(paymentAmount);
		loan.setRemainingBalance(newBalance);

		if (newBalance.compareTo(BigDecimal.ZERO) == 0) {
			loan.setStatus(Loan.LoanStatus.SETTLED);
		}

		return loanRepository.save(loan);

	}

}
