package com.nosoftskills.feedback.category;

import com.nosoftskills.feedback.model.Category;
import com.nosoftskills.feedback.testutils.TestEntityManagerProducer;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(Arquillian.class)
@RunAsClient
public class CategoryResourceTest {

    @Deployment
    public static WebArchive createDeployment() {
       WebArchive webArchive = ShrinkWrap
                .create(WebArchive.class, "feedback.war")
                .addClasses(CategoryService.class, Category.class, TestEntityManagerProducer.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
       System.out.println(webArchive.toString(true));
       return webArchive;
    }

    @ArquillianResource
    private URL base;

    private WebResource webResource;

    @Before
    public void setup() throws MalformedURLException {
        Client client = Client.create();
        this.webResource = client.resource(URI.create(new URL(base, "rest/category").toExternalForm()));
    }

    @Test
    @InSequence(1)
    public void shouldAddCategory() throws Exception {
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, new Category("javaee"));
        assertThat(response.getStatusInfo().getFamily(), is(SUCCESSFUL));
        assertNotNull(response.getEntity(Category.class));
    }

    @Test
    @InSequence(2)
    public void shouldReturnCategories() throws Exception {
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(
                ClientResponse.class);
        assertThat(response.getStatusInfo().getFamily(), is(SUCCESSFUL));

        List<Category> categories = response.getEntity(new GenericType<List<Category>>(){});
        assertThat(categories.size(), is(1));
        assertThat(categories.get(0).getName(), is("javaee"));
    }
}
