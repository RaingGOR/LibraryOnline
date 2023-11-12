package ru.Raingor.LibraryOnline.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Raingor.LibraryOnline.models.Book;
import ru.Raingor.LibraryOnline.models.Person;
import ru.Raingor.LibraryOnline.repositories.BooksRepositories;
import ru.Raingor.LibraryOnline.repositories.PeopleRepositories;


import java.util.ArrayList;
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

    public List<Book> findAll(int page, int books_per_page) {
        return booksRepositories.findAll(PageRequest.of(page, books_per_page)).getContent();
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
        book.setOwner(peopleRepositories.findById(id).orElse(null));
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

    @Transactional
    public void dellPersId(int id) {
        Book book = booksRepositories.findById(id).orElseThrow(() -> new RuntimeException("Not found book, how???"));
        book.getOwner().getBooks().remove(book);
        book.setOwner(null);
        booksRepositories.save(book);
    }

    public List<Book> findByTitleStartWith(String startWith) {
        return booksRepositories.findByTitleStartingWith(startWith);
    }
}
