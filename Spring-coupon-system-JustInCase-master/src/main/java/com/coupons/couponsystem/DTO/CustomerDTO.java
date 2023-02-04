package com.coupons.couponsystem.DTO;

import com.coupons.couponsystem.model.Coupon;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomerDTO {


        private Long id;

        private Long customerId;

        private String firstName;
        private String lastName;
        private String username;
        private String password;


        private List<Coupon> coupons ;

        public CustomerDTO(Long id, Long customerId, String firstName, String lastName, String username, String password, List<Coupon> coupons) {
                this.id = id;
                this.customerId = customerId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.username = username;
                this.password = password;
                this.coupons = coupons;
        }
}
