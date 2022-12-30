package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import com.coupons.couponsystem.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

public abstract  class ClientController {

        @Autowired
        protected AdminServiceImpl adminService;
        @Autowired
        protected CustomerServiceImpl customerService;
        @Autowired
        protected CompanyServiceImpl companyService;



        public abstract boolean logIn(Authentication authentication, LogInDOT logInDOT);

}
