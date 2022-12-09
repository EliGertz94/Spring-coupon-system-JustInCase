package com.coupons.couponsystem.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


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
    @JoinColumn(name = "company_id", referencedColumnName = "id",nullable = false)
    @ToString.Exclude
    private Company company;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="category")
  //  @ToString.Exclude
    private Category category;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int amount;
    private double price;
    private String image;



}
