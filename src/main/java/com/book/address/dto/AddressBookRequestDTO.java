package com.book.address.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBookRequestDTO
{
    @NotNull
    private String fullName;
    private Long phoneNumber;

    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String state;
    private int zipCode;
}
