package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Admin;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AdminServiceImpl extends  ClientFacade implements AdminService {


    /**
     *
     * @param email
     * @param password
     * @return
     * @throws CouponSystemException
     */

    @Override
    public boolean logIn(String email,String password) throws CouponSystemException {

        Admin admin=   adminRepository.findByEmailAndPassword(email,password)
                .orElseThrow(() -> new CouponSystemException("Admin not found " ));
        if(admin !=null) {
            return true;
        }
        return false;


    }

    /**
     * @param company
     * @return Company
     * @throws CouponSystemException if the entity doesn't exist
     */
    @Override
    public Company addCompany(Company company) throws CouponSystemException {
        if(!companyRepository.existsByEmail(company.getEmail())

        ) {
            if (!companyRepository.existsByPassword(company.getPassword())) {
                Company newCompany = companyRepository.save(company);
                return newCompany;
            }
            throw new CouponSystemException("addCompany error password exists already");

        }

        throw new CouponSystemException("addCompany error email  exists already");

    }


    /**
     *
     * @param company
     * @return  Company
     * @throws CouponSystemException company not found
     */
        @Override
        public Company updateCompany(Company company) throws CouponSystemException {


                return companyRepository.findById(company.getId()).map(companyEntity -> {
                            companyEntity.setEmail(company.getEmail());
                            companyEntity.setPassword(company.getPassword());

                            return companyEntity;
                        }
                ).orElseThrow(() -> new CouponSystemException("company not found updateCompany"));
          
            }

    /**
     *
     * @param id
     * @throws CouponSystemException company not found
     */
    public void deleteCompany(long id) throws CouponSystemException {
            Company company =  companyRepository.findById(id)
                    .orElseThrow(() -> new CouponSystemException("deleteCompany company not found"));

            companyRepository.delete(company);

        }

        /**
         *
         * @return List<Company>
         */
        @Override
        public List<Company> getAllCompanies(){
          return companyRepository.findAll();
        }

    /**
     *
     * @param id
     * @return Company
     * @throws CouponSystemException company not found
     */
    @Override
        public Company getOneCompany(long id) throws CouponSystemException {
           return  companyRepository.findById(id).orElseThrow
                  (() -> new CouponSystemException("company not found at getOneCompany"));

    }
    /**
     *
     * @param customer
     * @return Customer
     * @throws CouponSystemException Email exists already
     */
    @Override
    public Customer addCustomer(Customer customer) throws CouponSystemException {
        if(customerRepository.existsByEmail(customer.getEmail())
                &&companyRepository.existsByEmail(customer.getEmail()))
        {
            throw new CouponSystemException("Email exists already addCustomer at adminService");
        }
        //optional
        customer.setClientRole(ClientType.Customer);
        return customerRepository.save(customer);
    }

    /**
     *
     * @param customer
     * @return Customer
     * @throws CouponSystemException Customer not found
     */
    @Override
    public Customer updateCustomer(Customer customer) throws CouponSystemException {

        return customerRepository.findById(customer.getId()).map(customerEntity -> {
                    customerEntity.setFirstName(customer.getFirstName());
                    customerEntity.setLastName(customer.getLastName());
                    customerEntity.setEmail(customer.getEmail());
                    customerEntity.setPassword(customer.getPassword());

                    return customerEntity;
                }
        ).orElseThrow(() -> new CouponSystemException("Customer not found updateCustomer at admin service "));

    }

    /**
     *
     * @param id
     * @throws CouponSystemException customer not found
     */
    @Override
        public void deleteCustomer(long id) throws CouponSystemException {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new CouponSystemException("customer not found at deleteCustomer adminService"));
            System.out.println(customer);
            customerRepository.delete(customer);
        }

    /**
     *
      * @return List<Customer>
     */
    @Override
    public List<Customer> getAllCustomers() {
     return  customerRepository.findAll();
    }

    /**
     *
     * @param  id
     * @return
     * @throws CouponSystemException customer not found
     */
    @Override
    public Customer getOneCustomer(long id) throws CouponSystemException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CouponSystemException("customer not found at getOneCustomer"));

    }


}
