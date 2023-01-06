package com.coupons.couponsystem.controller;


import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/customer/")
public class CustomerController extends ClientController {

    Logger logger= LoggerFactory.getLogger(CompanyController.class);

    @PostMapping("/coupon/{couponId}")
    public ResponseEntity<String> purchaseCoupon(@PathVariable long couponId){
        try {

            customerService.purchaseCoupon(couponId);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());

        }
        return new ResponseEntity<>("coupon id "+couponId+ "  was purchased ", HttpStatus.OK);
    }

    @GetMapping("/coupons/")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(Authentication authentication){
        return new ResponseEntity<>(customerService.getCustomerCoupons(),HttpStatus.OK);
    }

    @GetMapping("/coupons/category/{category}")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable Category category){
        return new ResponseEntity<>(customerService.getCustomerCoupons(category),HttpStatus.OK);
    }

    @GetMapping("/coupons/price/{maxPrice}")
    public ResponseEntity<List<Coupon>> getCustomerCoupons(@PathVariable double maxPrice){

        return new ResponseEntity<>(customerService.getCustomerCoupons(maxPrice),HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<Customer> getCustomerDetails(){
        try {
            return  new ResponseEntity<>(customerService.getCustomerDetails(),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}
