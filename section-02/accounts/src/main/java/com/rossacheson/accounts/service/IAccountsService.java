package com.rossacheson.accounts.service;

import com.rossacheson.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     * Creates a new account for the given customer.
     *
     * @param customerDto the data transfer object containing customer details such as name, email, and mobile number
     */
    void createAccount(CustomerDto customerDto);
}
