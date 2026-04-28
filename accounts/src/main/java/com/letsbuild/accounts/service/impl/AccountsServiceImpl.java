package com.letsbuild.accounts.service.impl;

import com.letsbuild.accounts.constants.AccountsConstants;
import com.letsbuild.accounts.dto.AccountsDto;
import com.letsbuild.accounts.dto.CustomerDto;
import com.letsbuild.accounts.entity.Accounts;
import com.letsbuild.accounts.entity.Customer;
import com.letsbuild.accounts.exception.CustomerAlreadyExistsException;
import com.letsbuild.accounts.exception.ResourceNotFoundException;
import com.letsbuild.accounts.mapper.AccountsMapper;
import com.letsbuild.accounts.mapper.CustomerMapper;
import com.letsbuild.accounts.repository.AccountsRepository;
import com.letsbuild.accounts.repository.CustomerRepository;
import com.letsbuild.accounts.service.IAccountsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /***
     *
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber " +customerDto.getMobileNumber());

        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("TestAdmin");

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createAccount(savedCustomer));

    }



    private Accounts createAccount(Customer customer){
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        account.setAccountNumber(randomAccNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("TestAdmin");

        return account;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Accounts","Customer id", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapTOAccountsDto(accounts, new AccountsDto()));

        return customerDto;
    }

    /**
     * @param customerDto
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();

        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account","Account Number",accountsDto.getAccountNumber().toString()));

            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();

            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer","Customer id",customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);

            isUpdated = true;
        }

        return isUpdated;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    @Transactional
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobile number",mobileNumber)
        );

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }


}
