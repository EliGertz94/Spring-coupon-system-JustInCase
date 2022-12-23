package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.exception.ResourceNotFound;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CompanyServiceImpl extends ClientFacade  implements CompanyService {


  private long companyId;

    Logger logger= LoggerFactory.getLogger(CompanyServiceImpl.class);


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
        Company company = companyRepository.findById(this.companyId)
                .orElseThrow(() -> new CouponSystemException(HttpStatus.NOT_FOUND,"company not dound " ));
        logger.debug("coupon check statment {}"
                ,couponRepository.existsByTitleAndCompanyId(coupon.getTitle(),this.companyId) );

        if(couponRepository.existsByTitleAndCompanyId(coupon.getTitle(),this.companyId)){

            throw new CouponSystemException(HttpStatus.BAD_REQUEST,"addCoupon title in use already ");
        }
        coupon.setCompany(company);

        return  couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(Coupon coupon) {

     if(coupon.getCompany().getId()==this.companyId) {
         return couponRepository.findById(coupon.getId()).map(couponEntity -> {
                     couponEntity.setCategory(coupon.getCategory());
                     couponEntity.setTitle(coupon.getTitle());
                     couponEntity.setDescription(coupon.getDescription());
                     couponEntity.setStartDate(coupon.getStartDate());
                     couponEntity.setEndDate(coupon.getEndDate());
                     couponEntity.setAmount(coupon.getAmount());
                     couponEntity.setPrice(coupon.getPrice());
                     couponEntity.setImage(coupon.getImage());

                     return couponEntity;
                 }
         ).orElseThrow(() -> new ResourceNotFound("updateCompany", "company id", coupon.getId()));
     }

    throw new CouponSystemException(HttpStatus.BAD_REQUEST,"updateCoupon compant can't have access to this coupon");
    }




    @Override
    public void deleteCoupon(long couponId) {

            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new ResourceNotFound("deleteCoupon", "coupon id", couponId));
            if(coupon.getCompany().getId()!= this.companyId){
                throw new CouponSystemException(HttpStatus.BAD_REQUEST,"deleteCoupon can't delete this coupon ");
            }
            couponRepository.delete(coupon);

    }



    @Override
    public List<Coupon> getAllCompanyCoupons() {

        List<Coupon> coupons = couponRepository.findAllByCompany_id(this.companyId);
         return coupons;
    }

    @Override
    public List<Coupon> getAllCompanyCouponsByCategory(Category category) {
        return couponRepository.findAllByCompany_idAndCategory(this.companyId,category);
    }

    @Override
    public List<Coupon> getAllCompanyCouponsByPrice(double maxPrice) {

       return couponRepository.findAllByCompany_idAndPriceLessThanEqual(this.companyId,maxPrice);
    }

    @Override
    public Company getCompanyDetails() {
       return companyRepository.findFullCompany(this.companyId)
               .orElseThrow(() -> new CouponSystemException(HttpStatus.NOT_FOUND,"getCompanyDetails" ));


    }


}
