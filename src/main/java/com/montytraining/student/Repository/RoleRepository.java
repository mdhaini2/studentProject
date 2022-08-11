package com.montytraining.student.Repository;

import com.montytraining.student.Entities.Role;
import com.montytraining.student.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, String> {
}
