package com.book.address.controller;

import com.book.address.dto.AddressBookRequestDTO;
import com.book.address.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController
{
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody AddressBookRequestDTO requestDTO)
    {
        return new ResponseEntity<>(addressBookService.addBook(requestDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping("/byFullName/{fullName}")
    public ResponseEntity<?> findByFullName(@PathVariable String fullName)
    {
        return new ResponseEntity<>(addressBookService.findByName(fullName),HttpStatus.FOUND);
    }

    @GetMapping("/allBooks")
    public ResponseEntity<?> getAllBooks()
    {
        return new ResponseEntity<>(addressBookService.getAllBooks(),HttpStatus.FOUND);
    }

    @PutMapping("/updateBook/{fullName}")
    public ResponseEntity<?> updateBook(@PathVariable String fullName,@RequestBody AddressBookRequestDTO requestDTO)
    {
        return new ResponseEntity<>(addressBookService.updateBook(fullName,requestDTO),HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{fullName}")
    public ResponseEntity<?> deleteBook(@PathVariable String fullName)
    {
        return new ResponseEntity<>(addressBookService.deleteBook(fullName),HttpStatus.ACCEPTED);
    }
}
