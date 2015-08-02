package com.nosoftskills.feedback.testutils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Ivan St. Ivanov
 */
@ApplicationScoped
public class TestEntityManagerProducer {

    private EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("feedback-test-persistence-unit");

    @Produces
    @RequestScoped
    public EntityManager buildEntityManager() {
        return emf.createEntityManager();
    }

    public void dispose(@Disposes EntityManager entityManager) {
        entityManager.close();
    }

}
