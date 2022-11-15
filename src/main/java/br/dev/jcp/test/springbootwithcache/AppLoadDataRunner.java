package br.dev.jcp.test.springbootwithcache;

import br.dev.jcp.test.springbootwithcache.model.Book;
import br.dev.jcp.test.springbootwithcache.model.Student;
import br.dev.jcp.test.springbootwithcache.repository.BookRepository;
import br.dev.jcp.test.springbootwithcache.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppLoadDataRunner implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Creating books....");
        createBooks();
        log.info("Books created.");
        log.info("Creating students...");
        createStudents();
        log.info("Students created.");
    }

    private void createBooks() {
        Book book = new Book();
        book.setIsbn("0671434004");
        book.setTitle("Contact");
        book.setAuthor("Carl Sagan");
        bookRepository.save(book);
        book = new Book();
        book.setIsbn("655511424X");
        book.setTitle("O Senhor dos Anéis: Volume Único");
        book.setAuthor("J.R.R Tolkien");
        bookRepository.save(book);
        book = new Book();
        book.setIsbn("8501115827");
        book.setTitle("O nome da rosa (Edição especial)");
        book.setAuthor("Umberto Eco");
        bookRepository.save(book);
    }

    private void createStudents() {
        Student student = new Student();
        student.setEnrollment(1011);
        student.setName("John Doe");
        student.setCourse("Math");
        student.setPeriod("FULL");
        studentRepository.save(student);
        student = new Student();
        student.setEnrollment(1012);
        student.setName("Mary Doe");
        student.setCourse("Arts");
        student.setPeriod("FULL");
        studentRepository.save(student);

    }
}
