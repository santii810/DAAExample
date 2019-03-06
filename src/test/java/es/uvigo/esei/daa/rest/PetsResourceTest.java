package es.uvigo.esei.daa.rest;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import es.uvigo.esei.daa.DAAExampleApplication;
import es.uvigo.esei.daa.entities.Pet;
import es.uvigo.esei.daa.listeners.ApplicationContextBinding;
import es.uvigo.esei.daa.listeners.ApplicationContextJndiBindingTestExecutionListener;
import es.uvigo.esei.daa.listeners.DbManagement;
import es.uvigo.esei.daa.listeners.DbManagementTestExecutionListener;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static es.uvigo.esei.daa.dataset.PetsDataset.*;
import static es.uvigo.esei.daa.dataset.UsersDataset.adminLogin;
import static es.uvigo.esei.daa.dataset.UsersDataset.userToken;
import static es.uvigo.esei.daa.matchers.HasHttpStatus.hasOkStatus;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.containsPetsInAnyOrder;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.equalsToPet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/mem-context.xml")
@TestExecutionListeners({
        DbUnitTestExecutionListener.class,
        DbManagementTestExecutionListener.class,
        ApplicationContextJndiBindingTestExecutionListener.class
})
@ApplicationContextBinding(
        jndiUrl = "java:/comp/env/jdbc/daaexample",
        type = DataSource.class
)
@DbManagement(
        create = "classpath:db/hsqldb.sql",
        drop = "classpath:db/hsqldb-drop.sql"
)

@DatabaseSetup("/datasets/dataset.xml")    // Puede variar
@ExpectedDatabase("/datasets/dataset.xml")    // Puede variar
public class PetsResourceTest extends JerseyTest {


    @Override
    protected Application configure() {
        return new DAAExampleApplication();
    }

    @Override
    protected void configureClient(ClientConfig config) {
        super.configureClient(config);

        // Enables JSON transformation in client
        config.register(JacksonJsonProvider.class);
        config.property("com.sun.jersey.api.json.POJOMappingFeature", Boolean.TRUE);
    }

    //@Test
    public void testList() throws IOException {
        final Response response = target("pets").request()
                .header("Authorization", "Basic " + userToken(adminLogin()))
                .get();
        assertThat(response, hasOkStatus());

        final List<Pet> pets = response.readEntity(new GenericType<List<Pet>>(){});

        assertThat(pets, containsPetsInAnyOrder(pets()));
    }


    @Test
    public void testGet() throws IOException {
        final Response response = target("pets/" + existentId()).request()
                .header("Authorization", "Basic " + userToken(adminLogin()))
                .get();
        assertThat(response, hasOkStatus());

        final Pet pet = response.readEntity(Pet.class);

        assertThat(pet, is(equalsToPet(existentPet())));
    }


}
