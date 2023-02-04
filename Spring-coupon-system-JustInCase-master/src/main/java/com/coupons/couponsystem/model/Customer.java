package com.coupons.couponsystem.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
//    private String email;
//    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userName")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    @JoinTable(name = "customer_coupons",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id" ,referencedColumnName = "id"))
    @ToString.Exclude
    private List<Coupon> coupons;

    public Customer(long id, String firstName, String lastName, List<Coupon> coupons) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.coupons = coupons;
    }
}
