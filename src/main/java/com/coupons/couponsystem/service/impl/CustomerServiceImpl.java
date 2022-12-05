package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.Repositoty.CompanyRepository;
import com.coupons.couponsystem.Repositoty.CouponRepository;
import com.coupons.couponsystem.Repositoty.CustomerRepository;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl extends ClientFacade  implements CustomerService {

    private long customerId;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CouponRepository couponRepository;


//    public CustomerServiceImpl(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository) {
//        super(companyRepository, customerRepository, couponRepository);
//    }

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

            Customer customer = customerRepository.findById(this.customerId)
                    .orElseThrow(()-> new CouponSystemException(HttpStatus.NOT_FOUND
                            ," customer not founds by id - customer service"));

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(()-> new CouponSystemException(HttpStatus.NOT_FOUND
                        ," customer not founds by id - customer service"));

        customer.getCoupons().add(coupon);
    }


    //is it legit ?
    //do I need to do it with a specific repository query ?
    @Override
    public List<Coupon> getCustomerCoupons() {
        Customer customer = customerRepository.findById(this.customerId)
                .orElseThrow(()-> new CouponSystemException(HttpStatus.NOT_FOUND
                        ," customer not founds by id - customer service"));

        return customer.getCoupons();

  //   return    customerRepository.findAllCustomerCoupons(this.customerId);

    }

    @Override
    public List<Coupon> getCustomerCoupons(double maxPrice) {
        return null;
    }

    @Override
    public List<Coupon> getCustomerCoupons(Category category) {
        return null;
    }

    @Override
    public Customer getCustomerDetails() {
        return null;
    }
}
