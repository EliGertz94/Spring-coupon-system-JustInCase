package com.coupons.couponsystem.ClientLogIn;

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



    public ClientFacade logIn(String email, String password, ClientType clientType)  {

            switch (clientType) {
                case Administrator:
            //AdminServiceImpl adminFacade= ctx.getBean(AdminServiceImpl.class);
                    if(adminService.logIn(email,password)){
                        return adminService;
                    }

                    break;
                case Company:
             //      CompanyServiceImpl companyFacade = ctx.getBean(CompanyServiceImpl.class);

                    if(companyService.logIn(email,password))
                    {
                    return companyService;

                }

                break;
            case Customer:
            //   CustomerServiceImpl customerFacade=  ctx.getBean(CustomerServiceImpl.class);
                if(customerService.logIn(email,password)){
                    return customerService;
                }
                break;
        }
        return null;
    }
}
