package com.montytraining.student.Repository;

import com.montytraining.student.Entities.Student;
import com.montytraining.student.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    public User findByUsername(String username);
}
