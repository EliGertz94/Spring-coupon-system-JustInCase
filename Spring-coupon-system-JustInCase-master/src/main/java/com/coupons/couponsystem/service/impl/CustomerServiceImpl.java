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


    /**
     *
     * @param email
     * @param password
     * @return
     * @throws CouponSystemException - customer not found
     */
    @Override
    public  boolean logIn(String email,String password) throws CouponSystemException {

        Customer customer=   customerRepository.findByEmailAndPassword(email,password)
                .orElseThrow(() -> new CouponSystemException("customer not found logIn customerService ",HttpStatus.NOT_FOUND));

        if(customerRepository.existsByEmail(email)
                && customerRepository.existsByPassword(password)){
            customerId = customer.getId();
            return true;
        }
        return false;
    }

    /**
     *
     * @param couponId
     * @throws CouponSystemException - if purchaseCoupon purchase already exist -  customer or coupon not founds by id
     */
    @Override
    public void purchaseCoupon(long couponId) throws CouponSystemException {
        logger.info("exist or not ?  {} ",couponRepository.existsByCustomers_idAndId(this.customerId,couponId));
        if(couponRepository.existsByCustomers_idAndId(this.customerId,couponId)) {
        throw new CouponSystemException("purchaseCoupon purchase already exist at CustomerService", HttpStatus.BAD_REQUEST);
        }
            Customer customer = customerRepository.findById(this.customerId)
                    .orElseThrow(()
                            -> new CouponSystemException( " customer not founds by id - customer service",HttpStatus.NOT_FOUND));

            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(()
                            -> new CouponSystemException(" coupon not founds by id - customer service",HttpStatus.NOT_FOUND));

            customer.getCoupons().add(coupon);

            coupon.setAmount(coupon.getAmount() - 1);

    }


    /**
     *
     * @return List<Coupon>
     */
    @Override
    public List<Coupon> getCustomerCoupons() {

        List<Coupon> coupons = couponRepository.findAllByCustomers_id(this.customerId);
        coupons.forEach(c->logger.info(" coupon {}",c) );

        return coupons;

  //   return    customerRepository.findAllCustomerCoupons(this.customerId);

    }

    /**
     *
     * @param maxPrice
     * @return
     */
    @Override
    public List<Coupon> getCustomerCoupons(double maxPrice) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        SecuredUser user = (SecuredUser)authentication.getPrincipal();
//        this.logIn(user.getUsername(), user.getPassword());
        List<Coupon> coupons = couponRepository.findAllByCustomers_idAndPriceLessThanEqual(this.customerId,maxPrice);
        return coupons;
    }

    /**
     *
     * @param category
     * @return
     */
    @Override
    public List<Coupon> getCustomerCoupons(Category category) {
        List<Coupon> coupons = couponRepository.findAllByCustomers_idAndCategory(this.customerId,category);
        return coupons;
    }

    /**
     *
     * @return Customer
     * @throws CouponSystemException -  customer not found
     */
    @Override
    public Customer getCustomerDetails() throws CouponSystemException {

        Customer customer = customerRepository.findById(this.customerId)
                .orElseThrow(()-> new CouponSystemException(
                        "customer not found by id - getCustomerDetails",HttpStatus.NOT_FOUND));

        return customer;
    }
}
