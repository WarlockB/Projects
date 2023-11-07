package ru.grudcin.spring.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Column(name = "creation_year")
    @Min(value = 1500, message = "Year of creation should be greater than 1500")
    private int creationYear;
    @Column(name = "author")
    @NotEmpty(message = "Author should not be empty")
    private String author;
    @Column(name = "person_id")
    @JoinTable(name = "person",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Integer personId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean isOverdue;

    public Book(String name, int creationYear, Integer personId, int id, Date takenAt, boolean isOverdue) {
        this.name = name;
        this.creationYear = creationYear;
        this.personId = personId;
        this.id = id;
        this.takenAt = takenAt;
        this.isOverdue = isOverdue;
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

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isOverdue() {
        long milliseconds = 0;
        if (takenAt != null)
            milliseconds = new Date().getTime() - takenAt.getTime();
        return milliseconds >= 864000000;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }
}
