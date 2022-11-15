package br.dev.jcp.test.springbootwithcache.service;

import br.dev.jcp.test.springbootwithcache.mapper.BookMapper;
import br.dev.jcp.test.springbootwithcache.model.Book;
import br.dev.jcp.test.springbootwithcache.model.BookDto;
import br.dev.jcp.test.springbootwithcache.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final BookMapper mapper;

    @Cacheable("books")
    @Transactional(readOnly = true)
    public Book getBookByIsbn(String isbn) {
        log.info("Get Book by ISBN {}", isbn);
        return repository.findByIsbn(isbn).orElse(null);
    }

    @CachePut(value = "books", key = "#result.isbn")
    @Transactional
    public Book saveBook(BookDto dto) {
        return repository.save(mapper.toEntity(dto));
    }

    @CachePut(value = "books", key = "#result.isbn")
    @Transactional
    public Book updateBook(BookDto dto) {
        return repository.save(mapper.toEntity(dto));
    }

    @CacheEvict(value = "books", key = "#dto.isbn")
    @Transactional
    public void deleteBook(BookDto dto) {
        repository.delete(mapper.toEntity(dto));
    }

    @CacheEvict(value = "books")
    @Transactional
    public void deleteBookByIsbn(String isbn) {
        long deleted = repository.deleteBookByIsbn(isbn);
        log.info("Records deleted {}", deleted);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void clearBookCache() {
        log.info("Clearing Book cache...");
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(mapper::toDto).toList();
    }
}
