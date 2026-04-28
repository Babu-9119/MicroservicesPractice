package com.letsbuild.accounts.service.impl;

import com.letsbuild.accounts.dto.*;
import com.letsbuild.accounts.entity.Accounts;
import com.letsbuild.accounts.entity.Customer;
import com.letsbuild.accounts.exception.ResourceNotFoundException;
import com.letsbuild.accounts.mapper.AccountsMapper;
import com.letsbuild.accounts.mapper.CustomerMapper;
import com.letsbuild.accounts.repository.AccountsRepository;
import com.letsbuild.accounts.repository.CustomerRepository;
import com.letsbuild.accounts.service.ICustomerService;
import com.letsbuild.accounts.service.client.CardsFeignClient;
import com.letsbuild.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     *
     * @param mobileNumber  - input mobile number
     * @return Customer details based on given mobile number
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account","customer id", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());

        customerDetailsDto.setAccountsDto(AccountsMapper.mapTOAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoan(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
