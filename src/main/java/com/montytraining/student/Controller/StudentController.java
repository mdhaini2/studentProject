package com.montytraining.student.Controller;


import com.montytraining.student.Entities.Student;
import com.montytraining.student.Exception.IncorrectAgeException;
import com.montytraining.student.Exception.IncorrectGpaException;
import com.montytraining.student.Entities.Response;
import com.montytraining.student.Service.MyUserDetailsService;
import com.montytraining.student.Service.StudentService;
import com.montytraining.student.Util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@Log4j2
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;


    @GetMapping("/getAllStudents")
    public Response getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/getAllEquipment")
    public Response getAllEquipment() {
        return studentService.getAllEquipment();
    }

    @GetMapping("/clearRedis")
    public Response clearRedis() {
        return studentService.clearRedis();
    }


    @GetMapping("/getAgeAvg")
    public float getAvg() {
        return studentService.getAverageAge();
    }

    @GetMapping(value = "/getByFirstName")
    public ArrayList<Student> getByFirstName(@RequestParam String prefix) {
        return studentService.getFirstNameLike(prefix);

    }

    @GetMapping(value = "/getByLastName")
    public ArrayList<Student> getByLastName(@RequestParam String prefix) {
        return studentService.getLastNameLike(prefix);

    }

    @GetMapping(value = "/getNameWithAgeAndGPA")
    public Response getNameWithAgeAndGPA(@RequestParam double gpa, @RequestParam int age) throws IncorrectGpaException, IncorrectAgeException {

        Response response = studentService.getNameWithAgeAndGPA(gpa, age);

        return response;


    }

    @PostMapping(value = "/user/addStudent")
    public void addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @PutMapping(value = "/admin/updateStudentGrade/{id}")
    public String updateStudentGrade(@RequestParam double gpa, @PathVariable int id) throws IncorrectGpaException {
        return studentService.updateStudentGrade(gpa, id);
    }

    @DeleteMapping(value = "/removeStudent/{id}")
    public String removeStudent(@PathVariable int id) throws Exception {
        return studentService.removeStudent(id);
    }


    @GetMapping("/getHighStudents")
    public ArrayList<String> getHighStudents() {
        return studentService.getHighStudent();
    }


    @GetMapping(value = "/admin/getStudentInfo")
    public Optional<Student> getStudentInfo(@RequestParam int id) {
        log.info("Fetched from the db");
        return studentService.getStudentInfo(id);

    }



}