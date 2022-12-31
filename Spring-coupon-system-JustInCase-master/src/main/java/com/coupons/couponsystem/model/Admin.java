package com.coupons.couponsystem.model;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
@Table(name = "admin")
@EqualsAndHashCode(of = "id")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    @Column(name = "client_Role")
    @Enumerated(EnumType.STRING)
    private final ClientType clientRole =ClientType.Administrator;



}