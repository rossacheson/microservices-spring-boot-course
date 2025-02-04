package com.rossacheson.accounts.service.impl;

import com.rossacheson.accounts.constants.AccountsConstants;
import com.rossacheson.accounts.dto.CustomerDto;
import com.rossacheson.accounts.entity.Account;
import com.rossacheson.accounts.entity.Customer;
import com.rossacheson.accounts.exception.CustomerAlreadyExistsException;
import com.rossacheson.accounts.mapper.CustomerMapper;
import com.rossacheson.accounts.repository.AccountRepository;
import com.rossacheson.accounts.repository.CustomerRepository;
import com.rossacheson.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
    private AccountRepository accountRepository;
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

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");

        Customer savedCustom = customerRepository.save(customer);
        Account account = createNewAccount(savedCustom);
        accountRepository.save(account);
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
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }
}
