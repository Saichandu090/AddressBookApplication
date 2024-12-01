package com.book.address.service;

import com.book.address.dto.LoginResponseDTO;
import com.book.address.dto.UserLoginDTO;
import com.book.address.dto.UserRegisterDTO;
import com.book.address.dto.UserResponseDTO;

public interface UserService
{
   UserResponseDTO registerUser(UserRegisterDTO registerDTO);

   UserResponseDTO loginUser(UserLoginDTO loginDTO);
}
