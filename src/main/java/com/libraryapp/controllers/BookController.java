package com.libraryapp.controllers;

import com.libraryapp.dao.BookDAO;
import com.libraryapp.dao.PersonDAO;
import com.libraryapp.models.Book;
import com.libraryapp.models.Person;
import com.libraryapp.services.BooksService;
import com.libraryapp.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final BooksService booksService;
    private final PersonDAO personDAO;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookDAO bookDAO, BooksService booksService, PersonDAO personDAO, PeopleService peopleService) {
        this.bookDAO = bookDAO;
        this.booksService = booksService;
        this.personDAO = personDAO;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "books_per_page", required = false) Integer bpr
            , @RequestParam(value = "sort_by_year", required = false) boolean sort){
        if(page == null || bpr == null)
            model.addAttribute("books", booksService.findAll(sort));
        else
            model.addAttribute("books", booksService.findWithPagination(sort, page, bpr));
        return "/books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "/books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("books") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/books/new";

        booksService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", booksService.findOne(id));
        return "/books/edit";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", booksService.findOne(id));
        Optional<Person> bookOwner = booksService.getOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", peopleService.findAll());
        return "/books/show";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        booksService.assign(person.getId(), id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage(){
        return "/books/search";
    }

    @PostMapping("/search")
    public String search(@RequestParam("title") String title, Model model){
        model.addAttribute("books", booksService.findByTitle(title));
        return "/books/search";
    }
}