package com.example.loanpaymentsystem.payment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Payment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    
    @Column(nullable = false)
    private Long loanId;
    
    @Column(nullable = false)
    private BigDecimal paymentAmount;
    
    @Column(nullable = false)
    private LocalDateTime paymentDate;
    
    public Payment(Long loanId, BigDecimal paymentAmount, LocalDateTime paymentDate) {
        this.loanId = loanId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }
    
    public Payment(Long paymentId, Long loanId, BigDecimal paymentAmount, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.loanId = loanId;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }
}