package com.company.cattos.security.service;

import com.company.cattos.security.SecurityUser;
import com.company.cattos.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) {
        email = (email.startsWith("\"") && email.endsWith("\"")) ?
                email.substring(1, email.length() - 1) : email;

        var user = userRepository.findUserByEmail(email);
        var securityUser = user.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException("Could not find the user by this username!"));

        return new User(securityUser.getUsername(), securityUser.getPassword(), securityUser.getAuthorities());
    }


}
