package com.sara.springdatajpa.dao;

import com.sara.springdatajpa.domain.Author;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @Override //this has a problem what to do if the result is more than one item
    public List<Author> findAuthorByName(String firstName, String lastName) {
        List<Map<String, Object>> rows =  jdbcTemplate.queryForList(
                "SELECT * FROM author WHERE first_name = ? and last_name = ?",
                firstName,
                lastName);

        List<Author> authors = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            Author author = new Author();
            author.setId(Long.parseLong(String.valueOf(row.get("id"))));
            author.setFirstName(String.valueOf(row.get("first_name")));
            author.setLastName(String.valueOf(row.get("last_name")));
            authors.add(author);
        }
        return authors;
    }

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
