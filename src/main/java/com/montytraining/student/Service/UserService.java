package com.montytraining.student.Service;

import com.montytraining.student.Entities.Response;
import com.montytraining.student.Entities.Role;
import com.montytraining.student.Entities.User;
import com.montytraining.student.Repository.RoleRepository;
import com.montytraining.student.Repository.UserRepository;
import com.montytraining.student.Util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private RoleRepository roleRepository;

    public Object authenticateUser(String username, String password) {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            Response response = new Response(null, 401, "Username not found");
            return response;
        }

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            Response response = new Response(null, 401, "Incorrect Password");
            return response;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        user.setToken(jwt);
        Response response = new Response(user, 200, "User logged in successfully");
        return response;

    }

    public Response registerUser(User user) {
        Role role = roleRepository.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        Response response = new Response(user, 200, "User registered Successfully");
        return response;
    }

    public Response addRoleToUser(int userId, String role) {
        log.info("ROLELEEE" + role);
        Role roles = roleRepository.findById(role).get();
        User user = userRepository.findById(userId).get();
        Set<Role> userRoles = user.getRole();
        userRoles.add(roles);
        userRepository.save(user);
        Response response = new Response(user, 200, "Added new role to user!");
        return response;
    }

}
