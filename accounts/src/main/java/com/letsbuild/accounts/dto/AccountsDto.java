package com.letsbuild.accounts.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Customer mobile number cannot be null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number mush have 10 didgits")
    private Long accountNumber;

    @NotEmpty(message = "Account type cannot be null")
    private String accountType;

    @NotEmpty(message = "Branch address cannot be null")
    private String branchAddress;
}
