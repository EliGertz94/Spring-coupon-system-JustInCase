package com.coupons.couponsystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    private String email;
//    private String password;


//    @Column(name = "client_Role")
//    @Enumerated(EnumType.STRING)
//    private  ClientType clientRole;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Coupon> coupons ;

    public Company(Long id, String name,  List<Coupon> coupons) {
        this.id = id;
        this.name = name;

//
//        this.email = email;
//
//        this.password = password;


        if(coupons!=null){
            this.coupons = coupons;
            for (Coupon coupon :
                    coupons) {
                coupon.setCompany(this);
            }

            this.coupons=coupons;
        }
        this.coupons= new LinkedList<>();
    }

    public void setCoupons(List<Coupon> coupons) {
        for (Coupon coupon :
                coupons) {
            coupon.setCompany(this);
        }

        this.coupons=coupons;
    }

    // the owner of the relationships is the one in the onetomany relations
    //JoinColumn is the owner
    //mappedBy is the
    //the many will hold the foreign key
    //the one is mapping
    //persist to do the save forward
    //



}
