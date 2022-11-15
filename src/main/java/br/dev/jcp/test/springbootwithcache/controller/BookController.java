package br.dev.jcp.test.springbootwithcache.controller;

import br.dev.jcp.test.springbootwithcache.mapper.BookMapper;
import br.dev.jcp.test.springbootwithcache.model.Book;
import br.dev.jcp.test.springbootwithcache.model.BookDto;
import br.dev.jcp.test.springbootwithcache.service.BookService;
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
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<List<BookDto>> listBook() {
        List<BookDto> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable(value = "isbn") String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookMapper.toDto(book), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto dto) {
        Book book = bookService.saveBook(dto);
        BookDto bookDto = book != null ? bookMapper.toDto(book) : null;
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto dto) {
        Book book = bookService.updateBook(dto);
        return new ResponseEntity<>(bookMapper.toDto(book), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBook(@RequestBody BookDto dto) {
        bookService.deleteBook(dto);
        return new ResponseEntity<>("Book [" + dto.getTitle() + " / " + dto.getIsbn() + "] deleted!", HttpStatus.OK);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteBookByIsbn(@PathVariable("isbn") String isbn) {
        bookService.deleteBookByIsbn(isbn);
        return new ResponseEntity<>("Book [" + isbn + "] deleted!", HttpStatus.OK);
    }

    @GetMapping("/clear")
    public ResponseEntity<String> clearCache() {
        bookService.clearBookCache();
        return new ResponseEntity<>("Book Cache cleared!", HttpStatus.OK);
    }
}
