package com.montytraining.student.Service;


import com.montytraining.student.Entities.Equipment;
import com.montytraining.student.Entities.Student;
import com.montytraining.student.Exception.IdNotValidException;
import com.montytraining.student.Exception.IncorrectAgeException;
import com.montytraining.student.Exception.IncorrectGpaException;
import com.montytraining.student.Exception.ResourceNotFoundException;
import com.montytraining.student.Producers.Producer;
import com.montytraining.student.Producers.ProducerList;
import com.montytraining.student.Repository.EquipmentRepository;
import com.montytraining.student.Repository.StudentRepository;
import com.montytraining.student.Entities.Response;
import com.montytraining.student.Repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class StudentService {

    public static final String HASH_KEY = "Student";
    @Autowired
    private Producer producer;
    @Autowired
    private StudentRepository studentRepository;


    private ArrayList<Student> studentList = new ArrayList<Student>();

    @Autowired
    private ProducerList producerList;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    EquipmentRepository equipmentRepository;

    public Response getAllStudents() {
        studentList = new ArrayList<>();
        studentList = (ArrayList<Student>) redisTemplate.opsForValue().get(HASH_KEY);

        if (studentList == null) {
            studentList = new ArrayList<>();
            studentRepository.findAll().forEach(studentList::add);
            producerList.sendJsonMessage(studentList);
            redisTemplate.opsForHash().entries(studentList);
            redisTemplate.opsForValue().set(HASH_KEY, studentList);
            Response response = new Response(studentList, 1, "Fetched from the database");
            return response;
        }

        Response response = new Response(studentList, 1, "Fetched from Redis");
        return response;
    }

    public ArrayList<Student> getFirstNameLike(String prefix) {
        return studentRepository.findByNameStartingWith(prefix);
    }

    public ArrayList<Student> getLastNameLike(String prefix) {
        return studentRepository.findByLastNameStartingWith(prefix);
    }

    public Response getNameWithAgeAndGPA(double gpa, int age) throws IncorrectAgeException, IncorrectGpaException {

        if (gpa > 4.2 || gpa < 0) {
            throw new IncorrectGpaException("GPA not valid");
        }
        if (age <= 0) {

            throw new IncorrectAgeException("Age not valid");
        } else {
            ArrayList<String> student = studentRepository.getStudentNameWithAgeGPA(gpa, age);
            if (student.isEmpty()) {
                throw new ResourceNotFoundException("Students with these criteria not found");
            } else {
                Response response = new Response(studentRepository.getStudentNameWithAgeGPA(gpa, age), 1, "Success");
                return response;
            }

        }


    }

    public Response clearRedis() {
        if (redisTemplate.delete(HASH_KEY)) {
            Response response = new Response(null, 200, "Redis cleared");
            return response;
        } else {
            Response response = new Response(null, 500, "Redis already empty");
            return response;
        }

    }

    public void addStudent(Student student) {
        log.info(student.getName() + " added as student to the database");
        studentRepository.save(student);
    }

    public float getAverageAge() {
        getAllStudents();
        int counter = 0;

        for (int i = 0; i < studentList.size(); i++) {
            counter = studentList.get(i).getAge() + counter;
        }

        float avg = counter / studentList.size();
        log.info("Getting age average Info");
        log.warn("Getting age average warn");

        return avg;
    }


    public String updateStudentGrade(double gpa, int id) throws IncorrectGpaException {
        if (gpa > 4.2 || gpa < 0) {

            throw new IncorrectGpaException("GPA not valid");


        }

        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new IdNotValidException("GPA not valid");
        } else {
            Student student1 = student.get();
            student1.setGpa(gpa);
            studentRepository.save(student1);
        }


        return "success!";
    }

    public ArrayList<String> getHighStudent() {
        return studentRepository.getHighStudents();
    }

    public String removeStudent(int id) throws Exception {
        studentRepository.deleteById(id);

        return "success!";
    }

    public Optional<Student> getStudentInfo(int id) {
        log.info("Getting student info from the db");
        Student student = new Student();
        if (!studentRepository.findById(id).isEmpty()) {
            student = studentRepository.findById(id).get();
        }
        producer.sendJsonMessage(student);
        return studentRepository.findById(id);
    }

    public Response getAllEquipment() {
        List<Equipment> equipmentList = equipmentRepository.findAll();
        if (equipmentList.isEmpty()) {
            Response response = new Response(equipmentList, 404, "Equipments not found");
            return response;
        }
        Response response = new Response(equipmentList, 200, "Equipments retrieved successfully");
        return response;

    }

}
