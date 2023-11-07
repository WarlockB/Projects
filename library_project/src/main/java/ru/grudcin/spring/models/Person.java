package ru.grudcin.spring.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int personId;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String fullName;

    @Min(value = 0, message = "Birth year should be greater than 1920")
    private int birthYear;

    public Person() {

    }

    public Person(int personId, String fullName, int birthYear) {
        this.personId = personId;
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

}
