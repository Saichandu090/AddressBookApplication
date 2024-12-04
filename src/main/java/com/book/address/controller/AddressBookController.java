package com.book.address.controller;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.dto.UserResponseDTO;
import com.book.address.mapper.AddressBookMapper;
import com.book.address.model.AddressBook;
import com.book.address.service.AddressBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addressBook")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class AddressBookController
{
    @Autowired
    private AddressBookService addressBookService;

    private AddressBookMapper addressBookMapper;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestHeader("Authorization") String authHeader,@Valid @RequestBody AddressBookRequestDTO requestDTO)
    {
        UserDetails userDetails= addressBookService.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(addressBookService.addBook(requestDTO, userDetails.getUsername()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(addressBookMapper.userDetailsFailure(),HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> findByFullName(@RequestHeader("Authorization") String authHeader,@PathVariable int id)
    {
        UserDetails userDetails= addressBookService.validateUserToken(authHeader);
        if(userDetails!=null) {
            AddressBook ad=addressBookService.findById(id,userDetails.getUsername());
            if(ad!=null)
                return new ResponseEntity<>(ad, HttpStatus.OK);
            else {
                UserResponseDTO dto=new UserResponseDTO();
                dto.setResult(false);
                dto.setMessage("Book Not associated with this user");
                return new ResponseEntity<>(dto,HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(addressBookMapper.userDetailsFailure(),HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/allBooks")
    public ResponseEntity<?> getAllBooks(@RequestHeader("Authorization") String authHeader)
    {
        UserDetails userDetails= addressBookService.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(addressBookService.getAllBooks(userDetails.getUsername()), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(addressBookMapper.userDetailsFailure(),HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@RequestHeader("Authorization") String authHeader,@PathVariable int id,@Valid @RequestBody AddressBookRequestDTO requestDTO)
    {
        UserDetails userDetails= addressBookService.validateUserToken(authHeader);
        if(userDetails!=null) {
            UserResponseDTO ad=addressBookService.updateBook(id, requestDTO,userDetails.getUsername());
            if(ad!=null){
                 return new ResponseEntity<>(ad, HttpStatus.OK);
            }else{
                UserResponseDTO dto=new UserResponseDTO();
                dto.setResult(false);
                dto.setMessage("Book Not Associated with the User");
                return new ResponseEntity<>(dto,HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(addressBookMapper.userDetailsFailure(),HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<?> deleteBook(@RequestHeader("Authorization") String authHeader,@PathVariable int id)
    {
        UserDetails userDetails= addressBookService.validateUserToken(authHeader);
        if(userDetails!=null)
        {
            return new ResponseEntity<>(addressBookService.deleteBook(id, userDetails.getUsername()),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(addressBookMapper.userDetailsFailure(),HttpStatus.FORBIDDEN);
        }
    }
}
