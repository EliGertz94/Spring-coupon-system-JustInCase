package com.coupons.couponsystem.service;

import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;

import java.util.List;

public interface CompanyService {

     boolean logIn(String email,String password);

     Coupon addCoupon(Coupon coupon);

    Coupon updateCoupon(Coupon coupon);

    void deleteCoupon(long couponId);

    List<Coupon> getAllCompanyCoupons();

    List<Coupon> getAllCompanyCouponsByCategory(Category category);

  //  List<Coupon> getAllCompanyCouponsByPrice(int maxPrice);

    Company getCompanyDetails();

}
