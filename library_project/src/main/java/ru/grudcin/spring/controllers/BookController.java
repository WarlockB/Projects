package ru.grudcin.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.grudcin.spring.dao.BookDAO;
import ru.grudcin.spring.dao.PersonDAO;
import ru.grudcin.spring.models.Book;
import ru.grudcin.spring.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("book", bookDAO.showAllBooks());
        return "book/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.showBookByID(id));
        Person person = bookDAO.showPersonsByBookId(id);
        if (person != null)
            model.addAttribute("person", person);
        else
            model.addAttribute("people", personDAO.selectAllPeople());
        return "book/showById";
    }

    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.showBookByID(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";
        bookDAO.editBook(id, book);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/giveBackBook")
    public String makeFreeBook(@PathVariable("id") int id) {
        bookDAO.giveBackBook(id);
        return "redirect:/book/" + id;
    }

    @PatchMapping("/{id}/giveBookToPerson")
    public String giveBookToPerson(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookDAO.giveBookToPerson(book, id);
        return "redirect:/book/" + id;
    }

    @GetMapping("/new")
    public String createBookPage(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";
        bookDAO.createBook(book);
        return "redirect:book/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/book";
    }
}
