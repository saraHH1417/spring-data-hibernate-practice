package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDaoImpl implements BookDao{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Book findByISBN(String isbn) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Book> query = entityManager.createQuery(
                    "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
            query.setParameter("isbn", isbn);
            Book book = query.getSingleResult();
            return book;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Book findById(Long id) {
        EntityManager entityManager = getEntityManager();
        Book book = entityManager.find(Book.class, id);
        entityManager.close();
        return book;
    }

    @Override
    public Book findBookByTitle(String title) {
        EntityManager entityManager = getEntityManager();
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b where title like :title", Book.class);
        query.setParameter("title", "%" + title + "%");
        Book book = query.getSingleResult();
        return book;
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.flush();
        entityManager.clear();
        Book savedBook = findById(book.getId());
        entityManager.getTransaction().commit();
        entityManager.clear();
        return savedBook;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Book book = findById(id);
        entityManager.remove(book);
        entityManager.getTransaction().commit();

    }

    private EntityManager getEntityManager() {
        return  entityManagerFactory.createEntityManager();
    }
}
