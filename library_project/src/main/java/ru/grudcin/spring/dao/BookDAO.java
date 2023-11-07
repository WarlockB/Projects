package ru.grudcin.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.grudcin.spring.models.Book;
import ru.grudcin.spring.models.Person;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showAllBooks() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
        return books;
    }

    public Book showBookByID(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void editBook(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET person_id=?, name=?, author=?, creation_year=?  WHERE id = ?", updatedBook.getPersonId(),
                updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getCreationYear(), id);
    }

    public void giveBackBook(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE id = ?", id);
    }

    public void giveBookToPerson(Book updatedBook, int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", updatedBook.getPersonId(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public void createBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, creation_year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(), book.getCreationYear());
    }

    public Person showPersonsByBookId(int id) {
        return jdbcTemplate.query("SELECT p.* FROM Book b JOIN Person p ON p.person_id = b.person_id  WHERE b.id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }
}
