package com.book.address.serviceimpl;

import com.book.address.exception.UserNotFoundException;
import com.book.address.model.User;
import com.book.address.model.UserPrinciple;
import com.book.address.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User existingUser=userRepository.findByUserName(username).orElseThrow(()->new UserNotFoundException("User Not Found"));
        return new UserPrinciple(existingUser);
    }
}
