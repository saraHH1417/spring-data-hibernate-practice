package org.example;

import org.example.dto.Employee;;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Hello world!
 *
 */
public class App 
{
    @PersistenceContext
    EntityManager entityManager;
    public static void main( String[] args )
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("H2DB");

        EntityManager em = emf.createEntityManager();

        Employee employee = new Employee();
        employee.setEmail("s@gm.com");
        employee.setFirstName("sara");
        employee.setLastName("ss");

        assert employee.getEmplyeeId() == null;

        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        assert employee.getEmplyeeId() != null;
    }
}
