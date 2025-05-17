package com.example.loanpaymentsystem.loan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.aopalliance.intercept.Invocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.loanpaymentsystem.loan.model.Loan;
import com.example.loanpaymentsystem.loan.repository.LoanRepository;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class LoanServiceTest {
	
	@Mock
	private LoanRepository loanRepository;
	
	@InjectMocks
	private LoanService loanService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void createLoan_ShouldCreateLoanSuccessfully() {
		BigDecimal amount = new BigDecimal("1000.00");
		Integer term = 12;
		
		Loan expectedLoan = Loan.builder()
							.loanId(1L)
							.loanAmount(amount)
							.term(term)
							.remainingBalance(amount)
							.status(Loan.LoanStatus.ACTIVE)
							.build();
		
		when(loanRepository.save(any(Loan.class))).thenReturn(expectedLoan);
		
		Loan result = loanService.createLoan(amount, term);
		
		assertNotNull(result);
		assertEquals(expectedLoan.getLoanId(), result.getLoanId());
		assertEquals(expectedLoan.getLoanAmount(), result.getLoanAmount());
		assertEquals(expectedLoan.getRemainingBalance(), result.getRemainingBalance());
        assertEquals(expectedLoan.getTerm(), result.getTerm());
        assertEquals(expectedLoan.getStatus(), result.getStatus());
        
        verify(loanRepository, times(1)).save(any(Loan.class));

	}
	
	@Test
	void getLoanById_WhenLoanExists_ShouldReturnLoan() {
		Long loanId = 1L;
		Loan expectedLoan = Loan.builder()
                .loanId(loanId)
                .loanAmount(new BigDecimal("1000.00"))
                .remainingBalance(new BigDecimal("1000.00"))
                .term(12)
                .status(Loan.LoanStatus.ACTIVE)
                .build();
		
		when(loanRepository.findById(loanId)).thenReturn(Optional.of(expectedLoan));
		
		Loan result = loanService.getLoanById(loanId);
		
		assertNotNull(result);
		assertEquals(expectedLoan.getLoanId(), result.getLoanId());
		
		verify(loanRepository, times(1)).findById(loanId);
	}
	
	@Test
	void getLoanById_WhenLoanDoesNotExist_ShouldThrowException() {
		Long loanId = 1L;
		when(loanRepository.findById(loanId)).thenReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> loanService.getLoanById(loanId));
		verify(loanRepository, times(1)).findById(loanId);
	}
	
	@Test
    void updateLoanBalance_WhenValidPayment_ShouldUpdateBalance() {
		Long loanId = 1L;
		BigDecimal initialBalance = new BigDecimal("1000.00");
		BigDecimal paymentAmount = new BigDecimal("200.00");
		BigDecimal expectedRemainingBalance = new BigDecimal("800.00");
		
		Loan loan = Loan.builder()
                .loanId(loanId)
                .loanAmount(initialBalance)
                .remainingBalance(initialBalance)
                .term(12)
                .status(Loan.LoanStatus.ACTIVE)
                .build();
		
		when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
		when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		
		Loan result = loanService.updateLoanBalance(loanId, paymentAmount);
		
		assertNotNull(result);
        assertEquals(expectedRemainingBalance, result.getRemainingBalance());
        assertEquals(Loan.LoanStatus.ACTIVE, result.getStatus());
        
        verify(loanRepository, times(1)).findById(loanId);
        verify(loanRepository, times(1)).save(any(Loan.class));
	}
	
	@Test
    void updateLoanBalance_WhenPaymentExceedsBalance_ShouldThrowException() {
        // Arrange
        Long loanId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal paymentAmount = new BigDecimal("1200.00");
        
        Loan loan = Loan.builder()
                .loanId(loanId)
                .loanAmount(initialBalance)
                .remainingBalance(initialBalance)
                .term(12)
                .status(Loan.LoanStatus.ACTIVE)
                .build();
        
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> loanService.updateLoanBalance(loanId, paymentAmount));
        
        verify(loanRepository, times(1)).findById(loanId);
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void updateLoanBalance_WhenFullPayment_ShouldSettleLoan() {
        // Arrange
        Long loanId = 1L;
        BigDecimal initialBalance = new BigDecimal("1000.00");
        BigDecimal paymentAmount = new BigDecimal("1000.00");
        
        Loan loan = Loan.builder()
                .loanId(loanId)
                .loanAmount(initialBalance)
                .remainingBalance(initialBalance)
                .term(12)
                .status(Loan.LoanStatus.ACTIVE)
                .build();
        
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        Loan result = loanService.updateLoanBalance(loanId, paymentAmount);
        
        // Assert
        assertNotNull(result);
        assertEquals(0, result.getRemainingBalance().compareTo(BigDecimal.ZERO));
        assertEquals(Loan.LoanStatus.SETTLED, result.getStatus());
        
        verify(loanRepository, times(1)).findById(loanId);
        verify(loanRepository, times(1)).save(any(Loan.class));
    }
	

}
