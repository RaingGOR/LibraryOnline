package ru.Raingor.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.Raingor.dao.BookDao;
import ru.Raingor.dao.PersonDAO;
import ru.Raingor.models.Book;
import ru.Raingor.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDao bookDao;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDao bookDao) {
        this.personDAO = personDAO;
        this.bookDao = bookDao;
    }

    //show
    @GetMapping()
    public String showFullList(Model model) {         // get full peoples in DAO and pass to the display
        model.addAttribute("people", personDAO.fullShow());
        return "people/list";
    }

    @GetMapping("/{person_id}")
    public String showPerson(@PathVariable("person_id") int id, Model model,
                             @ModelAttribute("book") Book book) {
        // get one person with id in DAO and pass to display
        model.addAttribute("person", personDAO.showPerson(id));
        model.addAttribute("personBooks", bookDao.showBooks(id));
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

        personDAO.save(person);

        return "redirect:/people";
    }

    //edit
    @GetMapping("/{person_id}/edit")
    public String edit(Model model, @PathVariable("person_id") int id) {
        model.addAttribute("person", personDAO.showPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{person_id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("person_id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDAO.edit(id, person);

        return "redirect:/people";
    }

    //delete
    @DeleteMapping("/{person_id}")
    public String delete(@PathVariable("person_id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
