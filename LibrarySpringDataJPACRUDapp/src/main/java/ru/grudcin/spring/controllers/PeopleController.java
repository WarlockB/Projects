package ru.grudcin.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.grudcin.spring.models.Person;
import ru.grudcin.spring.services.BookService;
import ru.grudcin.spring.services.PersonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public PeopleController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/showAllPeople";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findPersonById(id));
        model.addAttribute("books", bookService.findBooksByPersonId(id));
        return "people/showById";
    }

    @GetMapping("/new")
    public String newPersonPage(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String saveNewPerson(@ModelAttribute("person") @Valid Person person,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personService.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.findPersonById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personService.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        personService.deletePerson(id);
        return "redirect:/people";
    }
}
