package br.dev.jcp.test.springbootwithcache.repository;

import br.dev.jcp.test.springbootwithcache.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    long deleteBookByIsbn(String isbn);
}
