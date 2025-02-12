package com.rossacheson.accounts.service.impl;

import com.rossacheson.accounts.constants.AccountsConstants;
import com.rossacheson.accounts.dto.AccountDto;
import com.rossacheson.accounts.dto.CustomerDto;
import com.rossacheson.accounts.entity.Account;
import com.rossacheson.accounts.entity.Customer;
import com.rossacheson.accounts.exception.CustomerAlreadyExistsException;
import com.rossacheson.accounts.exception.ResourceNotFoundException;
import com.rossacheson.accounts.mapper.AccountMapper;
import com.rossacheson.accounts.mapper.CustomerMapper;
import com.rossacheson.accounts.repository.AccountsRepository;
import com.rossacheson.accounts.repository.CustomerRepository;
import com.rossacheson.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    /**
     * Creates a new account for the given customer.
     *
     * @param customerDto the data transfer object containing customer details such as name, email, and mobile number
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists");
        }

        Customer savedCustom = customerRepository.save(customer);
        Account account = createNewAccount(savedCustom);
        accountsRepository.save(account);
    }

    /**
     * Retrieves the account details for the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the account
     * @return the account details
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () ->  new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account  = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        return customerDto;
    }

    /**
     * Updates the account details for the given customer.
     *
     * @param customerDto the data transfer object containing customer details such as name, email, and mobile number
     * @return true if account was updated successfully, false otherwise
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if(accountDto != null){
            Account account = accountsRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccount(accountDto, account);
            account = accountsRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * Deletes the account associated with the given mobile number.
     *
     * @param mobileNumber the mobile phone number associated with the account
     * @return true if account was deleted successfully, false otherwise
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
