package com.montytraining.student.Repository;

import com.montytraining.student.Entities.Response;
import com.montytraining.student.Entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class StudentRepositoryDao {
    public static final String HASH_KEY = "Student";
    @Autowired
    private RedisTemplate template;

    public Student save(Student student) {
        template.opsForValue().set(HASH_KEY, student);
        return student;

    }

    public ArrayList<Student> findAll() {
        return (ArrayList<Student>) template.opsForHash().values(HASH_KEY);
    }

    public Student findProductById(int id) {
        return (Student) template.opsForHash().get(HASH_KEY, id);
    }

    public String deleteStudent(int id) {
        template.opsForHash().delete(HASH_KEY, id);
        return "student deleted successfully";
    }

    public Response clearRedis(){
        if(template.delete(HASH_KEY)){
            Response response = new Response(null,1,"Redis cleared");
            return response;
        }else{
            Response response = new Response(null,0,"Error");
            return response;
        }

    }
}
