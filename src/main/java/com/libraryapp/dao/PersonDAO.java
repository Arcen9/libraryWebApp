package com.libraryapp.dao;

import com.libraryapp.models.Book;
import com.libraryapp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void addPerson(Person person){
        jdbcTemplate.update("INSERT INTO Person(name, birthdate) VALUES(?, ?)", person.getName(), person.getBirthdate());
    }

    public void deletePerson(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET name=?, birthdate=? WHERE id=?",
                person.getName(), person.getBirthdate(), id);
    }

    public List<Book> getBooksByPerson(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE Person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> getPersonByName(String name){
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?",
                new Object[] {name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
