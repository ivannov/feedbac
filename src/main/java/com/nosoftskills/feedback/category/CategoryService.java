package com.nosoftskills.feedback.category;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.nosoftskills.feedback.model.Category;
import com.nosoftskills.feedback.model.Employee;
import com.nosoftskills.feedback.model.EndorsementEvent;
import com.nosoftskills.feedback.user.UserContext;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
@RequestScoped
@Path("category")
public class CategoryService {

    private static final EndorsementCategoryConverter categoryExtractor = new EndorsementCategoryConverter();

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

    // TODO move this to 
    @Inject
    private UserContext userContext;

    @GET
    @Path("/currentUser/assigned")
    @Produces("application/json")
    public List<Category> getAssignedCategoriesForCurrentUser() {
        return findCategoriesForUser(userContext.getLoggedEmployee());
    }

    List<Category> findCategoriesForUser(Employee currentUser) {
        TypedQuery<EndorsementEvent> endorsementsQuery = entityManager.createNamedQuery("findAllEndorsementsForEmployee", EndorsementEvent.class);
        endorsementsQuery.setParameter("endorsedEmployee", currentUser);
        List<EndorsementEvent> endorsements = endorsementsQuery.getResultList();
        return Lists.transform(endorsements, categoryExtractor);
    }

    public List<Category> getNotAssignedCategoriesForCurrentUser() {
        return null;
    }

    private static class EndorsementCategoryConverter implements Function<EndorsementEvent, Category> {
        @Override
        public Category apply(EndorsementEvent endorsementEvent) {
            return endorsementEvent.getCategory();
        }
    }
}
