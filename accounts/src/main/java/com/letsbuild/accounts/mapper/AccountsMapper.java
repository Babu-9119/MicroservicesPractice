package com.letsbuild.accounts.mapper;

import com.letsbuild.accounts.dto.AccountsDto;
import com.letsbuild.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapTOAccountsDto(Accounts accounts, AccountsDto accountsDto){

        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());

        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts){

        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());

        return  accounts;
    }
}
