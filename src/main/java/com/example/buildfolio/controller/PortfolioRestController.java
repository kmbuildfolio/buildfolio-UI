package com.example.buildfolio.controller;

import com.example.buildfolio.model.ApiResponse;
import com.example.buildfolio.model.Portfolio;
import com.example.buildfolio.security.interf.MyUserDetails;
import com.example.buildfolio.service.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioRestController {
    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> savePortfolio(@Valid @RequestBody Portfolio portfolio, Principal principal){
        if (principal instanceof UsernamePasswordAuthenticationToken == false) {
            throw new RuntimeException("User Not Exist");
        }
        String userName = ((MyUserDetails)((UsernamePasswordAuthenticationToken) principal)
                .getPrincipal()).getUsername();
        ApiResponse apiResponse = new ApiResponse();
        if(portfolioService.savePortfolio(portfolio,userName) != null){
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Portfolio Saved Successfully");
        }
        else{
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Something Went Wrong");
        }
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 500).body(apiResponse);
    }

    @GetMapping("/get")
    public ResponseEntity<Portfolio> getPortfolio(Principal principal){
        if (principal instanceof UsernamePasswordAuthenticationToken == false) {
            throw new RuntimeException("User Not Exist");
        }
        String userName = ((MyUserDetails)((UsernamePasswordAuthenticationToken) principal)
                .getPrincipal()).getUsername();

        Portfolio portfolio = portfolioService.getPortfolio(userName);
        return ResponseEntity.status(200).body(portfolio);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updatePortfolio(@Valid @RequestBody Portfolio portfolio, Principal principal){
        if (principal instanceof UsernamePasswordAuthenticationToken == false) {
            throw new RuntimeException("User Not Exist");
        }
        String userName = ((MyUserDetails)((UsernamePasswordAuthenticationToken) principal)
                .getPrincipal()).getUsername();
        ApiResponse apiResponse = new ApiResponse();
        boolean success = portfolioService.updatePortfolio(userName,portfolio);
        apiResponse.setSuccess(success);
        apiResponse.setMessage(success ? "Update Successfully" : "Cannot Update");
        return ResponseEntity.status(200).body(apiResponse);
    }
}
