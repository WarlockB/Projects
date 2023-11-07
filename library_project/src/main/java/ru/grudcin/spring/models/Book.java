package ru.grudcin.spring.models;

import javax.validation.constraints.NotEmpty;

public class Book {
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Year of creation should not be empty")
    private int creationYear;
    @NotEmpty(message = "Author should not be empty")
    private String author;
    private Integer personId;
    private int id;

    public Book(String name, int creationYear, Integer personId, int id) {
        this.name = name;
        this.creationYear = creationYear;
        this.personId = personId;
        this.id = id;
    }

    public Book() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreationYear() {
        return creationYear;
    }

    public void setCreationYear(int creationYear) {
        this.creationYear = creationYear;
    }

    public Integer getPersonId() {
        return personId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
