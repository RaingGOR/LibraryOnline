package ru.Raingor.LibraryOnline.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.Raingor.LibraryOnline.models.Book;
import ru.Raingor.LibraryOnline.models.Person;
import ru.Raingor.LibraryOnline.service.BookService;
import ru.Raingor.LibraryOnline.service.PersonService;


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


    //show
    @GetMapping
    public String showFullList(Model model) {         // get full peoples in DAO and pass to the display
        model.addAttribute("people", personService.findAll());
        return "people/list";
    }

    @GetMapping("/{person_id}")
    public String showPerson(@PathVariable("person_id") int id, Model model,
                             @ModelAttribute("book") Book book) {
        // get one person with id in DAO and pass to display
        model.addAttribute("person", personService.findOne(id));
        return "people/show";
    }


    //add
    @GetMapping("/new")     //create new Person --> show create page
    public String createNewPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        //check validators errors
        if (bindingResult.hasErrors()) {
            return "people/new";
        }


        personService.savePerson(person);

        return "redirect:/people";
    }

    //edit
    @GetMapping("/{person_id}/edit")
    public String edit(Model model, @PathVariable("person_id") int id) {
        model.addAttribute("person", personService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{person_id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("person_id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personService.updatePerson(id, person);

        return "redirect:/people";
    }

    //delete
    @DeleteMapping("/{person_id}")
    public String delete(@PathVariable("person_id") int id) {
        personService.deletePerson(id);
        return "redirect:/people";
    }
}
