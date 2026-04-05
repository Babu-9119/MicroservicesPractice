package com.letsbuild.loans.service.impl;

import com.letsbuild.loans.constants.LoansConstants;
import com.letsbuild.loans.dto.LoansDto;
import com.letsbuild.loans.entity.Loans;
import com.letsbuild.loans.exception.LoanAlreadyExistsException;
import com.letsbuild.loans.exception.ResourceNotFoundException;
import com.letsbuild.loans.mapper.LoansMapper;
import com.letsbuild.loans.repository.LoansRepository;
import com.letsbuild.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    /**
     *
     * @param mobileNumber - mobile number of a customer
     */
    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);

        if(optionalLoans.isPresent()){

            throw new LoanAlreadyExistsException("Loan already exist with the given mobile number "+mobileNumber);
        }

//        System.out.println("created date" +createNewLoan(mobileNumber).getCreatedAt());
        Loans newLoan = createNewLoan(mobileNumber);
        loansRepository.save(newLoan);

    }


    private Loans createNewLoan(String mobileNumber){

        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setCreatedAt(LocalDateTime.now());
        newLoan.setCreatedBy("ADMIN");

        return newLoan;
    }


    /**
     *
     * @param mobileNumber - input mobile number
     * @return loan details based on a given input mobile number
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","Mobile Number",mobileNumber)
        );


        return LoansMapper.mapToLoansDto(loans,new LoansDto());
    }

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean value indicating weather loan update is successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan","Mobile Number",loansDto.getMobileNumber())
        );

        LoansMapper.mapToLoans(loansDto,loans);
        loansRepository.save(loans);

        return true;
    }

    /**
     *
     * @param mobileNumber - mobile number of a customer
     * @return boolean value indicating weather loan delete is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {

        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan","Mobile Number",mobileNumber)
        );

        loansRepository.deleteById(loans.getLoanId());

        return true;
    }



}
