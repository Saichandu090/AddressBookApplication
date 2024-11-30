package com.book.address.exception;

import com.book.address.dto.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(AddressBookNotFoundException.class)
    public ResponseEntity<?> addressBookNotFound(AddressBookNotFoundException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(UserNotFoundException e)
    {
        UserResponseDTO responseDTO=new UserResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
