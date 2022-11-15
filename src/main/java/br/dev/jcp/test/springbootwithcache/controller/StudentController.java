package br.dev.jcp.test.springbootwithcache.controller;

import br.dev.jcp.test.springbootwithcache.mapper.StudentMapper;
import br.dev.jcp.test.springbootwithcache.model.Student;
import br.dev.jcp.test.springbootwithcache.model.StudentDto;
import br.dev.jcp.test.springbootwithcache.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService service;
    private final StudentMapper mapper;

    @GetMapping
    public ResponseEntity<List<StudentDto>> listStudents() {
        return new ResponseEntity<>(service.listAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{enrollment}")
    public ResponseEntity<StudentDto> findStudentByEnrollment(@PathVariable("enrollment") Integer enrollment) {
        Student student = service.getStudentByEnrollment(enrollment);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.toDto(student), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto dto) {
        Student student = service.saveStudent(dto);
        StudentDto studentDto = student != null ? mapper.toDto(student) : null;
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto dto) {
        Student student = service.updateStudent(dto);
        return new ResponseEntity<>(mapper.toDto(student), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestBody StudentDto dto) {
        service.deleteStudent(dto);
        return new ResponseEntity<>("Student [" + dto.getEnrollment() + "] deleted.", HttpStatus.OK);
    }

    @DeleteMapping("/{enrollment}")
    public ResponseEntity<String> deleteStudentByEnrollment(@PathVariable("enrollment") Integer enrollment) {
        service.deleteStudentByEnrollment(enrollment);
        return new ResponseEntity<>("Student [" + enrollment + "] deleted.", HttpStatus.OK);
    }

    @GetMapping("/clear")
    public ResponseEntity<String> clearCache() {
        service.clearStudentsCache();
        return new ResponseEntity<>("Student Cache cleared!", HttpStatus.OK);
    }
}
