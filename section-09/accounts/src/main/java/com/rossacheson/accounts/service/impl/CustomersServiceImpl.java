package com.rossacheson.accounts.service.impl;

import com.rossacheson.accounts.dto.AccountDto;
import com.rossacheson.accounts.dto.CardDto;
import com.rossacheson.accounts.dto.CustomerDetailsDto;
import com.rossacheson.accounts.dto.LoanDto;
import com.rossacheson.accounts.entity.Account;
import com.rossacheson.accounts.entity.Customer;
import com.rossacheson.accounts.exception.ResourceNotFoundException;
import com.rossacheson.accounts.mapper.AccountMapper;
import com.rossacheson.accounts.mapper.CustomerMapper;
import com.rossacheson.accounts.repository.AccountsRepository;
import com.rossacheson.accounts.repository.CustomerRepository;
import com.rossacheson.accounts.service.ICustomersService;
import com.rossacheson.accounts.service.client.CardsFeignClient;
import com.rossacheson.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param correlationId - Input Correlation Id
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String correlationId, String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account account = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoanDto(loanDtoResponseEntity.getBody());

        ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardDto(cardDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}