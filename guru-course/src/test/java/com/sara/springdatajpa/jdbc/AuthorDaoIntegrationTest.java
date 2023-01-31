package com.sara.springdatajpa.jdbc;

import com.sara.springdatajpa.dao.AuthorDao;
import com.sara.springdatajpa.dao.AuthorDaoImplJDBCTemplate;
import com.sara.springdatajpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.sara.springdatajpa")
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthor() {
        Author savedAuthor = new Author();
        savedAuthor.setFirstName("Sara");
        savedAuthor.setLastName("Blue");
        authorDao.saveNewAuthor(savedAuthor);

        Author author = authorDao.getById(1L);
        assert author != null;
    }

    @Test
    void testGetAuthorByName() {
        Author savedAuthor = new Author();
        savedAuthor.setFirstName("Sara");
        savedAuthor.setLastName("Blue");
        authorDao.saveNewAuthor(savedAuthor);

        List<Author> authors = authorDao.findAuthorByName("Sara", "Blue");
        assert authors.size() != 0;
    }

    @Test
    void testSavedAuthor() {
        Author author = new Author();
        author.setFirstName("Sara");
        author.setLastName("Blue");
        Author savedAuthor = authorDao.saveNewAuthor(author);

        assert savedAuthor != null;
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("Sara");
        author.setLastName("Blue");

        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("Orange");
        Author updated = authorDao.updateAuthor(saved);

        assert updated.getLastName().equals("Orange");
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("Sara");
        author.setLastName("Blue");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            authorDao.getById(saved.getId());
        });

    }

}
