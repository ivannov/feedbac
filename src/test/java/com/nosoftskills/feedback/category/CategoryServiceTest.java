package com.nosoftskills.feedback.category;

import com.nosoftskills.feedback.model.Category;
import com.nosoftskills.feedback.model.Employee;
import com.nosoftskills.feedback.model.EndorsementEvent;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Ivan St. Ivanov
 */
public class CategoryServiceTest {

    private static EntityManager entityManager;
    private static CategoryService categoryService;
    private Employee employeeIvan;
    private Employee employeePetio;

    @BeforeClass
    public static void setupTestObjects() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("feedback-test-persistence-unit");
        entityManager = emf.createEntityManager();
        categoryService = new CategoryService(entityManager);
    }

    @Before
    public void setUp() throws Exception {
        entityManager.getTransaction().begin();
        insertTestData();
        entityManager.flush();
    }

    private void insertTestData() {
        Category categoryJavaEE = new Category("javaee");
        Category categoryJQuery = new Category("jQuery");
        Category categoryCdi = new Category("cdi");

        employeeIvan = new Employee("ivan", "ivan", "Ivan", "Ivanov");
        employeePetio = new Employee("petio", "petio", "Petio", "Petev");

        EndorsementEvent endorsementEvent1 = new EndorsementEvent(employeeIvan, categoryJavaEE);
        EndorsementEvent endorsementEvent2 = new EndorsementEvent(employeeIvan, categoryCdi);
        EndorsementEvent endorsementEvent3 = new EndorsementEvent(employeePetio, categoryJavaEE);
        EndorsementEvent endorsementEvent4 = new EndorsementEvent(employeePetio, categoryJQuery);

        entityManager.persist(categoryJavaEE);
        entityManager.persist(categoryJQuery);
        entityManager.persist(categoryCdi);
        entityManager.persist(employeeIvan);
        entityManager.persist(employeePetio);
        entityManager.persist(endorsementEvent1);
        entityManager.persist(endorsementEvent2);
        entityManager.persist(endorsementEvent3);
        entityManager.persist(endorsementEvent4);
    }

    @Test
    public void shouldListAllPreInsertedTest() throws Exception {
        List<Category> categories = categoryService.getAllCategories();
        assertThat(categories.size(), is(3));
    }

    @Test
    public void shouldAddCategory() throws Exception {
        Category persistedCategory = categoryService.addCategory(new Category("JUnit"));
        assertNotNull(persistedCategory.getId());
        assertNotNull(entityManager.find(Category.class, persistedCategory.getId()));
        assertThat(entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList().size(), is(4));
    }

    @Test
    public void shouldFindAllCategoriesForUser() throws Exception {
        List<Category> categoriesForIvan = categoryService.findCategoriesForUser(employeeIvan);
        assertThat(categoriesForIvan.size(), is(2));
    }

    @After
    public void tearDown() throws Exception {
        entityManager.getTransaction().rollback();
    }

    @AfterClass
    public static void closeEntityManager() throws Exception {
        entityManager.close();
    }

}
