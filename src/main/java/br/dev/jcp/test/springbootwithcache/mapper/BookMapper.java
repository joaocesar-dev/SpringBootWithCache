package br.dev.jcp.test.springbootwithcache.mapper;

import br.dev.jcp.test.springbootwithcache.model.Book;
import br.dev.jcp.test.springbootwithcache.model.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book entity);
    Book toEntity(BookDto dto);
}
