package com.coupons.couponsystem.ClientLogIn;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.ClientFacade;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import com.coupons.couponsystem.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@ComponentScan
@Component
@Scope // change to prototype
public class LoginManager {


        @Autowired
        AdminServiceImpl adminService;

        @Autowired
        CompanyServiceImpl companyService;

        @Autowired
        CustomerServiceImpl customerService;





    public ClientFacade logIn(String email, String password, ClientType clientType) throws CouponSystemException {

            switch (clientType) {
                case Administrator:
                    if(adminService.logIn(email,password)){
                        return adminService;
                    }

                    break;
                case Company:

                    if(companyService.logIn(email,password))
                    {
                    return companyService;

                }

                break;
            case Customer:
                if(customerService.logIn(email,password)){
                    return customerService;
                }
                break;
        }
        return null;
    }
}
