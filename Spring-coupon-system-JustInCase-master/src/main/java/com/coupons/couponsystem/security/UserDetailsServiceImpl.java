package com.coupons.couponsystem.security;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import com.coupons.couponsystem.Repositoty.AdminRepository;
import com.coupons.couponsystem.Repositoty.CompanyRepository;
import com.coupons.couponsystem.Repositoty.CustomerRepository;
import com.coupons.couponsystem.model.Admin;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);


        if(admin!=null) {
            User user = new User();
            user.setUserName(admin.getEmail());
            user.setPassWord(admin.getPassword());
            user.setClientRole(ClientType.Administrator);
            System.out.println(" user logged in "+ user.getClientRole());


            return new SecuredUser(user);
        }

        Company company = companyRepository.findByEmail(email);
        if(company!=null) {
            User user = new User();
            user.setUserName(company.getEmail());
            user.setPassWord(company.getPassword());
            user.setClientRole(ClientType.Company);
            System.out.println(user);
            System.out.println(" user logged in "+ user.getClientRole());


            return new SecuredUser(user);
        }
        Customer customer = customerRepository.findByEmail(email);

        if(customer!=null) {
            User user = new User();
            user.setUserName(customer.getEmail());
            user.setPassWord(customer.getPassword());
            user.setClientRole(ClientType.Customer);

            System.out.println(" user logged in "+ user.getClientRole());

            return new SecuredUser(user);
        }



        throw new UsernameNotFoundException("wrong credentials loadUser");





    }
}
