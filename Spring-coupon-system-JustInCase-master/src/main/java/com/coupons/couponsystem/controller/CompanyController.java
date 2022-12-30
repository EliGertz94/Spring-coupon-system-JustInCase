package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.security.SecuredUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/company/")
public class CompanyController  extends ClientController{

    Logger logger= LoggerFactory.getLogger(CompanyController.class);


    @PostMapping("/login")
    public boolean logIn(Authentication authentication,@Nullable LogInDOT logInDOT){

//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication authentication = context.getAuthentication();
        System.out.println(authentication.isAuthenticated());
        if(authentication.isAuthenticated()) {
            SecuredUser user = (SecuredUser) authentication.getPrincipal();
            System.out.println(user);

            //logger.info("Logged in user {} , {}  ",user.getUsername(),user.getPassword(),user.getAuthorities());
            try {
                return companyService.logIn(user.getUsername(), user.getPassword());
            } catch (CouponSystemException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());

            }
        }

        try {
            return companyService.logIn(logInDOT.getEmail(),logInDOT.getPassword());
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<String> addCoupon(@RequestBody  Coupon coupon){
        try {
            companyService.addCoupon(coupon);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());
        }
        return new ResponseEntity<>("coupon was added",HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCoupon(@RequestBody  Coupon coupon){
        try {
            companyService.updateCoupon(coupon);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());

        }
        return new ResponseEntity<>("coupon was updated",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable  Long couponId){
        try {
            companyService.deleteCoupon(couponId);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());
        }
        return new ResponseEntity<>("coupon was deleted",HttpStatus.OK);
    }

    @GetMapping("/allcoupons")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(){
        return new ResponseEntity<>(companyService.getAllCompanyCoupons() ,HttpStatus.OK);
    }

    @GetMapping("/allcoupons/category/{category}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable Category category){
        System.out.println(category);
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByCategory(category) ,HttpStatus.OK);
    }

    @GetMapping("/allcoupons/{maxPrice}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable double maxPrice){
        System.out.println(maxPrice);
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByPrice(maxPrice) ,HttpStatus.OK);
    }

}
