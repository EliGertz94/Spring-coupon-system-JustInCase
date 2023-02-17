package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.User;
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
    public boolean logIn(String email,String password,ClientType clientRole) throws CouponSystemException {

        User user=   userRepository.findByUsername(email)
                .orElseThrow(() -> new CouponSystemException("Admin not found ",HttpStatus.BAD_REQUEST));
        if(user.getPassword().equals(password) &&
        user.getClientRole().equals(clientRole)){
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
    public Company addCompany(User user,Company company) throws CouponSystemException {
        if(!userRepository.existsByUsername(user.getUsername()))
         {


             //unique company name
            if (!companyRepository.existsByName(company.getName())) {

                user.setClientRole(ClientType.Company);

                Company newCompany = companyRepository.save(company);
                newCompany.setUser(user);
                userRepository.save(user);

                return newCompany;
            }
            throw new CouponSystemException("addCompany error company name exists already",HttpStatus.BAD_REQUEST);

        }

        throw new CouponSystemException("addCompany error email  exists already",HttpStatus.BAD_REQUEST);

    }


    /**
     * checks if the email is unique or the same and mapping the new updated record accordingly
     * we can't change the name so no option for that
     * @param company
     * @return  Company
     * @throws CouponSystemException company not found
     */

    //     find by id of and change the username
        @Override
        public Company updateCompany( User user,Company company) throws CouponSystemException {


                User userRecord = userRepository.findById(user.getId())
                        .orElseThrow(()-> new CouponSystemException("user company not found updateCompany", HttpStatus.NOT_FOUND));

                Company companyRecord = companyRepository.findById(company.getId())
                        .orElseThrow(() -> new CouponSystemException("company not found updateCompany", HttpStatus.NOT_FOUND));


                if (user.getUsername().equals(userRecord.getUsername())) {
                    userRecord.setPassword(user.getPassword());
                    companyRecord.setUser(userRecord);
                    return companyRecord;

                }

                if(userRepository.existsByUsername(user.getUsername())){
                    throw new CouponSystemException("can't update - email is in use - updateCompany", HttpStatus.BAD_REQUEST);
                }

                userRecord.setPassword(user.getPassword());
                userRecord.setUsername(user.getUsername());
                companyRecord.setUser(userRecord);
                return companyRecord;
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
    public Customer addCustomer(User user, Customer customer) throws CouponSystemException {
        if(userRepository.existsByUsername(user.getUsername()))
        {
            throw new CouponSystemException("Email exists already addCustomer at adminService",HttpStatus.BAD_REQUEST);
        }

        Customer newCustomer = customerRepository.save(customer);
        newCustomer.setUser(user);
        userRepository.save(user);


        return customerRepository.save(customer);
    }

    /**
     *
     * @param customer
     * @return Customer
     * @throws CouponSystemException Customer not found
     */
    @Override
    public Customer updateCustomer(User user,Customer customer) throws CouponSystemException {

        Customer customerRecord = customerRepository.findById(customer.getId()).orElseThrow(()
                -> new CouponSystemException("Customer not found updateCustomer at admin service ",HttpStatus.NOT_FOUND));
        User userRecord = userRepository.findById(user.getId()).orElseThrow(()
                -> new CouponSystemException("user not found updateCustomer at admin service ",HttpStatus.NOT_FOUND));

        if(customerRecord.getUser().getUsername().equals(user.getUsername())){

            userRecord.setPassword(user.getPassword());

            customerRecord.setFirstName(customer.getFirstName());
            customerRecord.setLastName(customer.getLastName());
            customerRecord.setUser(userRecord);


             return customerRecord;

        }

        if(userRepository.existsByUsername(user.getUsername())){
            throw new CouponSystemException("can't update - email is in use - updateCustomer", HttpStatus.BAD_REQUEST);
        }

        userRecord.setPassword(user.getPassword());
        userRecord.setUsername(user.getUsername());

        customerRecord.setFirstName(customer.getFirstName());
        customerRecord.setLastName(customer.getLastName());
        customerRecord.setUser(userRecord);


        return customerRecord;


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
