package ru.Raingor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Raingor.models.Person;

@Repository
public interface PeopleRepositories extends JpaRepository<Person, Integer> {
}
