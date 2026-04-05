package com.letsbuild.loans.controller;

import com.letsbuild.loans.constants.LoansConstants;
import com.letsbuild.loans.dto.LoansDto;
import com.letsbuild.loans.dto.ResponseDto;
import com.letsbuild.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class LoansController {

    private ILoansService iLoansService;

    @RequestMapping("/hello")
    public String sayHello(){
        return "Hello";
    }

    @PostMapping("/createLoan")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber){
        iLoansService.createLoan(mobileNumber);
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @GetMapping("/fetchLoan")
    public ResponseEntity<LoansDto> fetchLoan(@RequestParam String mobileNumber){

        LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansDto);
    }

    @PutMapping("/updateLoan")
    public ResponseEntity<ResponseDto> updateLoan(@RequestBody LoansDto loansDto){
        boolean isUpdated = iLoansService.updateLoan(loansDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/deleteLoan")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam String mobileNumber){
        boolean isDeleted = iLoansService.deleteLoan(mobileNumber);

        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200,LoansConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417,LoansConstants.MESSAGE_417_DELETE));
        }

    }
}
