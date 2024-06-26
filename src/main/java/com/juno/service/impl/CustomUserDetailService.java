package com.juno.service.impl;

import com.juno.entity.User;
import com.juno.model.CustomUserDetail;
import com.juno.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByGmail(username);
        if(user.isPresent()) return new CustomUserDetail(user.get());
        else throw new UsernameNotFoundException("Can not find user : " + username);
    }
}
