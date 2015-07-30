package com.nosoftskills.feedback.category;

import com.nosoftskills.feedback.model.Category;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
@RequestScoped
@Path("category")
public class CategoryService {

    private EntityManager entityManager;

    public CategoryService() {}

    @Inject
    public CategoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GET
    @Produces("application/json")
    public List<Category> getAllCategories() {
        return entityManager.createNamedQuery("findAllCategories", Category.class).getResultList();
    }

    @POST
    @Consumes("application/json")
    @Transactional
    public Category addCategory(Category category) {
        entityManager.persist(category);
        return category;
    }
}
