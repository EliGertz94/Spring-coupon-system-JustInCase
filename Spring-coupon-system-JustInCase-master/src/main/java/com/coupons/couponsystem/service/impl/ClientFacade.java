package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.repositoty.*;
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
        protected PurchaseRepository purchaseRepository;
        @Autowired
        protected AdminRepository adminRepository;
        @Autowired
        protected UserRepository userRepository;

        @Autowired
        protected PaypalService paypalService;

   public abstract boolean logIn(String email, String password, ClientType clientType) throws CouponSystemException;
}
