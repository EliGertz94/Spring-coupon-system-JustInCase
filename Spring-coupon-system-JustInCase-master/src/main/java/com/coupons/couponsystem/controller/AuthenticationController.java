package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.DOT.ResponseDTO;
import com.coupons.couponsystem.exception.CouponSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("api/authentication")
public class AuthenticationController extends ClientController {
    @PostMapping("/login-admin")
    public ResponseEntity<ResponseDTO> logInAdmin(@RequestBody  LogInDOT logInDOT) {
    try{
        System.out.println("admin login ");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInDOT.getUsername(),
                        logInDOT.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token= tokenProvider.generateToken(authentication);


             adminService.logIn(logInDOT.getUsername(),logInDOT.getPassword());

             return new ResponseEntity<>(new ResponseDTO(token),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PostMapping("/login-company")
    public  ResponseEntity<ResponseDTO> logInCompany(@RequestBody LogInDOT logInDOT){
    try{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInDOT.getUsername(),
                        logInDOT.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= tokenProvider.generateToken(authentication);

             companyService.logIn(logInDOT.getUsername(), logInDOT.getPassword());

        return new ResponseEntity<>(new ResponseDTO(token),HttpStatus.OK);
        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());

        }

    }

    @PostMapping("/login-customer")
    public ResponseEntity<ResponseDTO> logInCustomer(@RequestBody LogInDOT logInDOT){

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInDOT.getUsername(),
                            logInDOT.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token= tokenProvider.generateToken(authentication);

             customerService.logIn(logInDOT.getUsername(),logInDOT.getPassword());

             return new ResponseEntity<>(new ResponseDTO(token),HttpStatus.OK);

        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());

        }

    }


}
