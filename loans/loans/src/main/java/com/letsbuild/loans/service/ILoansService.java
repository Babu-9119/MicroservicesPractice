package com.letsbuild.loans.service;

import com.letsbuild.loans.dto.LoansDto;

public interface ILoansService {

    /**
     *
     * @param mobileNumber - mobile number of a customer
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber - input mobile number
     * @return loan details based on a given input mobile number
     */
    LoansDto fetchLoan(String mobileNumber);

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean value indicating weather loan update is successful or not
     */
    boolean updateLoan(LoansDto loansDto);

    /**
     *
     * @param mobileNumber - mobile number of a customer
     * @return boolean value indicating weather loan delete is successful or not
     */

    boolean deleteLoan(String mobileNumber);
}
