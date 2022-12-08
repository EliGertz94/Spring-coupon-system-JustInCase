package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.Repositoty.CompanyRepository;
import com.coupons.couponsystem.Repositoty.CouponRepository;
import com.coupons.couponsystem.Repositoty.CustomerRepository;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.exception.ResourceNotFound;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AdminServiceImpl extends  ClientFacade implements AdminService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CouponRepository couponRepository;





    @Override
    public boolean logIn(String email,String password){

        return email.equals("admin@admin.com") && password.equals("admin");

    }

    @Override
    public Company addCompany(Company company) {
        if(!companyRepository.existsByEmail(company.getEmail())) {
            if (!companyRepository.existsByPassword(company.getPassword())) {
                Company newCompany = companyRepository.save(company);
                return newCompany;
            }
            throw new CouponSystemException(HttpStatus.BAD_REQUEST,"addCompany error password exists already");

        }

        throw new CouponSystemException(HttpStatus.BAD_REQUEST,"addCompany error email  exists already");

    }


    //if the name is the same should I have a set name ?
        @Override
        public Company updateCompany(Company company) {


                return companyRepository.findById(company.getId()).map(companyEntity -> {
                            companyEntity.setEmail(company.getEmail());
                            companyEntity.setPassword(company.getPassword());

                            return companyEntity;
                        }
                ).orElseThrow(() -> new ResourceNotFound("updateCompany", "company id", company.getId()));
          
            }

        public void deleteCompany(long id)
        {
            Company company = companyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFound("deleteCompany", "company id", id));

            companyRepository.delete(company);

        }

        @Override
        public List<Company> getAllCompanies(){
          return companyRepository.findAll();
        }

        @Override
        public Company getOneCompany(long id) {
          return  companyRepository.findById(id).orElseThrow
                  (() -> new ResourceNotFound("getOneCompany", "company id", id));
        }

    @Override
    public Customer addCustomer(Customer customer) {
        if(customerRepository.existsByEmail(customer.getEmail()))
        {
            throw new CouponSystemException(HttpStatus.BAD_REQUEST,"addCustomer error at adminService");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {

        return customerRepository.findById(customer.getId()).map(customerEntity -> {
            customerEntity.setFirstName(customer.getFirstName());
            customerEntity.setLastName(customer.getLastName());
            customerEntity.setEmail(customer.getEmail());
            customerEntity.setPassword(customer.getPassword());

                    return customerEntity;
                }
        ).orElseThrow(() -> new CouponSystemException(HttpStatus.NOT_FOUND,"updateCustomer error at admin service "));

    }

    @Override
    public void deleteCustomer(long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("deleteCustomer", "customer id", id));

        customerRepository.delete(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
     return  customerRepository.findAll();
    }

    @Override
    public Customer getOneCustomer(long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("getOneCustomer", "customer id", id));

    }


}
