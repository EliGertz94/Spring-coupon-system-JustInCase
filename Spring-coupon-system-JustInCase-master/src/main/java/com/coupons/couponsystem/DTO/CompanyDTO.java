package com.coupons.couponsystem.DTO;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CompanyDTO {


        private Long id;

        private Long companyId;

        private String name;
        private String username;
        private String password;

        private ClientType clientRole;


        private List<Coupon> coupons ;

        public CompanyDTO(User user, Company company) {
                this.id= user.getId();
                this.companyId= company.getId();
                this.name = company.getName();
                this.password= user.getPassword();
                this.clientRole =user.getClientRole();
                this.coupons=company.getCoupons();

        }
}
