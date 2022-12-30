package com.coupons.couponsystem.Repositoty;

import com.coupons.couponsystem.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

//    boolean existsBy(String email,String password);

    Optional<Company> findByEmailAndPassword(String email, String Password);
    Company findByEmail(String email);


    boolean existsByEmailAndPassword(String email,String password);

    boolean existsByEmail(String email);
    boolean existsByPassword(String password);

    Company findByName(String name);

  //  List<Company> getAllCompanies();

    @Query("select c from Company c join fetch c.coupons where c.id = :id")
    Optional<Company> findFullCompany(Long id);

}
