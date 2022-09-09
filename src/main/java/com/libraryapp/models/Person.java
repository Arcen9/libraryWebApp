package com.libraryapp.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Person {
    private int id;
    @NotEmpty(message = "Title should be fulfilled")
    private String name;
    @NotNull(message = "Birthdate should be fulfilled")
    @Min(value = 1942, message = "Birthdate should be greater than 1942")
    @Max(value = 2022, message = "You should be older than 0")
    private int birthdate;

    public Person() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(int birthdate) {
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
