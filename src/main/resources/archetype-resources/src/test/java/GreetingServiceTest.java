package $package;

import jakarta.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ArquillianExtension.class)
public class GreetingServiceTest {

    private final static Logger LOGGER = Logger.getLogger(GreetingServiceTest.class.getName());

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(GreetingMessage.class)
                .addClass(GreetingService.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    GreetingService service;

    @Test
    @DisplayName("testing buildGreetingMessage")
    public void testBuildingGreetingMessage() {
        LOGGER.log(Level.INFO, " Running test:: GreetingServiceTest#testBuildingGreetingMessage ... ");
        GreetingMessage message = service.buildGreetingMessage("Jakarta EE");
        assertTrue(message.getMessage().startsWith("Say Hello to Jakarta EE at "),
                "message should start with \"Say Hello to Jakarta EE at \"");
    }
}
