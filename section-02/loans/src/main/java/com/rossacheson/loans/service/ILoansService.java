package com.rossacheson.loans.service;

import com.rossacheson.loans.dto.LoanDto;

public interface ILoansService {
    /**
     * Creates a new loan for the given mobile phone number.
     *
     * @param mobileNumber the mobile number to be associated with the loan
     */
    void createLoan(String mobileNumber);
//    /**
//     * Retrieves the account details for the given mobile number.
//     *
//     * @param mobileNumber the mobile phone number associated with the account
//     * @return the account details
//     */
//    LoanDto fetchLoan(String mobileNumber);
//
//    /**
//     * Updates the account details for the given customer.
//     *
//     * @param customerDto the data transfer object containing customer details such as name, email, and mobile number
//     * @return true if account was updated successfully, false otherwise
//     */
//    boolean updateLoan(LoanDto customerDto);
//
//    /**
//     * Deletes the account associated with the given mobile number.
//     *
//     * @param mobileNumber the mobile phone number associated with the account
//     * @return true if account was deleted successfully, false otherwise
//     */
//    boolean deleteLoan(String mobileNumber);
}
