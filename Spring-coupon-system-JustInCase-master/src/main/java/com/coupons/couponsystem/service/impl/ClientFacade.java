package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.Repositoty.*;
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
        @Autowired
        protected UserRepository userRepository;

   public abstract boolean logIn(String email, String password) throws CouponSystemException;
}
