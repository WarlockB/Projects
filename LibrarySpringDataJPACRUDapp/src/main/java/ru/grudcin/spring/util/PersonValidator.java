package ru.grudcin.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.grudcin.spring.models.Person;
import ru.grudcin.spring.repositories.PeopleRepository;

@Component
public class PersonValidator implements Validator {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (peopleRepository.findPersonByFullName(person.getFullName()) != null) {
            errors.rejectValue("fullName", "", "Человек с таким именем уже существует");
        }
    }
}
