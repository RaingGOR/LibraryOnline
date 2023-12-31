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
@RequestMapping("/books")
public class BookController {
    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public BookController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }


    //show
    @GetMapping
    public String index(Model model, @ModelAttribute("person") Person person,
                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(value = "books_per_page", required = false, defaultValue = "0") Integer books_per_page
    ) {
        // get full peoples in book service (DAO <- old method) and pass to the display
        if (books_per_page == 0) {
            model.addAttribute("books", bookService.findAll());
        } else {
            model.addAttribute("books", bookService.findAll(page, books_per_page));
        }
        model.addAttribute("people", personService.findAll());

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("people", personService.findAll());
        model.addAttribute("person1", personService.findOne(id));

        return "books/show";
    }

    //add owner
    @PatchMapping("/{id}/add")
    public String setBook(@ModelAttribute("person") Person person, @PathVariable("id") int book_id) {
        bookService.setBook(person, book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/del")
    public String delPersonId(@PathVariable("id") int book_id) {
        bookService.dellPersId(book_id);
        return "redirect:/books";
    }

    //add new book
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

        bookService.saveBook(book);
        return "redirect:/books";
    }

    //update book
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book
            , BindingResult bindingResult
            , @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    // search
    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model,@RequestParam("query") String query){
        model.addAttribute("books", bookService.findByTitleStartWith(query));
        return "books/search";
    }


}

