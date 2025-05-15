package com.example.loanpaymentsystem.loan.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
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
	private Long loanId;
	private BigDecimal loanAmount;
	private BigDecimal remainingBalance;
	private Integer term;
	private LoanStatus status;
	
	public enum LoanStatus {
		ACTIVE, SETTLED
	}
}
