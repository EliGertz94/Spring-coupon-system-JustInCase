package com.coupons.couponsystem.ClientLogIn;

import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.ClientFacade;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import com.coupons.couponsystem.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoginManager {

        @Autowired
        AnnotationConfigApplicationContext ctx;

        static  LoginManager instance=  new LoginManager();
        private LoginManager(){}

        public static LoginManager getInstance(){return instance;}

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
