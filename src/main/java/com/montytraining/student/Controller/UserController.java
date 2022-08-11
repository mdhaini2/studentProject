package com.montytraining.student.Controller;

import com.montytraining.student.Entities.Response;
import com.montytraining.student.Entities.Student;
import com.montytraining.student.Entities.User;
import com.montytraining.student.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/registerUser")
    public Response registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }
    @PostMapping(value = "/addRoleToUser")
    public Response addRoleToUser(@RequestParam int userId,@RequestParam(value = "user_role") String role){
        return userService.addRoleToUser(userId,role);
    }
    @GetMapping(value = "/authenticateUser")
    public Object registerUser(@RequestParam String username, @RequestParam String password){
        return userService.authenticateUser(username,password);
    }
}
