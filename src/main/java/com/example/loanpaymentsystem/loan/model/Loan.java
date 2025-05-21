package com.example.loanpaymentsystem.loan.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;
	
	@Column(nullable = false)
	private BigDecimal loanAmount;
	
	@Column(nullable = false)
	private BigDecimal remainingBalance;
	
	@Column(nullable = false)
	private Integer term;
	
	@Enumerated(EnumType.STRING)
	private LoanStatus status;

	// Constructor to replace builder pattern
	public Loan(BigDecimal loanAmount, BigDecimal remainingBalance, Integer term, LoanStatus status) {
		this.loanAmount = loanAmount;
		this.remainingBalance = remainingBalance;
		this.term = term;
		this.status = status;
	}
	
	// Constructor with ID for complete entity creation
	public Loan(Long loanId, BigDecimal loanAmount, BigDecimal remainingBalance, Integer term, LoanStatus status) {
		this.loanId = loanId;
		this.loanAmount = loanAmount;
		this.remainingBalance = remainingBalance;
		this.term = term;
		this.status = status;
	}

	public enum LoanStatus {
		ACTIVE, SETTLED
	}
}