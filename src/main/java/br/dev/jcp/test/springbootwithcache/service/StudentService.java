package br.dev.jcp.test.springbootwithcache.service;

import br.dev.jcp.test.springbootwithcache.mapper.StudentMapper;
import br.dev.jcp.test.springbootwithcache.model.Student;
import br.dev.jcp.test.springbootwithcache.model.StudentDto;
import br.dev.jcp.test.springbootwithcache.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private final CacheManager cacheManager;
    private final StudentMapper mapper;

    @Transactional(readOnly = true)
    public Student getStudentByEnrollment(Integer enrollment) {
        Student student = getCache().get(enrollment, Student.class);
        if (student != null) {
            return student;
        }
        student = repository.findByEnrollment(enrollment).orElse(null);
        getCache().put(enrollment, student);
        return student;
    }

    @Transactional
    public Student saveStudent(StudentDto dto) {
        Student student = repository.save(mapper.toEntity(dto));
        getCache().put(dto.getEnrollment(), student);
        return student;
    }

    @Transactional
    public Student updateStudent(StudentDto dto) {
        return saveStudent(dto);
    }

    @Transactional
    public void deleteStudent(StudentDto dto) {
        repository.delete(mapper.toEntity(dto));
        getCache().evict(dto.getEnrollment());
    }

    @Transactional
    public void deleteStudentByEnrollment(Integer enrollment) {
        long deleted = repository.deleteStudentByEnrollment(enrollment);
        getCache().evict(enrollment);
        log.info("Records deleted {}", deleted);
    }

    public void clearStudentsCache() {
        getCache().clear();
    }

    @Transactional(readOnly = true)
    public List<StudentDto> listAllStudents() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(mapper::toDto).toList();
    }

    private Cache getCache() {
        return cacheManager.getCache("students");
    }
}
