package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.Repositoty.AdminRepository;
import com.coupons.couponsystem.Repositoty.CompanyRepository;
import com.coupons.couponsystem.Repositoty.CouponRepository;
import com.coupons.couponsystem.Repositoty.CustomerRepository;
import com.coupons.couponsystem.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientFacade {

        @Autowired
        protected CompanyRepository companyRepository;
        @Autowired
        protected CustomerRepository customerRepository;
        @Autowired
        protected CouponRepository couponRepository;

        @Autowired
        protected AdminRepository adminRepository;



   public abstract boolean logIn(String email, String password) throws CouponSystemException;
}
