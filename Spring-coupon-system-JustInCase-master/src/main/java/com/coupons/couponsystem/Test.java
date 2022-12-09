package com.coupons.couponsystem;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import com.coupons.couponsystem.ClientLogIn.LoginManager;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.service.AdminService;
import com.coupons.couponsystem.service.CompanyService;
import com.coupons.couponsystem.service.CustomerService;
import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Test implements CommandLineRunner {
    @Autowired
    LoginManager loginManager;

    public static Logger logger = LoggerFactory.getLogger(CouponSystemApplication.class);


    @Override
    public void run(String... args) throws Exception {

        CompanyService company = (CompanyServiceImpl)
                loginManager.logIn("aaa@aaa.com", "aaa123", ClientType.Company);

        CompanyService company1 = (CompanyServiceImpl)
                loginManager.logIn("ccc@ccc.com", "ccc123", ClientType.Company);



        AdminService admin = (AdminServiceImpl) loginManager.logIn
				("admin@admin.com","admin",ClientType.Administrator);

        Customer customerEntity = new Customer(0L,"yula","grtz","yulla@dgdf.com","12345",null);
//        admin.addCustomer(customerEntity);
//        Customer customerEntity2 = new Customer(0L,"yanna","grtz","yamma@dgdf.com","12345",null);
//        admin.addCustomer(customerEntity2);

      //  Company company1 = new Company(0L,"ccc","ccc@ccc.com","ccc123",null);
		//Company company2 = new Company(0L,"bbb","bbb@bbb.com","bbb123",null);
      //  admin.addCompany(company1);
       // company.deleteCoupon(3L);

        List<Coupon> couponsByCategory = company.getAllCompanyCouponsByCategory(Category.Restaurant);

//        company1.addCoupon(new Coupon(0L,null,Category.FOOD,"food first coupon","description",
//					LocalDateTime.now(),LocalDateTime.of(2022, 11, 11, 12, 12)
//					,150,80,"image"));
        List<Coupon> couponsByPrice = company1.getAllCompanyCouponsByPrice(70);

        printList(couponsByPrice);

       logger.info("company object {} its coupon {}",company1.getCompanyDetails(),company1.getCompanyDetails().getCoupons());

        CustomerService customer = (CustomerService) loginManager.logIn
                ("yulla@dgdf.com","12345",ClientType.Customer);

        customer.purchaseCoupon(2);
        customer.purchaseCoupon(1);


    }
    public static void printList(List<Coupon> list){
        int i =0;
        for(Coupon  coupon :list){
            logger.info(" coupon "+ i+"  {}",coupon.getTitle());
            i++;
        }
    }

}
