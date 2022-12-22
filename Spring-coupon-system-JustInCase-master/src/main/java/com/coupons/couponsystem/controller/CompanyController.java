package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/company/")
public class CompanyController  extends ClientController{

    Logger logger= LoggerFactory.getLogger(CompanyController.class);


    @PostMapping("/login")
    public boolean logIn(@RequestBody LogInDOT logInDOT){
            return companyService.logIn(logInDOT.getEmail(),logInDOT.getPassword());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCoupon(@RequestBody  Coupon coupon){
        companyService.addCoupon(coupon);
        return new ResponseEntity<>("coupon was added",HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCoupon(@RequestBody  Coupon coupon){
        companyService.updateCoupon(coupon);
        return new ResponseEntity<>("coupon was updated",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{couponId}")
    public ResponseEntity<String> deleteCoupon(@RequestBody  Long couponId){
        companyService.deleteCoupon(couponId);
        return new ResponseEntity<>("coupon was deleted",HttpStatus.OK);
    }

    @GetMapping("/allcoupons")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(){
        return new ResponseEntity<>(companyService.getAllCompanyCoupons() ,HttpStatus.OK);
    }

    @GetMapping("/allcoupons/{category}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable Category category){
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByCategory(category) ,HttpStatus.OK);
    }

    @GetMapping("/allcoupons/{maxPrice}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable double maxPrice){
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByPrice(maxPrice) ,HttpStatus.OK);
    }

}
