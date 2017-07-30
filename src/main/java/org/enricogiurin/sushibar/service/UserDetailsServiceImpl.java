package org.enricogiurin.sushibar.service;

import org.enricogiurin.sushibar.model.Role;
import org.enricogiurin.sushibar.model.User;
import org.enricogiurin.sushibar.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by enrico on 7/29/17.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Collection<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> list = new ArrayList<>(2);
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, user.getConfirmed(), list);
    }
}
