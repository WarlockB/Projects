package ru.grudcin.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.grudcin.spring.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByPersonId(int id);

    List<Book> findBookByNameContainingIgnoreCase(String str);
}
