package com.book.address.controller;

import com.book.address.dto.UserLoginDTO;
import com.book.address.dto.UserRegisterDTO;
import com.book.address.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userDetails")
@CrossOrigin("*")
public class LoginController
{
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO registerDTO)
    {
       return new ResponseEntity<>(userService.registerUser(registerDTO),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO)
    {
        return new ResponseEntity<>(userService.loginUser(loginDTO),HttpStatus.OK);
    }
}
