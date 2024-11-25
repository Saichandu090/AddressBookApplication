package com.book.address.service;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.model.AddressBook;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressBookService
{
    public AddressBook addBook(AddressBookRequestDTO requestDTO);

    public AddressBook findByName(String fullName);

    public List<AddressBook> getAllBooks();

    public AddressBook updateBook(String fullName,AddressBookRequestDTO requestDTO);

    public String deleteBook(String fullName);
}
