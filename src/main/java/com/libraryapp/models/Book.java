package com.libraryapp.models;

import javax.validation.constraints.NotNull;

public class Book {
    private int id;
    @NotNull(message = "Title should be fulfilled")
    private String title;
    @NotNull(message = "Author should be fulfilled")
    private String author;
    @NotNull(message = "Year should be fulfilled")
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
