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
    public AddressBook findById(int id,String userName)
    {
        User user=userRepository.findByUserName(userName).orElseThrow(()->new UserNotFoundException("User Not Found"));
        AddressBook addressBook=addressBookRepository.findById(id).orElseThrow(()->new AddressBookNotFoundException("Book Not Found 404"));
        if(user.getAddressBookList().contains(addressBook))
        {
            return addressBook;
        }
        return null;
    }

    @Override
    public List<AddressBook> getAllBooks(String userName)
    {
        User user=userRepository.findByUserName(userName).orElseThrow(()->new UserNotFoundException("User Not Found"));
        return addressBookRepository.findByUser(user);
    }

    @Override
    public UserResponseDTO updateBook(int id, AddressBookRequestDTO requestDTO,String userName)
    {
        User user=userRepository.findByUserName(userName).orElseThrow(()->new UserNotFoundException("User Not Found"));
        AddressBook book=addressBookRepository.findById(id).orElseThrow(()->new AddressBookNotFoundException("Book Not Found 404"));
        if(user.getAddressBookList().contains(book))
        {
            AddressBook updatedBook = addressBookMapper.updateBook(book, requestDTO);
            addressBookRepository.save(updatedBook);
            UserResponseDTO responseDTO=new UserResponseDTO();
            responseDTO.setResult(true);
            responseDTO.setMessage("Book Updated Successfully");
            return responseDTO;
            }
        else {
            UserResponseDTO responseDTO=new UserResponseDTO();
            responseDTO.setResult(false);
            responseDTO.setMessage("Book not  Updated!!");
            return responseDTO;
        }
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
            dto.setMessage("Address Book with name " + book.getFullName() + " has been deleted!!");
            return dto;
        }else{
            UserResponseDTO dto=new UserResponseDTO();
            dto.setResult(false);
            dto.setMessage("Address Book with name " + book.getFullName() + " has not found!!");
            return dto;
        }
    }

    @Override
    public UserDetails validateUserToken(String authHeader)
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
            return userDetails;
        else
            return null;
    }
}
