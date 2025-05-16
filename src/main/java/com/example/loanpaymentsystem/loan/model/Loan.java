package com.example.loanpaymentsystem.loan.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

	public enum LoanStatus {
		ACTIVE, SETTLED
	}
}
