package com.nosoftskills.feedback;

import com.nosoftskills.feedback.model.Category;
import org.apache.deltaspike.core.api.lifecycle.Initialized;
import org.apache.deltaspike.jpa.api.entitymanager.PersistenceUnitName;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
public class TestDataInserter {

    @Inject
    @PersistenceUnitName("feedback-persistence-unit")
    private EntityManagerFactory entityManagerFactory;

    public void init(@Observes @Initialized ServletContext context) throws IOException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(new Category("javaee"));
        entityManager.getTransaction().commit();
    }

}
