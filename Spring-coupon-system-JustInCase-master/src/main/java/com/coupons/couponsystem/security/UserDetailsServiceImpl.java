package com.coupons.couponsystem.security;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import com.coupons.couponsystem.Repositoty.AdminRepository;
import com.coupons.couponsystem.Repositoty.CompanyRepository;
import com.coupons.couponsystem.Repositoty.CustomerRepository;
import com.coupons.couponsystem.Repositoty.UserRepository;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        User user =  userRepository.findByUsername(email)
                .orElseThrow(()-> new UsernameNotFoundException("No user with such username in loaduserbyusername"));

        if(user.getClientRole().equals(ClientType.Administrator))
        {
            return new SecuredUser(user);
        }
        if(user.getClientRole().equals(ClientType.Company))
        {
            Company company =  companyRepository.findByUserId(user.getId())
                    .orElseThrow(()-> new UsernameNotFoundException("No company user with such username in loaduserbyusername"));

            return new SecuredUser(user,company.getId());
        }
        if(user.getClientRole().equals(ClientType.Customer))
        {
            Customer customer =  customerRepository.findByUserId(user.getId())
                    .orElseThrow(()-> new UsernameNotFoundException("No customer user with such username in loaduserbyusername"));

            return new SecuredUser(user,customer.getId());
        }


        throw new UsernameNotFoundException("wrong credentials loadUser");





    }
}
