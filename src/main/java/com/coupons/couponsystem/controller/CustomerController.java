package com.coupons.couponsystem.controller;


import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.service.AdminService;
import com.coupons.couponsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/customer/")
public class CustomerController {

    @Autowired
    AdminService adminService;

    @Autowired
    CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody LogInDOT logInDOT){
        try{
            String messege =  customerService.logIn(logInDOT.getEmail(),logInDOT.getPassword())+" ";
            return new ResponseEntity<>(messege,HttpStatus.OK);
        }catch(CouponSystemException e) {
            throw new ResponseStatusException(e.getStatus(),e.getMessage(),e);
        }
    }


    @PostMapping()
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        return new ResponseEntity<>(adminService.addCustomer(customer), HttpStatus.OK);
    }

    @PostMapping("/coupon/{couponId}")
    public ResponseEntity<String> purchaseCoupon(@PathVariable long couponId){
        customerService.purchaseCoupon(couponId);
        return new ResponseEntity<>("coupon id "+couponId+ "  was purchased ", HttpStatus.OK);
    }

    @GetMapping("/coupon/")
    public ResponseEntity<List<String>> getAllCoupons(){

      List<Coupon> coupons=customerService.getCustomerCoupons();
        List<String> couponNames=  new ArrayList<>();
              for(Coupon coupon:coupons){
                  couponNames.add(coupon.getTitle());
              }
        return new ResponseEntity<>(couponNames , HttpStatus.OK);
    }
}
