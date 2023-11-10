package ru.Raingor.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Raingor.models.Book;
import ru.Raingor.models.Person;
import ru.Raingor.repositories.BooksRepositories;
import ru.Raingor.repositories.PeopleRepositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepositories booksRepositories;
    private final PeopleRepositories peopleRepositories;

    @Autowired
    public BookService(BooksRepositories booksRepositories, PeopleRepositories peopleRepositories) {
        this.booksRepositories = booksRepositories;
        this.peopleRepositories = peopleRepositories;
    }

    public List<Book> findAll() {
        return booksRepositories.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepositories.findById(id);
        Hibernate.initialize(book.get().getOwner());
        return book.orElse(null);
    }

    @Transactional
    public void saveBook(Book book) {
        booksRepositories.save(book);
    }

    @Transactional
    public void updateBook(int id, Book book) {
        book.setId(id);
        booksRepositories.save(book);
    }

    @Transactional
    public void deleteBook(int id) {
        booksRepositories.deleteById(id);
    }

    @Transactional
    public void setBook(Person person, int idBook) {
        Book book = booksRepositories.findById(idBook).orElseThrow(() -> new RuntimeException("music not found"));
        if (person.getBooks() == null) {
            List<Book> books = new ArrayList<>(List.of(book));
        } else {
            person.getBooks().add(book);
        }
        book.setOwner(person);
        booksRepositories.save(book);
    }

    //error method
    @Transactional
    public void dellPersId(int id) {
        Book book = booksRepositories.findById(id).orElseThrow(() -> new RuntimeException("Not found book, how???"));
        book.getOwner().getBooks().remove(book);
        book.setOwner(null);
        booksRepositories.save(book);
    }
}
