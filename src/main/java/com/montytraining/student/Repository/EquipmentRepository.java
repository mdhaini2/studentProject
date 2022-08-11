package com.montytraining.student.Repository;

import com.montytraining.student.Entities.Equipment;
import com.montytraining.student.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

}
