package com.coupons.couponsystem.ClientLogIn;

import com.coupons.couponsystem.CouponSystemApplication;
import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.ClientFacade;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import com.coupons.couponsystem.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;

@ComponentScan
@Scope("singleton") // change to prototype
public class LoginManager {

    @Autowired
    ApplicationContext ctx= SpringApplication.run(CouponSystemApplication.class);;


        static  LoginManager instance;
        private LoginManager(){
        }

        public static LoginManager getInstance(){
            if(instance==null){
                instance=  new LoginManager();
            }
                return instance;
        }

        public ClientFacade logIn(String email, String password, ClientType clientType)  {

            switch (clientType) {
                case Administrator:
            AdminServiceImpl adminFacade= ctx.getBean(AdminServiceImpl.class);
                    if(adminFacade.logIn(email,password)){
                        return adminFacade;
                    }

                    break;
                case Company:
                   CompanyServiceImpl companyFacade = ctx.getBean(CompanyServiceImpl.class);

                    if(companyFacade.logIn(email,password))
                    {
                    return companyFacade;

                }

                break;
            case Customer:
               CustomerServiceImpl customerFacade=  ctx.getBean(CustomerServiceImpl.class);
                if(customerFacade.logIn(email,password)){
                    return customerFacade;
                }
                break;
        }
        return null;
    }
}
