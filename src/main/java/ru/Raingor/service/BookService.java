package ru.Raingor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Raingor.models.Book;
import ru.Raingor.models.Person;
import ru.Raingor.repositories.BooksRepositories;
import ru.Raingor.repositories.PeopleRepositories;

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

}
