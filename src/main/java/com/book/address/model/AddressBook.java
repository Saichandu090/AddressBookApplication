package com.book.address.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressBook
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    private Long phoneNumber;
    private String address;
    private String city;
    private String state;
    private int zipCode;

    @ManyToOne()
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User user;
}
