package com.example.istocks.service;

import com.example.istocks.dto.UserDto;
import com.example.istocks.model.IStocksUser;
import com.example.istocks.repository.IStocksUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IStocksUserRepository iStocksUserRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IStocksUser user = iStocksUserRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found for: " + username);
        }

        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public IStocksUser save(UserDto userDto) {
        IStocksUser newUser = IStocksUser.builder()
            .email(userDto.getEmail())
            .name(userDto.getName())
            .password(bcryptEncoder.encode(userDto.getPassword()))
            .build();

        IStocksUser savedUser = iStocksUserRepository.save(newUser);
        transactionService.createInitialTransaction(savedUser.getEmail());
        return savedUser;
    }

}