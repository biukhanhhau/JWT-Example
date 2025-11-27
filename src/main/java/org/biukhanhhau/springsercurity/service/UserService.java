package org.biukhanhhau.springsercurity.service;

import org.biukhanhhau.springsercurity.config.SercurityConfig;
import org.biukhanhhau.springsercurity.model.User;
import org.biukhanhhau.springsercurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }
}
