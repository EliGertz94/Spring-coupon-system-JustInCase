package com.coupons.couponsystem.job;

import com.coupons.couponsystem.CouponSystemApplication;
import com.coupons.couponsystem.Repositoty.CouponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class CouponExpirationDailyJob {

    public  Logger logger = LoggerFactory.getLogger(CouponSystemApplication.class);

   @Autowired
   CouponRepository couponRepository;

       @Scheduled(fixedRate = 10_000)
       @Transactional
        public void dailyJob() {
            couponRepository.deleteByEndDateBefore(LocalDateTime.now());
           logger.info("was looking for coupons to delete ");
        }
}


