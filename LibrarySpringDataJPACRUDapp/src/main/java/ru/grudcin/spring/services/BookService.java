package ru.grudcin.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grudcin.spring.models.Book;
import ru.grudcin.spring.models.Person;
import ru.grudcin.spring.repositories.BooksRepository;
import ru.grudcin.spring.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }


    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAllSorted() {
        return booksRepository.findAll(Sort.by("creationYear"));
    }

    public List<Book> findAllPaginated(int lists, int objects) {
        return booksRepository.findAll(PageRequest.of(lists, objects)).getContent();
    }

    public List<Book> findAllSortedAndPaginated(int lists, int objects) {
        return booksRepository.findAll(PageRequest.of(lists, objects, Sort.by("creationYear"))).getContent();
    }

    public Book findBookById(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    public List<Book> findBookByNameContainingIgnoreCase(String string) {
        List<Book> book = booksRepository.findBookByNameContainingIgnoreCase(string);
        return book;
    }

    @Transactional
    public void saveBook(Book Book) {
        booksRepository.save(Book);
    }

    @Transactional
    public void updateBook(Book updatedPerson) {
        Book book = findBookById(updatedPerson.getId());
        updatedPerson.setPersonId(book.getPersonId());
        booksRepository.save(updatedPerson);
    }

    @Transactional
    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }

    public List<Book> findBooksByPersonId(int id) {
        return booksRepository.findBooksByPersonId(id);
    }

    @Transactional
    public void releaseBook(int id) {
        Book book = findBookById(id);
        book.setPersonId(null);
        book.setTakenAt(null);
        booksRepository.save(book);
    }

    @Transactional
    public void giveBookToPerson(Book book, int id) {
        Book bookToUpdate = findBookById(id);
        bookToUpdate.setPersonId(book.getPersonId());
        bookToUpdate.setTakenAt(new Date());
        booksRepository.save(bookToUpdate);
    }

    public Person findPersonByBookId(int id) {
        Book book = findBookById(id);
        if (book.getPersonId() != null)
            return peopleRepository.findById(book.getPersonId()).orElse(null);
        return null;
    }
}
