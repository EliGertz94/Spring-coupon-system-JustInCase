package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.DOT.LogInDOT;
import com.coupons.couponsystem.service.AdminService;
import com.coupons.couponsystem.service.CompanyService;
import com.coupons.couponsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract  class ClientController {

        @Autowired
        protected AdminService adminService;
        @Autowired
        protected CustomerService customerService;
        @Autowired
        protected CompanyService companyService;

        public abstract boolean logIn(LogInDOT logInDOT);

}
