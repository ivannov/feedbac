package com.nosoftskills.feedback.category;

import com.nosoftskills.feedback.misc.EntityManagerProducer;
import com.nosoftskills.feedback.model.Category;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.core.MediaType;

import java.io.File;

import static org.junit.Assert.assertNotNull;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(Arquillian.class)
public class CategoryResourceTest {

    @Inject
    private EntityManager entityManager;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "feedback.war")
                .addClasses(CategoryService.class, Category.class, EntityManagerProducer.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsManifestResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void setup() {
        entityManager.persist(new Category("javaee"));
    }

    @Test
    public void shouldReturnCategories() throws Exception {
        Client client = Client.create();
        WebResource resource = client.resource("http://localhost:8080/feedback/rest/category");
        String response = resource.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        assertNotNull(response);
    }
}
