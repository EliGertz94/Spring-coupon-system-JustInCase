package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.Repositoty.CompanyRepository;
import com.coupons.couponsystem.Repositoty.CouponRepository;
import com.coupons.couponsystem.Repositoty.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientFacade {

        @Autowired
        protected CompanyRepository companyRepository;
        @Autowired
        protected CustomerRepository customerRepository;
        @Autowired
        protected CouponRepository couponRepository;





    public abstract boolean logIn(String email, String password);
}
