package com.example.loanpaymentsystem.loan.contorller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loanpaymentsystem.loan.model.Loan;
import com.example.loanpaymentsystem.loan.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/loans")
public class LoanController {
	
	@Autowired
	LoanService loanService;
	
	@GetMapping("/{loanId}")
	public ResponseEntity<Loan> getLoan(@PathVariable Long loanId){
		Loan loan = loanService.getLoanById(loanId);
		
		return ResponseEntity.ok(loan);
	}
	
	@PostMapping
	public ResponseEntity<Loan> createLoan(@Valid @RequestBody LoanRequest request){
		Loan loan = loanService.createLoan(request.getLoanAmount(), request.getTerm());
		
		return new ResponseEntity<>(loan, HttpStatus.CREATED);
	}
	
	
	public static class LoanRequest{
		private BigDecimal loanAmount;
		private Integer term;
		
		public BigDecimal getLoanAmount() {
			return loanAmount;
		}
		public void setLoanAmount(BigDecimal loanAmount) {
			this.loanAmount = loanAmount;
		}
		public Integer getTerm() {
			return term;
		}
		public void setTerm(Integer term) {
			this.term = term;
		}
	}
}