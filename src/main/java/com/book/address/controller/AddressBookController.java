package com.book.address.controller;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addressBook")
@CrossOrigin("*")
public class AddressBookController
{
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody AddressBookRequestDTO requestDTO)
    {
        return new ResponseEntity<>(addressBookService.addBook(requestDTO), HttpStatus.OK);
    }

    @GetMapping("/byFullName/{fullName}")
    public ResponseEntity<?> findByFullName(@PathVariable String fullName)
    {
        return new ResponseEntity<>(addressBookService.findByName(fullName),HttpStatus.OK);
    }

    @GetMapping("/allBooks")
    public ResponseEntity<?> getAllBooks()
    {
        return new ResponseEntity<>(addressBookService.getAllBooks(),HttpStatus.OK);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id,@RequestBody AddressBookRequestDTO requestDTO)
    {
        return new ResponseEntity<>(addressBookService.updateBook(id,requestDTO),HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id)
    {
        return new ResponseEntity<>(addressBookService.deleteBook(id),HttpStatus.OK);
    }
}
