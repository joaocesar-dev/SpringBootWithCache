package br.dev.jcp.test.springbootwithcache.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private String edition;
    private String author;
}
