package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.Repositoty.CompanyRepository;
import com.coupons.couponsystem.Repositoty.CouponRepository;
import com.coupons.couponsystem.Repositoty.CustomerRepository;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.exception.ResourceNotFound;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CompanyServiceImpl extends ClientFacade  implements CompanyService {


    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CouponRepository couponRepository;

    private long companyId;



    @Override
    public boolean logIn(String email,String password){

      Company company=   companyRepository.findByEmailAndPassword(email,password)
                .orElseThrow(() -> new CouponSystemException(HttpStatus.NOT_FOUND,"logIn company service " ));

      if(companyRepository.existsByEmail(email)
              && companyRepository.existsByPassword(password)) {
          companyId = company.getId();
          return true;
      }
       return false;

    }

    @Override
    public Coupon addCoupon(Coupon coupon) {

        if(couponRepository.existsByTitle(coupon.getTitle())){
            throw new CouponSystemException(HttpStatus.BAD_REQUEST,"addCoupon title in use already ");
        }
        Company company = companyRepository.findById(this.companyId)
        .orElseThrow(() -> new CouponSystemException(HttpStatus.NOT_FOUND,"company not dound " ));

        coupon.setCompany(company);

        return  couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new ResourceNotFound("deleteCoupon", "coupon id", couponId));

         couponRepository.delete(coupon);

    }


    /// why am  I getting stackoverflow??!?!?!?!?
    @Override
    public Company getAllCompanyCoupons() {
        Company company = companyRepository.findFullCompany(this.companyId)
                .orElseThrow(() -> new ResourceNotFound("deleteCoupon", "coupon id", this.companyId));
//couponRepository.findAllByCompanyId(this.companyId);
//        List<String> couponsNames= new ArrayList<>();
//        for(Coupon coupon:coupons ){
//            couponsNames.add(coupon.getTitle());
//        }

         return company;
    }

    @Override
    public List<Coupon> getAllCompanyCouponsByCategory(Category category) {

//        Company company = companyRepository.findFullCompany(this.companyId)
//                .orElseThrow(() -> new ResourceNotFound("deleteCoupon", "coupon id", this.companyId));

   //     return company.getCoupons().stream().
        return couponRepository.findAllByCompanyIdAndCategory(this.companyId,category);
    }

//    @Override
//    public List<Coupon> getAllCompanyCouponsByPrice(int maxPrice) {
//        return couponRepository.findAllByCompanyIdAndMaxPrice(this.companyId,maxPrice);
//    }

    @Override
    public Company getCompanyDetails() {
        return null;
    }


}
