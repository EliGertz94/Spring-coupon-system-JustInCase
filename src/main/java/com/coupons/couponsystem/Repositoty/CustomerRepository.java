package com.coupons.couponsystem.Repositoty;

import com.coupons.couponsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

   Optional<Customer> findByEmailAndPassword(String email, String password);



//    @Query(value = "select * from coupons  where coupons.id  in " +
//            "(select coupon_id from CUSTOMERS_VS_COUPONS where customer_id = customerId )"
//            , nativeQuery = true)
//    ArrayList<Coupon> findAllCustomerCoupons(long customerId);
//

}
