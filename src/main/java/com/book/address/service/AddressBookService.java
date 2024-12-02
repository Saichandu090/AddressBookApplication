package com.book.address.service;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.dto.UserResponseDTO;
import com.book.address.model.AddressBook;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressBookService
{
    public AddressBook addBook(AddressBookRequestDTO requestDTO,String userName);

    public AddressBook findByName(String fullName);

    public List<AddressBook> getAllBooks(String userName);

    public AddressBook updateBook(int id,AddressBookRequestDTO requestDTO);

    public UserResponseDTO deleteBook(int id, String userName);

    public boolean validateUserToken(String authHeader);
}
