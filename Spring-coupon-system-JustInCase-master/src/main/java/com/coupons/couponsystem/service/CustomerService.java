package com.coupons.couponsystem.service;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;

import java.util.List;

public interface CustomerService {


//    boolean logIn(String email, String password) throws CouponSystemException;

    void purchaseCoupon(long couponId) throws CouponSystemException;

    List<Coupon> getCustomerCoupons();

    List<Coupon> getCustomerCoupons(double maxPrice);

    List<Coupon> getCustomerCoupons(Category category);

    Customer getCustomerDetails() throws CouponSystemException;




}
