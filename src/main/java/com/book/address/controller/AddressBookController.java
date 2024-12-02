package com.book.address.controller;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.dto.UserResponseDTO;
import com.book.address.service.AddressBookService;
import com.book.address.serviceimpl.JWTService;
import com.book.address.serviceimpl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addressBook")
@CrossOrigin("*")
public class AddressBookController
{
    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestHeader("Authorization") String authHeader,@RequestBody AddressBookRequestDTO requestDTO)
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
        {
            return new ResponseEntity<>(addressBookService.addBook(requestDTO,userName), HttpStatus.OK);
        }
        else{
            UserResponseDTO dto=new UserResponseDTO();
            dto.setResult(false);
            dto.setMessage("Book Not Added");
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }
    }

    @GetMapping("/byFullName/{fullName}")
    public ResponseEntity<?> findByFullName(@PathVariable String fullName)
    {
        return new ResponseEntity<>(addressBookService.findByName(fullName),HttpStatus.OK);
    }

    @GetMapping("/allBooks")
    public ResponseEntity<?> getAllBooks(@RequestHeader("Authorization") String authHeader)
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
        {
            return new ResponseEntity<>(addressBookService.getAllBooks(userDetails.getUsername()), HttpStatus.OK);
        }
        else{
            UserResponseDTO dto=new UserResponseDTO();
            dto.setResult(false);
            dto.setMessage("Login Failed");
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id,@RequestBody AddressBookRequestDTO requestDTO)
    {
        return new ResponseEntity<>(addressBookService.updateBook(id,requestDTO),HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<?> deleteBook(@RequestHeader("Authorization") String authHeader,@PathVariable int id)
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
        {
            return new ResponseEntity<>(addressBookService.deleteBook(id,userName),HttpStatus.OK);
        }
        else{
            UserResponseDTO dto=new UserResponseDTO();
            dto.setResult(false);
            dto.setMessage("Deletion Failed");
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }
    }
}
