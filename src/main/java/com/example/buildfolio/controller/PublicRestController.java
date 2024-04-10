package com.example.buildfolio.controller;

import com.example.buildfolio.model.*;
import com.example.buildfolio.repository.PersonRepo;
import com.example.buildfolio.security.JwtTokenHelper;
import com.example.buildfolio.security.PersonDetailService;
import com.example.buildfolio.security.interf.MyUserDetails;
import com.example.buildfolio.service.EmailService;
import com.example.buildfolio.service.OTPService;
import com.example.buildfolio.service.PersonService;
import com.example.buildfolio.service.PortfolioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PublicRestController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private PersonDetailService personDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PortfolioService portfolioService;

    @Value("${cookie.samesite}")
    private String same_site;

    @PostMapping("/user_login")
    public ResponseEntity<JwtAuthResponse> createToken(@Valid @RequestBody AuthCredential authCredential,
                                                       HttpServletRequest req, HttpServletResponse res) {

        try{
            this.authenticate(authCredential.getUserNameOrEmail(), authCredential.getPass());
        }
        catch (Exception e){
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setSuccess(false);
            jwtAuthResponse.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(jwtAuthResponse);
        }
        MyUserDetails userDetails = this.personDetailService
                .loadUserByUsername(authCredential.getUserNameOrEmail());
        List<GrantedAuthority> auth = (List<GrantedAuthority>) userDetails.getAuthorities();
        String userRole = auth.get(0).getAuthority();
        if(!userRole.equalsIgnoreCase("ROLE_ADMIN") && !userRole.equalsIgnoreCase("ROLE_NORMAL")){
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setSuccess(false);
            jwtAuthResponse.setMessage("Invalid User");
            return ResponseEntity.status(200).body(jwtAuthResponse);
        }
        String token = this.jwtTokenHelper.generateToken(authCredential.getUserNameOrEmail());
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken("Bearer_"+token);
        ResponseCookie responseCookie = ResponseCookie.from("authorization_token","Bearer_"+token)
                .path("/").sameSite(same_site).httpOnly(true).secure(true).build();
        response.setSuccess(true);
        response.setRole(userDetails.getRole());
        response.setUserName(userDetails.getUsername());
        response.setMessage("Login SuccessFully");
        return ResponseEntity.status(200).header(HttpHeaders.SET_COOKIE,responseCookie.toString()).body(response);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password !!");
        }
    }

    @PostMapping("/save/user")
    public ResponseEntity<ApiResponse>
    savePerson(@RequestBody @Valid Person person,
               @RequestParam String otp, HttpServletRequest request){
        int status;
        ApiResponse apiResponse = new ApiResponse();
        if(personService.checkUserExistOrNot(person.getEmail(),person.getUserName())){
            return ResponseEntity.status(400).body(new ApiResponse("UserName Or Email Already Exist",false));
        }
        ResponseEntity<ApiResponse> res = verifyOTP(otp,person.getEmail(),request);
        if(!res.getBody().isSuccess()){
            return res;
        }
        if(personService.savePerson(person)){
            apiResponse.setSuccess(true);
            apiResponse.setMessage("Person Saved Successfully");
            status = 201;
        }
        else{
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Something went wrong. Please try again");
            status = 200;
        }
        return ResponseEntity.status(status).body(apiResponse);
    }

    @GetMapping("/verify/user-status")
    public ResponseEntity<ApiResponse> checkUserStatus(@RequestParam String email, @RequestParam String username){
        if(personRepo.doesEmailExist(email)){
            return ResponseEntity.status(200).body(new ApiResponse("Email Already Exist",false));
        }
        else if(personRepo.doesUserNameExist(username)){
            return ResponseEntity.status(200).body(new ApiResponse("User Name Already Exist",false));
        }
        return ResponseEntity.status(200).body(new ApiResponse("UserName And Email Are Available",true));
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOtp(@RequestParam String email, HttpSession session, HttpServletResponse res){
        String otp = OTPService.generateOtp();
        boolean success = emailService.sendOtpEmail(email,otp);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(success);
        if(success){
            apiResponse.setMessage("OTP Sent Successfully");
            session.setAttribute(email+"_otp",otp);
        }
        else{
            apiResponse.setMessage("Something Went Wrong");
        }
        return ResponseEntity.status(200).body(apiResponse);//header(HttpHeaders.SET_COOKIE,responseCookie.toString()).body(apiResponse);
    }

    @GetMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOTP(@RequestParam String otp, @RequestParam String email, HttpServletRequest request){
        String generatedOTP = (String)request.getSession().getAttribute(email+"_otp");
        if(!otp.equals(generatedOTP)){
            return ResponseEntity.status(200).body(new ApiResponse("Invalid OTP",false));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Verified Successfully",true));
    }

    @PostMapping("/change-pass")
    public ResponseEntity<ApiResponse> changePassByPhoneNumber
            (@RequestBody @Valid ResetPassword resetPassword , HttpServletRequest req, HttpServletResponse res){
        String generatedOTP = (String)req.getSession().getAttribute(resetPassword.getEmail()+"_otp");
        if(!resetPassword.getOtp().equals(generatedOTP)){
            return ResponseEntity.status(200).body(new ApiResponse("Invalid OTP",false));
        }
        if(resetPassword.getConfirmPass() == null || resetPassword.getConfirmPass().length() <= 4){
            return ResponseEntity.status(200).body(new ApiResponse("Check Your Password",false));
        }

        if(!resetPassword.getNewPass().equals(resetPassword.getConfirmPass())){
            return ResponseEntity.status(200).body(new ApiResponse("New Pass And Confirm Pass Must be Same",false));
        }

        if(!personService.changePassByEmail(resetPassword.getEmail(),resetPassword.getConfirmPass())){
            return ResponseEntity.status(400).body(new ApiResponse("Something went wrong",false));
        }
        req.getSession().invalidate();
        Cookie cookie = new Cookie("JSESSIONID",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        res.addCookie(cookie);
        return ResponseEntity.status(200).body(new ApiResponse("Password Change Successfully",true));
    }

    @GetMapping("/portfolio/get")
    public ResponseEntity<Portfolio> getPortfolio(@RequestParam String username){
        Portfolio portfolio = portfolioService.getPortfolio(username);
        return ResponseEntity.status(200).body(portfolio);
    }

    @PostMapping("/contact/send-msg")
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody @Valid UserContact userContact,
                                                      @RequestParam String username){
        String email = personRepo.getEmailByUsername(username);
        if(email == null || email.equals("")){
            return ResponseEntity.status(404).body(new ApiResponse("Email Not Found",false));
        }
        boolean success = emailService.sendMessage(userContact,email);
        ApiResponse response = new ApiResponse();
        response.setSuccess(success);
        if(success){
            response.setMessage("Message Sent Successfully");
        }
        else{
            response.setMessage("Failed To Send Message");
        }
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/validate/email")
    public ResponseEntity<ApiResponse> validateEmail(@RequestParam String email){
        boolean success = personRepo.doesEmailExist(email);
        ApiResponse apiResponse =
                new ApiResponse(success ? "Email exist" : "Email does not exist",success);
        return ResponseEntity.status(200).body(apiResponse);
    }
}