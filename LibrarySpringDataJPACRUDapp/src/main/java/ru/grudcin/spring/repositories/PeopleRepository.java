package ru.grudcin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.grudcin.spring.models.Person;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Person findPersonByFullName(String name);
}
