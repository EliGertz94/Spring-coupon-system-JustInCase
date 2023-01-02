package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Admin;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.service.AdminService;
import org.springframework.http.HttpStatus;
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
                .orElseThrow(() -> new CouponSystemException("Admin not found ",HttpStatus.BAD_REQUEST));
        return true;
    }

    /**
     * @param company
     * @return Company
     * @throws CouponSystemException if the entity doesn't exist
     */
    @Override
    public Company addCompany(Company company) throws CouponSystemException {
        if(!companyRepository.existsByEmail(company.getEmail())
            && !customerRepository.existsByEmail(company.getEmail()))
         {
            if (!companyRepository.existsByPassword(company.getPassword())) {
                company.setClientRole(ClientType.Company);
                Company newCompany = companyRepository.save(company);
                return newCompany;
            }
            throw new CouponSystemException("addCompany error password exists already",HttpStatus.BAD_REQUEST);

        }

        throw new CouponSystemException("addCompany error email  exists already",HttpStatus.BAD_REQUEST);

    }


    /**
     * checks if the email is unique or the same and mapping the new updated record accordingly
     * @param company
     * @return  Company
     * @throws CouponSystemException company not found
     */
        @Override
        public Company updateCompany(Company company) throws CouponSystemException {

                Company companyRecord = companyRepository.findById(company.getId())
                        .orElseThrow(() -> new CouponSystemException("company not found updateCompany", HttpStatus.NOT_FOUND));

                if (company.getEmail().equals(companyRecord.getEmail())) {
                    return  companyRepository.findById(company.getId()).map(companyEntity -> {

                                companyEntity.setEmail(company.getEmail());

                                companyEntity.setPassword(company.getPassword());

                                return companyEntity;
                            }
                    ).orElseThrow(() -> new CouponSystemException("company not found updateCompany", HttpStatus.NOT_FOUND));
                }

                if(companyRepository.existsByEmail(company.getEmail())
                ||adminRepository.existsByEmail(company.getEmail())
                ||customerRepository.existsByEmail(company.getEmail())){
                    throw new CouponSystemException("can't update - email is in use - updateCompany", HttpStatus.BAD_REQUEST);
                }

            return  companyRepository.findById(company.getId()).map(companyEntity -> {

                        companyEntity.setEmail(company.getEmail());

                        companyEntity.setPassword(company.getPassword());

                        return companyEntity;
                    }
            ).orElseThrow(() -> new CouponSystemException("company not found updateCompany", HttpStatus.NOT_FOUND));

            }


    /**
     *
     * @param id
     * @throws CouponSystemException company not found
     */
    public void deleteCompany(long id) throws CouponSystemException {
            Company company =  companyRepository.findById(id)
                    .orElseThrow(() -> new CouponSystemException("deleteCompany company not found",HttpStatus.NOT_FOUND));

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
                  (() -> new CouponSystemException("company not found at getOneCompany",HttpStatus.NOT_FOUND));

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
            throw new CouponSystemException("Email exists already addCustomer at adminService",HttpStatus.BAD_REQUEST);
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

        Customer customerRecord = customerRepository.findById(customer.getId()).orElseThrow(()
                -> new CouponSystemException("Customer not found updateCustomer at admin service ",HttpStatus.NOT_FOUND));

        if(customer.getEmail().equals(customerRecord.getEmail())){
            return customerRepository.findById(customer.getId()).map(customerEntity -> {
                        customerEntity.setFirstName(customer.getFirstName());
                        customerEntity.setLastName(customer.getLastName());
                        customerEntity.setEmail(customer.getEmail());
                        customerEntity.setPassword(customer.getPassword());

                        return customerEntity;
                    }
            ).orElseThrow(()
                    -> new CouponSystemException("Customer not found updateCustomer at admin service ",HttpStatus.NOT_FOUND));
        }

        if(customerRepository.existsByEmail(customer.getEmail())
        || adminRepository.existsByEmail(customer.getEmail())
        ||companyRepository.existsByEmail(customer.getEmail())){
            throw new CouponSystemException("can't update - email is in use - updateCustomer", HttpStatus.BAD_REQUEST);
        }

        return customerRepository.findById(customer.getId()).map(customerEntity -> {
                    customerEntity.setFirstName(customer.getFirstName());
                    customerEntity.setLastName(customer.getLastName());
                    customerEntity.setEmail(customer.getEmail());
                    customerEntity.setPassword(customer.getPassword());

                    return customerEntity;
                }
        ).orElseThrow(()
                -> new CouponSystemException("Customer not found updateCustomer at admin service ",HttpStatus.NOT_FOUND));



    }

    /**
     *
     * @param id
     * @throws CouponSystemException customer not found
     */
    @Override
        public void deleteCustomer(long id) throws CouponSystemException {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new CouponSystemException("customer not found at deleteCustomer adminService",HttpStatus.NOT_FOUND));
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
                .orElseThrow(() -> new CouponSystemException("customer not found at getOneCustomer",HttpStatus.NOT_FOUND));

    }


}
