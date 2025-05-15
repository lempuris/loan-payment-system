package com.example.loanpaymentsystem.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loanpaymentsystem.loan.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
