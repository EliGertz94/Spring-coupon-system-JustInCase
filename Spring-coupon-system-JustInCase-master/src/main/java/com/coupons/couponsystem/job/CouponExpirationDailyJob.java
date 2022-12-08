package com.coupons.couponsystem.job;

import com.coupons.couponsystem.Repositoty.CouponRepository;
import com.coupons.couponsystem.exception.CouponSystemException;

import java.time.LocalDateTime;

//@Component
public class CouponExpirationDailyJob extends Thread {

    private CouponRepository couponRepository;
    private Thread thread = new Thread(this,"daily job");

    private boolean quit = true;


    private static CouponExpirationDailyJob instance;

    private CouponExpirationDailyJob()
    {
        instance= new CouponExpirationDailyJob();
    }
    @Override
    public void run() {

        while (quit) {
            try {
                couponRepository.deleteAllByEndDateBefore(LocalDateTime.now());
                Thread.sleep(43_200_000); // 12 hours
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
                break;
            } catch (CouponSystemException e) {
                System.out.println("Thread error , please check thread again !");
            }
        }
        System.out.println("job stoped");
    }

    public void stopJob(){
        quit = false;
        Thread.interrupted();

    }

    public void startJob(){
        thread.start();
        System.out.println("job started");
    }

    public Thread getThread() {
        return thread;
    }
}
