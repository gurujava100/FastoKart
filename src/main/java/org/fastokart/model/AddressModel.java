package org.fastokart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String phone;
    private String name;
    private String city;
    private String state;
    private String pincode;
    private String landmark;
    private String fullAddress;

    private boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;
}
