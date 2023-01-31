package com.sara.springdatajpa.dao;

import com.sara.springdatajpa.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author getById(Long id);

    List<Author> findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

}
