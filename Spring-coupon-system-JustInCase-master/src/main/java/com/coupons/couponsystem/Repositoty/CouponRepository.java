package com.coupons.couponsystem.Repositoty;

import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    boolean existsByTitleAndCompanyId(String title,long companyId);
    boolean existsByCompanyId(long companyId);

    List<Coupon> findAllByCompany_id(long company_id);

    List<Coupon> findAllByCompanyId(long id);

    List<Coupon> findAllBy
    List<Coupon> findAllByCompany_idAndCategory(long CompanyId,Category category);
    //LessThanEqual
    List<Coupon> findAllByCompany_idAndPriceLessThanEqual(long CompanyId,double price);

    long deleteAllByEndDateBefore(LocalDateTime endDate);


}

