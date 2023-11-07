package ru.grudcin.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.grudcin.spring.models.Book;
import ru.grudcin.spring.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> selectAllPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showPersonById(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> showPersonByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?", new Object[]{fullName}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public List<Book> showBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, birth_year) VALUES(?, ?)", person.getFullName(), person.getBirthYear());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name=?, birth_year=? WHERE person_id=?", updatedPerson.getFullName(),
                updatedPerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }
}
