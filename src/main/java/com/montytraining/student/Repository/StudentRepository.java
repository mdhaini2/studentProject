package com.montytraining.student.Repository;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.montytraining.student.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {



    @Query(value = "update student set gpa = ?1 where id = ?2 ", nativeQuery = true)
    public void updateStudentGrade(double gpa, int id);

    @Query(value = "Select name from Student where gpa >=3")
    public ArrayList<String> getHighStudents();

    public ArrayList<Student> findByNameStartingWith(String prefix);

    public ArrayList<Student> findByLastNameStartingWith(String prefix);

    @Query(value = "Select name from student where gpa= ?1 and age= ?2 ", nativeQuery = true)
    public ArrayList<String> getStudentNameWithAgeGPA(double gpa, int age);


}