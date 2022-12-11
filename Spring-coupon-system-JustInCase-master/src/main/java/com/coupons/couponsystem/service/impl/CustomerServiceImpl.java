package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.CouponSystemApplication;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl extends ClientFacade  implements CustomerService {

    private long customerId;

    public static Logger logger = LoggerFactory.getLogger(CouponSystemApplication.class);


    @Override
    public boolean logIn(String email,String password){

        Customer customer=   customerRepository.findByEmailAndPassword(email,password)
                .orElseThrow(() -> new CouponSystemException(HttpStatus.NOT_FOUND,"logIn customer service " ));

        if(customerRepository.existsByEmail(email)
                && customerRepository.existsByPassword(password)){
            customerId = customer.getId();
            return true;
        }
        return false;
    }


    @Override
    public void purchaseCoupon(long couponId) {
        logger.info("exist or not ?  {} ",couponRepository.existsByCustomers_idAndId(this.customerId,couponId));
        if(!couponRepository.existsByCustomers_idAndId(this.customerId,couponId)) {

            Customer customer = customerRepository.findById(this.customerId)
                    .orElseThrow(() -> new CouponSystemException(HttpStatus.NOT_FOUND
                            , " customer not founds by id - customer service"));

            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new CouponSystemException(HttpStatus.NOT_FOUND
                            , " customer not founds by id - customer service"));

            customer.getCoupons().add(coupon);

            coupon.setAmount(coupon.getAmount() - 1);
            return;
        }
        throw new CouponSystemException(HttpStatus.BAD_REQUEST,"purchaseCoupon purchase already exist");
    }


    //is it legit ?
    //do I need to do it with a specific repository query ?
    @Override
    public List<Coupon> getCustomerCoupons() {
//        Customer customer = customerRepository.findById(this.customerId)
//                .orElseThrow(()-> new CouponSystemException(HttpStatus.NOT_FOUND
//                        ," customer not founds by id - customer service"));
//
//        return customer.getCoupons();

        List<Coupon> coupons = couponRepository.findAllByCustomers_id(this.customerId);
        return coupons;

  //   return    customerRepository.findAllCustomerCoupons(this.customerId);

    }

    @Override
    public List<Coupon> getCustomerCoupons(double maxPrice) {
        List<Coupon> coupons = couponRepository.findAllByCustomers_idAndPriceLessThanEqual(this.customerId,maxPrice);
        return coupons;
    }

    @Override

    public List<Coupon> getCustomerCoupons(Category category) {
        List<Coupon> coupons = couponRepository.findAllByCustomers_idAndCategory(this.customerId,category);
        return coupons;
    }

    @Override
    public Customer getCustomerDetails() {

        Customer customer = customerRepository.findById(this.customerId)
                .orElseThrow(()-> new CouponSystemException(HttpStatus.NOT_FOUND
                       ," customer not founds by id - getCustomerDetails"));

        return customer;
    }
}
