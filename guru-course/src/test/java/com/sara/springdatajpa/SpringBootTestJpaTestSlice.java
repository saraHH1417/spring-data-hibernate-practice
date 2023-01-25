package com.sara.springdatajpa;

import com.sara.springdatajpa.domain.Book;
import com.sara.springdatajpa.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = "com.sara.springdatajpa.bootstrap")
public class SpringBootTestJpaTestSlice {

    @Autowired
    BookRepository bookRepository;

    @Commit
    @Order(1)
    @Test
    void TestJpaTestSlice() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);

        bookRepository.save(new Book("My Book", "1235555", "Self"));
        long countAfter = bookRepository.count();

        assertThat(countBefore).isLessThan(countAfter);

    }

    @Order(2)
    @Test
    void testJpaTestSpliceTransaction() {
        long count = bookRepository.count();
        assertThat(count).isEqualTo(3);
    }
}
