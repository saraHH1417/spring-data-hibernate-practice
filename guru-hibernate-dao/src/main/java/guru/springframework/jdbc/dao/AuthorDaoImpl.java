package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jt on 8/28/21.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory entityManagerFactory;

    public AuthorDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Author> findAll() {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Author> typedQuery = entityManager.createNamedQuery("author_find_all", Author.class);

            return typedQuery.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        EntityManager entityManager = getEntityManager();

        try {
            Query query = entityManager.createQuery("SELECT a from Author a " +
                    "where a.lastName like :last_name");
            query.setParameter("last_name", "%" + lastName + "%");
            List<Author> authors = query.getResultList();

            return authors;

        } finally {
            entityManager.close();
        }
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    public Author findAuthorByName(String firstName, String lastName) {
        EntityManager entityManager = getEntityManager();
//        TypedQuery<Author> query = getEntityManager().createQuery(
//                "SELECT a from Author a where" +
//                        " a.firstName = :first_name and " +
//                        "a.lastName= :last_name",
//                Author.class);
        TypedQuery<Author> query = entityManager.createNamedQuery(
                "find_by_name", Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        Author author = query.getSingleResult();
        entityManager.close();
        return author;
    }

    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);

            Root<Author> root = criteriaQuery.from(Author.class);

            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

            TypedQuery<Author> typedQuery = entityManager.createQuery(criteriaQuery);
            typedQuery.setParameter(firstNameParam, firstName);
            typedQuery.setParameter(lastNameParam, lastName);

            return typedQuery.getSingleResult();

        } finally {
            entityManager.close();
        }

    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.merge(author);
        entityManager.flush();
        entityManager.clear();
        return entityManager.find(Author.class, author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Author author = entityManager.find(Author.class, id);
        entityManager.remove(author);
        entityManager.flush();
        entityManager.getTransaction().commit();

    }

    @Override
    public Author findAuthorByNameNative(String firstName, String lastName) {
        EntityManager entityManager = getEntityManager();
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT * FROM author a " +
                    "WHERE a.first_name = ? and a.last_name = ?", Author.class);
            query.setParameter(1, firstName);
            query.setParameter(2, lastName);
            return (Author) query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
