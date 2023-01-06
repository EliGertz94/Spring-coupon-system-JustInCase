package com.coupons.couponsystem.model;


import com.coupons.couponsystem.ClientLogIn.ClientType;
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
    private String email;
    private String password;

    @Column(name = "client_Role")
    @Enumerated(EnumType.STRING)
    private  ClientType clientRole;

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    @JoinTable(name = "customer_coupons",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id" ,referencedColumnName = "id"))
    @ToString.Exclude
    private List<Coupon> coupons;
}
