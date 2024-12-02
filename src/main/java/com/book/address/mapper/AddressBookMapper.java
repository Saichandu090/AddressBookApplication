package com.book.address.mapper;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.dto.UserResponseDTO;
import com.book.address.model.AddressBook;
import com.book.address.model.User;


public class AddressBookMapper
{
    public AddressBook addBook(AddressBookRequestDTO requestDTO, User user)
    {
        AddressBook book=new AddressBook();
        book.setFullName(requestDTO.getFullName());
        book.setAddress(requestDTO.getAddress());
        book.setPhoneNumber(requestDTO.getPhoneNumber());
        book.setCity(requestDTO.getCity());
        book.setState(requestDTO.getState());
        book.setZipCode(requestDTO.getZipCode());
        book.setUser(user);
        return book;
    }

    public AddressBook updateBook(AddressBook book,AddressBookRequestDTO requestDTO)
    {
        book.setFullName(requestDTO.getFullName());
        book.setAddress(requestDTO.getAddress());
        book.setPhoneNumber(requestDTO.getPhoneNumber());
        book.setCity(requestDTO.getCity());
        book.setState(requestDTO.getState());
        book.setZipCode(requestDTO.getZipCode());
        return book;
    }

    public UserResponseDTO userDetailsFailure()
    {
        UserResponseDTO dto=new UserResponseDTO();
        dto.setResult(false);
        dto.setMessage("Invalid User Details");
        return dto;
    }

}
