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

@RestController
@RequestMapping("/api/person")
public class PersonRestController {
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logOut(HttpServletResponse res, HttpServletRequest req){
        Cookie invalidatedCookie = new Cookie("authorization_token", null);
        // Set the max age of the cookie to 0 to invalidate it
        invalidatedCookie.setMaxAge(0);

        // Set the cookie's path to match the original cookie's path
        invalidatedCookie.setPath("/");
        invalidatedCookie.setHttpOnly(true);
        invalidatedCookie.setSecure(true);

        // Add the cookie to the response
        res.addCookie(invalidatedCookie);

        ApiResponse response = new ApiResponse();
        response.setSuccess(true);
        response.setMessage("Logout Successfully");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isLogout", "true")
                .body(response);
    }
}
