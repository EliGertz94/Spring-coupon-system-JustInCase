package com.coupons.couponsystem.Repositoty;

import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    boolean existsByTitleAndCompanyId(String title,long companyId);

    List<Coupon> findAllByCompany_id(long company_id);

    List<Coupon> findAllByCompanyId(long id);

    List<Coupon> findAllByCompany_idAndCategory(long CompanyId,Category category);

    long deleteAllByEndDateBefore(LocalDateTime endDate);
}

