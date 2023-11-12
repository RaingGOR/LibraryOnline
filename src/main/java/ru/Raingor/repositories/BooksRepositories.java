package ru.Raingor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Raingor.models.Book;
import ru.Raingor.models.Person;

import java.util.List;

@Repository
public interface BooksRepositories extends JpaRepository<Book, Integer> {
    public List<Book> findByOwner(Person owner);

    public List<Book> findByTitleStartingWith(String startWith);

}
