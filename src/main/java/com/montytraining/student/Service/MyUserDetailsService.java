package com.montytraining.student.Service;

import com.montytraining.student.Entities.User;
import com.montytraining.student.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username);
       if(user != null){
           return new org.springframework.security.core.userdetails.User(
                   user.getUsername(),
                   user.getPassword(),
                   getAuthority(user)

           );

       }else{
           throw new UsernameNotFoundException("User not found");
       }
    }

    private Set getAuthority(User user){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role->{
            authorities.add(new SimpleGrantedAuthority("ROLE_"+ role.getRoleName()));
        });
        return authorities;
    }
}
