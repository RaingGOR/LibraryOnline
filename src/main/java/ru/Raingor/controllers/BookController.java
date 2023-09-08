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
@RequestMapping("/books")
public class BookController {
    private final BookDao bookDao;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDao bookDao, PersonDAO personDAO) {
        this.bookDao = bookDao;
        this.personDAO = personDAO;
    }

    //checked * work *
    @GetMapping()
    public String index(Model model, @ModelAttribute("person") Person person) {
        // get full peoples in DAO and pass to the display
        model.addAttribute("books", bookDao.index());
        model.addAttribute("people", personDAO.fullShow());

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,@ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDao.show(id));
        model.addAttribute("people", personDAO.fullShow());
        model.addAttribute("isEmpty", bookDao.havePerson(id));
        model.addAttribute("person1", personDAO.showPerson(id));

        return "books/show";
    }

    @PatchMapping("/{id}/add")
    public String setBook(@ModelAttribute("person") Person person, @PathVariable("id") int book_id) {
        bookDao.setBook(person.getPerson_id(),book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/del")
    public String delPersonId( @PathVariable("id") int book_id){
        bookDao.dellPersId(book_id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDao.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book
            , BindingResult bindingResult
            , @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookDao.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

}

