package com.rossacheson.loans.service.impl;

import com.rossacheson.loans.constants.LoansConstants;
import com.rossacheson.loans.dto.LoanDto;
import com.rossacheson.loans.entity.Loan;
import com.rossacheson.loans.exception.LoanAlreadyExistsException;
import com.rossacheson.loans.exception.ResourceNotFoundException;
import com.rossacheson.loans.mapper.LoansMapper;
import com.rossacheson.loans.repository.LoansRepository;
import com.rossacheson.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansService implements ILoansService {
    private LoansRepository loansRepository;

    /**
     * Creates a new loan for the given mobile phone number.
     *
     * @param mobileNumber the mobile number to be associated with the loan
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * Retrieves the loan details for the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the loan
     * @return the loan details
     */
    @Override
    public LoanDto fetchLoan(String mobileNumber) {
        Loan loan = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoanDto(loan, new LoanDto());
    }

    /**
     * Updates the loan details.
     *
     * @param loanDto the data transfer object containing loan details
     * @return true if loan was updated successfully, false otherwise
     */
    @Override
    public boolean updateLoan(LoanDto loanDto) {
        Loan loan = loansRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loanDto.getLoanNumber()));
        LoansMapper.mapToLoan(loanDto, loan);
        loansRepository.save(loan);
        return  true;
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }
}
