package ru.Raingor.LibraryOnline.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Raingor.LibraryOnline.models.Person;
import ru.Raingor.LibraryOnline.repositories.PeopleRepositories;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PeopleRepositories peopleRepositories;

    @Autowired
    public PersonService(PeopleRepositories peopleRepositories) {
        this.peopleRepositories = peopleRepositories;
    }

    public List<Person> findAll() {
        return peopleRepositories.findAll();
    }


    public Person findOne(int id) {
        Optional<Person> person = peopleRepositories.findById(id);
        Hibernate.initialize(person.get().getBooks());
        return person.orElse(null);
    }

    @Transactional
    public void savePerson(Person person) {
        peopleRepositories.save(person);
    }

    @Transactional
    public void updatePerson(int id, Person person) {
        person.setId(id);
        peopleRepositories.save(person);
    }

    @Transactional
    public void deletePerson(int id) {
        peopleRepositories.deleteById(id);
    }

}
