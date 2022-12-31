package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.exception.CouponSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController extends ClientController {

    @PostMapping("/login-admin")
    public boolean logInAdmin(@RequestBody  LogInDOT logInDOT) {
    try{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInDOT.getUsername(),
                        logInDOT.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }catch (AuthenticationException e){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
    }


        try {
            return adminService.logIn(logInDOT.getUsername(),logInDOT.getPassword());
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PostMapping("/login-company")
    public boolean logInCompany(@RequestBody LogInDOT logInDOT){
    try{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInDOT.getUsername(),
                        logInDOT.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }catch (AuthenticationException e){
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());
    }

        try {
            return companyService.logIn(logInDOT.getUsername(), logInDOT.getPassword());
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());

        }

    }

    @PostMapping("/login-customer")
    public boolean logInCustomer(@RequestBody LogInDOT logInDOT){

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInDOT.getUsername(),
                            logInDOT.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }

        try {
            return customerService.logIn(logInDOT.getUsername(),logInDOT.getPassword());
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());

        }

    }


}
