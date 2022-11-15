package br.dev.jcp.test.springbootwithcache.mapper;

import br.dev.jcp.test.springbootwithcache.model.Student;
import br.dev.jcp.test.springbootwithcache.model.StudentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDto(Student entity);
    Student toEntity(StudentDto dto);
}
