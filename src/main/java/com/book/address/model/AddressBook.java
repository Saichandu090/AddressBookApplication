package com.book.address.model;

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

    @ManyToOne
    private User user;
}
