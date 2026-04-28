package com.letsbuild.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold cutomer, account, cards and loans details"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer",example = "John"
    )
    @NotEmpty(message = "Customer name cannot be null")
    @Size(min = 5, max = 25, message = "Customer name should have minimum 5 characters and maximum 25 characters")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "test@gmail.com"
    )
    @NotEmpty(message = "Customer email cannot be null")
    @Email(message = "Email is not in valid format")
    private String email;

    @Schema(
            description = "Mobile number of the customer", example = "9673673637"
    )
    @NotEmpty(message = "Customer mobile number cannot be null")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number mush have 10 didgits")
    private String mobileNumber;

    @Schema(
            description = "Accounts details of the customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Loans details of the customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "Card details of the customer"
    )
    private CardsDto cardsDto;
}
