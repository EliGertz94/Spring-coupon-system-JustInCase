package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import com.coupons.couponsystem.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract  class ClientController {

        @Autowired
        protected AdminServiceImpl adminService;
        @Autowired
        protected CustomerServiceImpl customerService;
        @Autowired
        protected CompanyServiceImpl companyService;

        @Autowired
        protected PasswordEncoder passwordEncoder;

        @Autowired
        protected AuthenticationManager authenticationManager;





     //   public abstract boolean logIn(LogInDOT logInDOT);

}
