package ru.grudcin.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.grudcin.spring.models.Book;
import ru.grudcin.spring.models.Person;
import ru.grudcin.spring.services.BookService;
import ru.grudcin.spring.services.PersonService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("book", bookService.findAll());
        return "book/showAll";
    }

    @GetMapping(params = {"sort"})
    public String showAllSorted(Model model, @RequestParam("sort") boolean sort) {
        model.addAttribute("book", bookService.findAllSorted());
        return "book/showAll";
    }

    @GetMapping(params = {"list", "objects", "sort"})
    public String showAllSortedAndPaginated(Model model, @RequestParam("list") int list,
                                            @RequestParam("objects") int objects,
                                            @RequestParam("sort") boolean sort) {
        model.addAttribute("book", bookService.findAllSortedAndPaginated(list, objects));
        return "book/showAll";
    }

    @GetMapping(params = {"list", "objects"})
    public String showAllPaginated(Model model, @RequestParam("list") int list,
                                   @RequestParam("objects") int objects) {
        model.addAttribute("book", bookService.findAllPaginated(list, objects));
        return "book/showAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        Person person = bookService.findPersonByBookId(id);
        if (person != null)
            model.addAttribute("person", person);
        else
            model.addAttribute("people", personService.findAll());
        return "book/showById";
    }

    @GetMapping("/{id}/edit")
    public String editBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "book/edit";
    }

    @GetMapping("/search")
    public String searchBookPage(@RequestParam(value = "name", required = false) String name, Model model) {
        if (name != null && !name.equals("")) {
            List<Book> bookList = bookService.findBookByNameContainingIgnoreCase(name);
            Map<Book, Person> bookPersonMap = new HashMap<>();
            if (!bookList.isEmpty()) {
                for (Book book : bookList) {
                    bookPersonMap.put(book, bookService.findPersonByBookId(book.getId()));
                }
                model.addAttribute("bookPersonMap", bookPersonMap);
                model.addAttribute("search", name);
            }
        }
        return "book/search";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,
                             @PathVariable("id") int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/edit";
        bookService.updateBook(book);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/realiseBook")
    public String releaseBook(@PathVariable("id") int id) {
        bookService.releaseBook(id);
        return "redirect:/book/" + id;
    }

    @PatchMapping("/{id}/giveBookToPerson")
    public String giveBookToPerson(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookService.giveBookToPerson(book, id);
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
        bookService.saveBook(book);
        return "redirect:book/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/book";
    }
}
