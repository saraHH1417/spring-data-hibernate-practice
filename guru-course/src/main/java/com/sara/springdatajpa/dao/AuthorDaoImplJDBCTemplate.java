package com.sara.springdatajpa.dao;

import com.sara.springdatajpa.domain.Author;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Primary
public class AuthorDaoImplJDBCTemplate implements AuthorDao{

    private JdbcTemplate jdbcTemplate;

    public AuthorDaoImplJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM author where id = ?",
                getRowMapper(),
                id
        );
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

//    @Override this has a problem what to do if the result is more than one item
//    public Author findAuthorByName(String firstName, String lastName) {
//        return jdbcTemplate.queryForObject(
//                "SELECT * FROM author WHERE first_name = ? and last_name = ?",
//                getRowMapper(),
//                firstName,
//                lastName);
//    }

    @Override
    public Author saveNewAuthor(Author author) {
        jdbcTemplate.update("INSERT INTO author(first_name, last_name) values (?,?)",
                            author.getFirstName(), author.getLastName());
        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return this.getById(createdId);
    }

    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update(
                "UPDATE author SET first_name = ?, last_name = ? where id = ?",
                    author.getFirstName(),
                    author.getLastName(),
                    author.getId());
        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update("DELETE from author where id = ?", id);
    }


    public RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }
}
