package com.book.address.exception;

import com.book.address.dto.UserResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(AddressBookNotFoundException.class)
    public ResponseEntity<?> addressBookNotFound(AddressBookNotFoundException e)
    {
        UserResponseDTO responseDTO=new UserResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(UserNotFoundException e)
    {
        UserResponseDTO responseDTO=new UserResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail exceptions(Exception ex)
    {
        ProblemDetail errorDetail=null;
        if(ex instanceof ExpiredJwtException) {
            errorDetail=ProblemDetail.
                    forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","JWT token expired!!");
        }

        if(ex instanceof SignatureException) {
            errorDetail=ProblemDetail.
                    forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("access_denied_reason","JWT Signature not matched!");
        }
        return errorDetail;
    }
}
