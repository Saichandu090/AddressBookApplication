package com.book.address.service;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.dto.UserResponseDTO;
import com.book.address.model.AddressBook;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressBookService
{
    public AddressBook addBook(AddressBookRequestDTO requestDTO,String userName);

    public AddressBook findById(int id,String userName);

    public List<AddressBook> getAllBooks(String userName);

    public UserResponseDTO updateBook(int id,AddressBookRequestDTO requestDTO,String userName);

    public UserResponseDTO deleteBook(int id, String userName);

    public UserDetails validateUserToken(String authHeader);
}
