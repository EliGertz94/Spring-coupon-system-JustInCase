package com.coupons.couponsystem;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import com.coupons.couponsystem.ClientLogIn.LoginManager;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.service.CustomerService;
import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class CouponSystemApplication {

	public static Logger logger = LoggerFactory.getLogger(CouponSystemApplication.class);
	public static void main(String[] args) {


		//ApplicationContext ctx=  SpringApplication.run(CouponSystemApplication.class, args);

//		CompanyServiceImpl companyService = ctx.getBean(CompanyServiceImpl.class);
//		companyService.logIn("aaa@gmail.com","aaacombbpbany");
//		logger.info("copany coupons  {}",companyService.getAllCompanyCoupons());
		LoginManager loginManager =LoginManager.getInstance();
		LoginManager loginManager2 =LoginManager.getInstance();
		//LoginManager loginManager2 = LoginManager.getInstance();
		logger.info("login objects -  {} {}",loginManager,loginManager2);

		CompanyServiceImpl company = (CompanyServiceImpl)
				loginManager.logIn("bbb@gmail.com", "bbbbdbfd", ClientType.Company);

		company.addCoupon(new Coupon(0L,null,Category.Restaurant,"new first coupon","description",
						LocalDateTime.now(),LocalDateTime.of(2022, 11, 11, 12, 12)
						,150,120,"image"));

		//company.up



	List<Coupon> coupons= company.getAllCompanyCoupons();
		List<Coupon> couponsByCategory = company.getAllCompanyCouponsByCategory(Category.Restaurant);
		System.out.println("all company coupons");
		printList(coupons);
		System.out.println("coupons by category ");
		printList(couponsByCategory);




		AdminServiceImpl admin = (AdminServiceImpl)
					loginManager.logIn("admin@admin.com", "admin", ClientType.Administrator);


		CustomerService customer = (CustomerService)
					loginManager.logIn("lolaa@gmaul.comd", "eli1234", ClientType.Customer);

	}

	public static void printList(List<Coupon> list){
		for(Coupon  coupon :list){
			logger.info("one coupon  {}",coupon.getTitle());
		}
	}

}
