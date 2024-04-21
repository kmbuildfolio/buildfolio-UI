package com.example.buildfolio.controller;

import com.example.buildfolio.model.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

@RestController
@RequestMapping("/api/person")
public class PersonRestController {

    @Value("${cookie.samesite}")
    private String same_site;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logOut(HttpServletResponse res, HttpServletRequest req){
	ResponseCookie responseCookie = ResponseCookie.from("authorization_token",null)
	.maxAge(0).path("/").sameSite(same_site).httpOnly(true).secure(true).build();

        ApiResponse response = new ApiResponse();
        response.setSuccess(true);
        response.setMessage("Logout Successfully");
        return ResponseEntity.status(200).header(HttpHeaders.SET_COOKIE,responseCookie.toString()).body(response);
    }
}
