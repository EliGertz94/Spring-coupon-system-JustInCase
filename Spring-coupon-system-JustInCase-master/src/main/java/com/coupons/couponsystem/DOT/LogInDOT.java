package com.coupons.couponsystem.DOT;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class LogInDOT {

    private String username;
    private String password;

    public LogInDOT(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
