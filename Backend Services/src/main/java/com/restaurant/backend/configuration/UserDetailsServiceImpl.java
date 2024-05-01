package com.restaurant.backend.configuration;

import com.restaurant.backend.dao.UserRepository;
import com.restaurant.backend.exception.ResourceNotFound;
import com.restaurant.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound("User", "user-name: " + username, 0));

        return new MyUserDetails(user);
    }
}
