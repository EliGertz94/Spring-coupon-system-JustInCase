package com.coupons.couponsystem.model;

import com.coupons.couponsystem.ClientLogIn.ClientType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String username;
    private String password;
    @Column(name = "client_Role")
    @Enumerated(EnumType.STRING)
    private ClientType clientRole;



//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Customer customer;
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Company company;

//    @ToString.Exclude
//    @JsonIgnore
//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
//    private List<Role> roles;

}
