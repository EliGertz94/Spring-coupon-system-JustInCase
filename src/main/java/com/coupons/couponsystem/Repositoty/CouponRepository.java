package com.coupons.couponsystem.Repositoty;

import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    boolean existsByTitle(String title);


    List<Coupon> findAllByCompanyId(long id);

    List<Coupon> findAllByCompanyIdAndCategory(long CompanyId,Category category);
    //List<Coupon> findAllByCompanyIdAndMaxPrice(int maxPrice);

//    @Query(value = "SELECT * FROM coupon WHERE price < :maxPrice",
//            nativeQuery = true)
//    List<Coupon> findAllByCompanyIdAndMaxPrice(long id,int maxPrice);
//
        long deleteAllByEndDateBefore(LocalDateTime endDate);
}

