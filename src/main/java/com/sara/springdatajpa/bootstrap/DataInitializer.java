package com.sara.springdatajpa.bootstrap;

import com.sara.springdatajpa.domain.Book;
import com.sara.springdatajpa.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse");

        System.out.println("Id: " + bookDDD.getId());

        Book savedDDD = bookRepository.save(bookDDD);

        System.out.println("Id: " + savedDDD.getId());

        Book bookSIA = new Book("Spring In Action", "2323", "Unknown");
        Book savedSIA = bookRepository.save(bookDDD);

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book Title: " + book.getTitle());
            System.out.println("Book Isbn: " + book.getIsbn());
            System.out.println("Book Publisher: " + book.getPublisher());
        });
    }
}
