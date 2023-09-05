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
    public String index(Model model) {
        // get full peoples in DAO and pass to the display
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        return "books/show";
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

