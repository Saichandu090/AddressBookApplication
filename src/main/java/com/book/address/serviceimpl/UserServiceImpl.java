package com.book.address.serviceimpl;

import com.book.address.dto.LoginResponseDTO;
import com.book.address.dto.UserLoginDTO;
import com.book.address.dto.UserRegisterDTO;
import com.book.address.dto.UserResponseDTO;
import com.book.address.exception.UserNotFoundException;
import com.book.address.mapper.UserToUserResMapper;
import com.book.address.model.User;
import com.book.address.repository.UserRepository;
import com.book.address.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    private final UserToUserResMapper userResponse=new UserToUserResMapper();

    @Override
    public UserResponseDTO registerUser(UserRegisterDTO registerDTO)
    {
        boolean rs=userRepository.existsByEmail(registerDTO.getEmail());
        if(rs){
            return userResponse.userAlreadyExists();
        }else {
            User user = new User();
            user.setEmail(registerDTO.getEmail());
            user.setUserName(registerDTO.getUserName());
            user.setPassword(encoder.encode(registerDTO.getPassword()));
            return userResponse.convertUser(userRepository.save(user));
        }
    }

    @Override
    public UserResponseDTO loginUser(UserLoginDTO loginDTO)
    {
        User user=userRepository.findByUserName(loginDTO.getUserName()).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Authentication authentication=
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(),loginDTO.getPassword()));
        if(authentication.isAuthenticated())
        {
            System.out.println("User Authenticated");
            String token=jwtService.generateToken(loginDTO.getUserName());
            return userResponse.login(loginDTO,token);
        }
        else {
            UserResponseDTO dto=new UserResponseDTO();
            dto.setResult(false);
            dto.setMessage("Login Failed");
            return dto;
        }
    }
}
