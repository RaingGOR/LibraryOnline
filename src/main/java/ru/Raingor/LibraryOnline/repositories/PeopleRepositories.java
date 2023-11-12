package ru.Raingor.LibraryOnline.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Raingor.LibraryOnline.models.Person;

@Repository
public interface PeopleRepositories extends JpaRepository<Person, Integer> {
}
