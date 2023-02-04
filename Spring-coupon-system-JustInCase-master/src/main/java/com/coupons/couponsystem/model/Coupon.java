package com.coupons.couponsystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private Company company;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="category")
    private Category category;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int amount;
    private double price;
    private String image;

    private boolean buyable;

    //get rid of the cascaden
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    @JoinTable(name = "customer_coupons",
            joinColumns = @JoinColumn(name = "coupon_id" ,referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
    @ToString.Exclude
    @JsonIgnore
    private List<Customer> customers;

}
