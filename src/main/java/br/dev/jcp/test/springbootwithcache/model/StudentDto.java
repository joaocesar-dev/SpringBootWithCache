package br.dev.jcp.test.springbootwithcache.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {
    private Long id;
    private Integer enrollment;
    private String name;
    private String course;
    private String period;
}
