package com.book.address.serviceimpl;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.exception.AddressBookNotFoundException;
import com.book.address.mapper.AddressBookMapper;
import com.book.address.model.AddressBook;
import com.book.address.repository.AddressBookRepository;
import com.book.address.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService
{
    @Autowired
    private AddressBookRepository addressBookRepository;

    private final AddressBookMapper addressBookMapper=new AddressBookMapper();

    @Override
    public AddressBook addBook(AddressBookRequestDTO requestDTO)
    {
        AddressBook book=addressBookMapper.addBook(requestDTO);
        return addressBookRepository.save(book);
    }

    @Override
    public AddressBook findByName(String fullName)
    {
        return addressBookRepository.findByFullName(fullName).orElseThrow(()->new AddressBookNotFoundException("Book Not Found 404"));
    }

    @Override
    public List<AddressBook> getAllBooks()
    {
        return addressBookRepository.findAll();
    }

    @Override
    public AddressBook updateBook(String fullName, AddressBookRequestDTO requestDTO)
    {
        AddressBook book=addressBookRepository.findByFullName(fullName).orElseThrow(()->new AddressBookNotFoundException("Book Not Found 404"));
        AddressBook updatedBook=addressBookMapper.updateBook(book,requestDTO);
        return addressBookRepository.save(updatedBook);
    }

    @Override
    public String deleteBook(String fullName)
    {
        AddressBook book=addressBookRepository.findByFullName(fullName).orElseThrow(()->new AddressBookNotFoundException("Book Not Found 404"));
        addressBookRepository.delete(book);
        return "Address Book with name "+fullName+" has been deleted!!";
    }
}
