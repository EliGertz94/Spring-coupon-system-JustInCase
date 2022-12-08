package com.coupons.couponsystem.service;

import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;

import java.util.List;


public interface AdminService {

 //   boolean existsBy(String email,String password) throws Exception;

    public boolean logIn(String email,String password);

    Company addCompany(Company company);

    Company updateCompany(Company company);

    void deleteCompany(long id);

    List<Company> getAllCompanies();

    Company getOneCompany(long id);

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(long id);

    List<Customer> getAllCustomers();

    Customer getOneCustomer(long id);


}
