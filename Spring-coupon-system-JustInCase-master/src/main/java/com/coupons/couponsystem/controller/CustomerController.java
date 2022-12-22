package com.coupons.couponsystem.controller;


import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer/")
public class CustomerController extends ClientController {


    @PostMapping("/login")
    public boolean logIn(@RequestBody LogInDOT logInDOT){

        return customerService.logIn(logInDOT.getEmail(),logInDOT.getPassword());

    }

    @PostMapping("/coupon/{couponId}")
    public ResponseEntity<String> purchaseCoupon(@PathVariable long couponId){
        customerService.purchaseCoupon(couponId);
        return new ResponseEntity<>("coupon id "+couponId+ "  was purchased ", HttpStatus.OK);
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(){
        return new ResponseEntity<>(customerService.getCustomerCoupons(),HttpStatus.OK);
    }

    @GetMapping("/coupons/{category}")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable Category category){
        return new ResponseEntity<>(customerService.getCustomerCoupons(category),HttpStatus.OK);
    }

    @GetMapping("/coupons/{maxPrice}")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable double maxPrice){
        return new ResponseEntity<>(customerService.getCustomerCoupons(maxPrice),HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<Customer> getCustomerDetails(){
        return  new ResponseEntity<>(customerService.getCustomerDetails(),HttpStatus.OK);
    }

}
