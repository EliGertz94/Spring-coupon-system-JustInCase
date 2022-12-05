package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.service.AdminService;
import com.coupons.couponsystem.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("api/company/")
public class CompanyController {


    @Autowired
    private AdminService adminService;
    @Autowired
    private CompanyService companyService;

    Logger logger= LoggerFactory.getLogger(CompanyController.class);


    public CompanyController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody LogInDOT logInDOT){
        try{
            String messege =  companyService.logIn(logInDOT.getEmail(),logInDOT.getPassword())+" ";

            logger.info("logged in status {}",messege);
            return new ResponseEntity<>(messege,HttpStatus.OK);
        }catch(CouponSystemException e) {
            throw new ResponseStatusException(e.getStatus(),e.getMessage(),e);
        }
    }

    @PostMapping("/boolean/")
    public String doesCompanyExisit(@RequestBody Company company){
        try{
            String messege =  adminService.logIn(company.getEmail(),company.getPassword())+" ";
            return messege;
        }catch(CouponSystemException e) {
            throw new ResponseStatusException(e.getStatus(),e.getMessage(),e);
        }
    }

    @PostMapping()
    public ResponseEntity<Company> addCompany(@RequestBody Company company){
        return new ResponseEntity<>(adminService.addCompany(company), HttpStatus.OK);
    }



    @PutMapping()
    public ResponseEntity<Company> updateCompany(@RequestBody Company company){
        return new ResponseEntity<>(adminService.updateCompany(company),HttpStatus.OK);
    }
    @PostMapping("/addcoupon/")
    public ResponseEntity<String> addCoupon(@RequestBody Coupon coupon){
        companyService.addCoupon(coupon);
            return new ResponseEntity<>("coupon was added",HttpStatus.OK);
    }

    @GetMapping("/companycoupons/")
    public  ResponseEntity<List<Coupon>> getCompanyCoupons(){

       List<Coupon> coupons=  companyService.getAllCompanyCoupons().getCoupons();

        List<String> couponsTitle=  new ArrayList<>();

//        for(Coupon coupon:coupons) {
//            couponsTitle.add(coupon.getTitle());
//        }

        return new ResponseEntity<>(coupons,HttpStatus.OK)  ;
    }

    @GetMapping("/companycoupons/{category}")
    public ResponseEntity<List<Coupon>> getCompanyCouponsByCategory(@PathVariable String category){

        Category categoryByNum = Category.valueOf(category);

        Company company =  companyService.getAllCompanyCoupons();

        List<Coupon> coupons=  companyService.getAllCompanyCouponsByCategory(categoryByNum);


//        List<String> couponsTitle=  new ArrayList<>();
//
//        for(Coupon coupon:coupons) {
//            couponsTitle.add(coupon.getTitle());
//        }
        return new ResponseEntity<>(coupons,HttpStatus.OK)  ;
    }

}
