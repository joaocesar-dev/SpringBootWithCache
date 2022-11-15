package br.dev.jcp.test.springbootwithcache.repository;

import br.dev.jcp.test.springbootwithcache.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findByEnrollment(Integer enrollment);
    long deleteStudentByEnrollment(Integer enrollment);

}
