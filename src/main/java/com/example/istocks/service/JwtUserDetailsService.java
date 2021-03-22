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

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IStocksUserRepository IStocksUserRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IStocksUser user = IStocksUserRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found for: " + username);
        }
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public IStocksUser save(UserDto userDto) {
        IStocksUser newUser = new IStocksUser();
        newUser.setEmail(userDto.getEmail());
        newUser.setName(userDto.getName());
        newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));

        walletService.initiateWalletWithAmount(BigDecimal.valueOf(20000), userDto.getEmail());

        return IStocksUserRepository.save(newUser);
    }


}