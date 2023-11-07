package ru.grudcin.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.grudcin.spring.dao.PersonDAO;
import ru.grudcin.spring.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("people", personDAO.selectAllPeople());
        return "people/showAllPeople";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.showPersonById(id));
        model.addAttribute("books", personDAO.showBooksByPersonId(id));
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

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.showPersonById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
