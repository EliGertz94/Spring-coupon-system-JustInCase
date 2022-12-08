package com.coupons.couponsystem.exception;

import org.springframework.http.HttpStatus;



//business logic Exception

public class CouponSystemException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public CouponSystemException( HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }


    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

