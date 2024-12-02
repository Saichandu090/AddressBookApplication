package com.book.address.serviceimpl;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.dto.UserResponseDTO;
import com.book.address.exception.AddressBookNotFoundException;
import com.book.address.exception.UserNotFoundException;
import com.book.address.mapper.AddressBookMapper;
import com.book.address.model.AddressBook;
import com.book.address.model.User;
import com.book.address.repository.AddressBookRepository;
import com.book.address.repository.UserRepository;
import com.book.address.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService
{
    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    private final AddressBookMapper addressBookMapper=new AddressBookMapper();

    @Override
    public AddressBook addBook(AddressBookRequestDTO requestDTO,String userName)
    {
        User user=userRepository.findByUserName(userName).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        AddressBook book=addressBookMapper.addBook(requestDTO,user);
        AddressBook addressBook=addressBookRepository.save(book);
        user.setAddressBookList(List.of(addressBook));
        return addressBook;
    }

    @Override
    public AddressBook findByName(String fullName)
    {
        return addressBookRepository.findByFullName(fullName).orElseThrow(()->new AddressBookNotFoundException("Book Not Found 404"));
    }

    @Override
    public List<AddressBook> getAllBooks(String userName)
    {
        User user=userRepository.findByUserName(userName).orElseThrow(()->new UserNotFoundException("User Not Found"));
        return addressBookRepository.findByUser(user);
    }

    @Override
    public AddressBook updateBook(int id, AddressBookRequestDTO requestDTO)
    {
        AddressBook book=addressBookRepository.findById(id).orElseThrow(()->new AddressBookNotFoundException("Book Not Found 404"));
        AddressBook updatedBook=addressBookMapper.updateBook(book,requestDTO);
        return addressBookRepository.save(updatedBook);
    }

    @Override
    public UserResponseDTO deleteBook(int id,String userName)
    {
        AddressBook book=addressBookRepository.findById(id).orElseThrow(()->new AddressBookNotFoundException("Book Not Found 404"));
        User user=userRepository.findByUserName(userName).orElseThrow(()->new UserNotFoundException("User Not Found"));
        if(user.getAddressBookList().contains(book))
        {
            user.getAddressBookList().remove(book);
            addressBookRepository.delete(book);
            UserResponseDTO dto=new UserResponseDTO();
            dto.setResult(true);
            dto.setMessage("Address Book with name " + id + " has been deleted!!");
            return dto;
        }else{
            UserResponseDTO dto=new UserResponseDTO();
            dto.setResult(false);
            dto.setMessage("Address Book with id " + id + " has not found!!");
            return dto;
        }
    }

    @Override
    public boolean validateUserToken(String authHeader)
    {
        String token=null;
        String userName=null;
        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token=authHeader.substring(7);
            userName=jwtService.extractUserName(token);
        }
        UserDetails userDetails=context.getBean(MyUserDetailsService.class).loadUserByUsername(userName);
        if(jwtService.validateToken(token,userDetails))
            return true;
        else
            return false;
    }
}
