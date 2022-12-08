package com.coupons.couponsystem.Repositoty;

import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    boolean existsByTitleAndCompanyId(String title,long companyId);
    boolean existsByCompanyId(long companyId);

    List<Coupon> findAllByCompany_id(long company_id);

    List<Coupon> findAllByCompanyId(long id);

    List<Coupon> findAllByCompany_idAndCategory(long CompanyId,Category category);



    @Query("SELECT u FROM coupon u WHERE u.price < ?1 and u.company_id = ?2")
    List<Coupon> findAllByMaxPriceAndCompany_id(double maxPrice, long companyId);


    long deleteAllByEndDateBefore(LocalDateTime endDate);
}

