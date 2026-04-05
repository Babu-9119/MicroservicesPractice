package com.letsbuild.accounts.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Customer name cannot be null")
    @Size(min = 5, max = 25, message = "Customer name should have minimum 5 characters and maximum 25 characters")
    private String name;

    @NotEmpty(message = "Customer email cannot be null")
    @Email(message = "Email is not in valid format")
    private String email;

    @NotEmpty(message = "Customer mobile number cannot be null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number mush have 10 didgits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
