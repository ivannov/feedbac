package com.nosoftskills.feedback.category;

import com.nosoftskills.feedback.model.Category;
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
        Category categoryCdi = new Category("misc");

        entityManager.persist(categoryJavaEE);
        entityManager.persist(categoryJQuery);
        entityManager.persist(categoryCdi);
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

    @After
    public void tearDown() throws Exception {
        entityManager.getTransaction().rollback();
    }

    @AfterClass
    public static void closeEntityManager() throws Exception {
        entityManager.close();
    }

}
