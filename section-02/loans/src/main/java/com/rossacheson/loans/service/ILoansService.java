package com.rossacheson.loans.service;

import com.rossacheson.loans.dto.LoanDto;

public interface ILoansService {
    /**
     * Creates a new loan for the given mobile phone number.
     *
     * @param mobileNumber the mobile number to be associated with the loan
     */
    void createLoan(String mobileNumber);
    /**
     * Retrieves the loan details for the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the loan
     * @return the loan details
     */
    LoanDto fetchLoan(String mobileNumber);

    /**
     * Updates the loan details.
     *
     * @param loanDto the data transfer object containing loan details
     * @return true if loan was updated successfully, false otherwise
     */
    boolean updateLoan(LoanDto loanDto);

    /**
     * Deletes the loan associated with the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the loan
     * @return true if loan was deleted successfully, false otherwise
     */
    boolean deleteLoan(String mobileNumber);
}
