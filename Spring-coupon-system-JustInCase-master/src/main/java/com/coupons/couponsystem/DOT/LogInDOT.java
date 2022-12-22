package com.coupons.couponsystem.DOT;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import lombok.Data;

@Data
public class LogInDOT {

    private String email;
    private String password;

    private  ClientType clientType;
}
