package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/")
public class AdminController extends ClientController {
    @Override
    public boolean logIn(@RequestBody LogInDOT logInDOT) {
        return adminService.logIn("admin@admin.com", "admin");
    }

    @PostMapping("addcompany")
        public ResponseEntity<Company> addCompany(@RequestBody Company company){
        return new ResponseEntity<>(adminService.addCompany(company), HttpStatus.OK);
    }

    @PutMapping("updatecompany")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company){
        return new ResponseEntity<>(adminService.updateCompany(company),HttpStatus.OK);
    }

    @DeleteMapping("deletecompany/{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long companyId){
        adminService.deleteCompany(companyId);
        return new ResponseEntity<>("company was deleted",HttpStatus.OK);
    }

    @GetMapping("/company/getall")
    public ResponseEntity<List<Company>> getAllCompanies(){
        return  new ResponseEntity<>(adminService.getAllCompanies(),HttpStatus.OK);
    }
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Company> getOneCompany(@PathVariable Long companyId){
        return  new ResponseEntity<>(adminService.getOneCompany(companyId),HttpStatus.OK);
    }

    @PostMapping("/customer/")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        return  new ResponseEntity<>(adminService.addCustomer(customer),HttpStatus.OK);
    }

    @PutMapping("/customer/")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer){
        return new ResponseEntity<>(adminService.updateCustomer(customer),HttpStatus.OK);
    }
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId){
        adminService.deleteCustomer(customerId);
        return new ResponseEntity<>("customer"+customerId+" was deleted",HttpStatus.OK);
    }

    @GetMapping("/customer/getall")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return  new ResponseEntity<>(adminService.getAllCustomers(),HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable Long customerId){
        return  new ResponseEntity<>(adminService.getOneCustomer(customerId),HttpStatus.OK);
    }

}
