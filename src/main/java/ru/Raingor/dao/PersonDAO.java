package ru.Raingor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.Raingor.models.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //show people
    public List<Person> fullShow() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    //show one person
    public Person showPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    //add
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(fullname, age) VALUES(?,?)",
                person.getFullName(), person.getAge());
    }

    //edit
    public void edit(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET fullname=?, age=? WHERE id=?",
                person.getFullName(), person.getAge(), person.getId());
    }

    //delete
    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
