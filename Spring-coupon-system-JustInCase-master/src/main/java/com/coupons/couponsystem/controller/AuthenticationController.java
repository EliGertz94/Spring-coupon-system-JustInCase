package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DTO.LogInDOT;
import com.coupons.couponsystem.DTO.ResponseDTO;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.security.SecuredUser;
import com.coupons.couponsystem.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("api/authentication")
public class AuthenticationController extends ClientController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @PostMapping("/login-admin")
    public ResponseEntity<ResponseDTO> logInAdmin(@RequestBody  LogInDOT logInDOT) {
    try{
        System.out.println(logInDOT.getUsername()+
                logInDOT.getPassword());
        System.out.println("admin login ");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInDOT.getUsername(),
                        logInDOT.getPassword()));

        SecuredUser user = (SecuredUser) userDetailsService.loadUserByUsername(logInDOT.getUsername());

        String token= tokenProvider.generateToken(user);

        GrantedAuthority grantedAuth = user.getAuthorities().stream().toList().get(0);

        adminService.logIn(logInDOT.getUsername(),logInDOT.getPassword());

        return new ResponseEntity<>(new ResponseDTO(token,grantedAuth),HttpStatus.OK);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());
        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());
        }
    }

    @PostMapping("/login-company")
    public  ResponseEntity<ResponseDTO> logInCompany(@RequestBody LogInDOT logInDOT){
    try{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInDOT.getUsername(),
                        logInDOT.getPassword()
                )
        );


        SecuredUser user = (SecuredUser) userDetailsService.loadUserByUsername(logInDOT.getUsername());

        String token= tokenProvider.generateToken(user);

        companyService.logIn(logInDOT.getUsername(), logInDOT.getPassword());

        GrantedAuthority grantedAuth = user.getAuthorities().stream().toList().get(0);

        return new ResponseEntity<>(new  ResponseDTO(token,grantedAuth),HttpStatus.OK);

        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
    } catch (CouponSystemException e) {
        throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());

        }

    }

    @PostMapping("/login-customer")
    public ResponseEntity<ResponseDTO> logInCustomer(@RequestBody LogInDOT logInDOT){

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            logInDOT.getUsername(),
                            logInDOT.getPassword()
                    )
            );

            SecuredUser user = (SecuredUser) userDetailsService.loadUserByUsername(logInDOT.getUsername());

            String token= tokenProvider.generateToken(user);
            customerService.logIn(logInDOT.getUsername(),logInDOT.getPassword());
            GrantedAuthority grantedAuth = user.getAuthorities().stream().toList().get(0);

             return new ResponseEntity<>(new ResponseDTO(token,grantedAuth),HttpStatus.OK);

        }catch (AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());

        }

    }
//    @PostMapping("/add-customer/")
//    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
//        try {
//            return  new ResponseEntity<>(adminService.addCustomer(customer),HttpStatus.OK);
//        } catch (CouponSystemException e) {
//            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());
//
//        }
//    }

}
