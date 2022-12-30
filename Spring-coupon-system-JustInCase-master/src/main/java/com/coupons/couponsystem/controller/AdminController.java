package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.security.SecuredUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/admin/")
public class AdminController extends ClientController {
    @Override
    @PostMapping("log-in")
    public boolean logIn(Authentication authentication, LogInDOT logInDOT) {

        SecuredUser user =(SecuredUser) authentication.getPrincipal();

        try {
            return adminService.logIn(user.getUsername(),user.getPassword());
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }

    @PostMapping("addcompany")
        public ResponseEntity<Company> addCompany(@RequestBody Company company){
        try {
            return new ResponseEntity<>(adminService.addCompany(company), HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());

        }
    }

    @PutMapping("updatecompany")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company){
        try {
            return new ResponseEntity<>(adminService.updateCompany(company),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());

        }
    }

    @DeleteMapping("deletecompany/{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long companyId){
        try {
            adminService.deleteCompany(companyId);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        return new ResponseEntity<>("company was deleted",HttpStatus.OK);
    }

    @GetMapping("/company/getall")
    public ResponseEntity<List<Company>> getAllCompanies(){
        return  new ResponseEntity<>(adminService.getAllCompanies(),HttpStatus.OK);
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Company> getOneCompany(@PathVariable Long companyId){
        try {
            return  new ResponseEntity<>(adminService.getOneCompany(companyId),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());

        }
    }

    @PostMapping("/customer/")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        try {
            return  new ResponseEntity<>(adminService.addCustomer(customer),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());

        }
    }

    @PutMapping("/customer/")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
        try {
            return new ResponseEntity<>(adminService.updateCustomer(customer),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());

        }
    }
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId){
        try {
            adminService.deleteCustomer(customerId);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());

        }
        return new ResponseEntity<>("customer"+customerId+" was deleted",HttpStatus.OK);
    }

    @GetMapping("/customer/get-all")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return  new ResponseEntity<>(adminService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable Long customerId){
        try {
            return  new ResponseEntity<>(adminService.getOneCustomer(customerId),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}
