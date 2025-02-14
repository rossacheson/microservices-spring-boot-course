package com.rossacheson.accounts.service;

import com.rossacheson.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {

    /**
     *
     * @param correlationId - Input Correlation Id
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetails(String correlationId, String mobileNumber);
}
