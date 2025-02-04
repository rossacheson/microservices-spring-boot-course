package com.rossacheson.accounts.service;

import com.rossacheson.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     * Creates a new account for the given customer.
     *
     * @param customerDto the data transfer object containing customer details such as name, email, and mobile number
     */
    void createAccount(CustomerDto customerDto);
    /**
     * Retrieves the account details for the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the account
     * @return the account details
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     * Updates the account details for the given customer.
     *
     * @param customerDto the data transfer object containing customer details such as name, email, and mobile number
     * @return true if account was updated successfully, false otherwise
     */
    boolean updateAccount(CustomerDto customerDto);
}
