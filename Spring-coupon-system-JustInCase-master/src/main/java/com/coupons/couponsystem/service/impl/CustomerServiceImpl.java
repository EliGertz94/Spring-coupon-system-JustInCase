package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.CouponSystemApplication;
import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.dto.Order;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.Purchase;
import com.coupons.couponsystem.model.User;
import com.coupons.couponsystem.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.LinkedList;
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
    public  boolean logIn(String email, String password, ClientType clientRole) throws CouponSystemException {

        User customerUser=   userRepository.findByUsername(email)
                .orElseThrow(() -> new CouponSystemException("customer not found logIn customerService ",HttpStatus.NOT_FOUND));

        if(customerUser.getPassword().equals(password)
                && customerUser.getClientRole().equals(clientRole)){
            Customer customer = customerRepository.findByUserId(customerUser.getId())
                    .orElseThrow(() -> new CouponSystemException("customer not found logIn customerService ",HttpStatus.NOT_FOUND));

            customerId = customer.getId();

            return true;
        }
        return false;
    }

    /**
     *
     * @param
     * @throws CouponSystemException - if purchaseCoupon purchase already exist -  customer or coupon not founds by id
     */
    @Override
    public String makePurchase(List<Long> couponIdList) throws CouponSystemException {

        Customer customer = customerRepository.findById(this.customerId).orElseThrow(()
                -> new CouponSystemException( " customer was not found was not found ",HttpStatus.NOT_FOUND));
        List<Coupon> coupons =  new LinkedList<>();
        double totalPrice = 0;
        for (Long id: couponIdList) {
            //scan all coupons if found
            Coupon coupon = couponRepository.findById(id).orElseThrow(()
                    -> new CouponSystemException( " coupon in coupon list was not found ",HttpStatus.NOT_FOUND));
            if(checkedCoupon(coupon)) {
                coupons.add(coupon);
                totalPrice+= coupon.getPrice();
            }
            else{
                throw new CouponSystemException("coupon # "+coupon.getId()+ " can't be bought ", HttpStatus.NOT_FOUND);
            }
        }

        System.out.println(totalPrice+" total price");

        Purchase purchase = Purchase.builder()
                .purchaseDate(LocalDateTime.now())
                .id(0)
                .customer(customer)
                .totalPrice(totalPrice)
                .coupons(coupons)
                .build();
        RestTemplate restTemplate = new RestTemplate();
        String url
                = "http://localhost:8081/api/paypal/";


      Purchase savedPurchase=   purchaseRepository.save(purchase);


        Order order = Order.builder()
                .price(totalPrice)
                .currency("USD")
                .description(savedPurchase.getId()+"")
                .intent("sale")
                .method("paypal")
                .build();
        String result=  restTemplate.postForEntity(url,order,String.class).getBody();
        System.out.println(result +" is the result ");

        return result;

    }





    public boolean checkedCoupon(Coupon coupon){
        if(coupon.getAmount()<= 0|| !coupon.isBuyable())
        {
            return false;
        }
        return true;
    }

    /**
     *
     * @return List<Coupon>
     */
    @Override
    public List<Purchase> getCustomerPurchases() throws CouponSystemException {

        List<Purchase> purchases= purchaseRepository.findAllByCustomer_id(this.customerId);
        return purchases;
    }

    /**
     *
     * @param maxPrice
     * @return
     */
    @Override
    public List<Purchase> getCustomerPurchases(double maxPrice) throws CouponSystemException {

        List<Purchase> purchases= purchaseRepository.findAllByCustomer_idAndTotalPriceLessThanEqual(this.customerId,maxPrice);
        return purchases;
    }

    /**
     *
     * @param category
     * @return
     */
//    @Override
//    public List<Coupon> getCustomerCoupons(Category category) {
//        List<Coupon> coupons = couponRepository.findAllByCustomers_idAndCategory(this.customerId,category);
//        return coupons;
//    }

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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
