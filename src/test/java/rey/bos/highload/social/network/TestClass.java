package rey.bos.highload.social.network;

import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import rey.bos.highload.social.network.config.BaeldungPostgresqlContainer;
import rey.bos.highload.social.network.config.TestConfig;

@SpringBootTest(classes = {Application.class, TestConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles({"tc", "tc-auto", "test"})
public class TestClass {

    @ClassRule
    public static PostgreSQLContainer<BaeldungPostgresqlContainer> postgreSQLContainer
        = BaeldungPostgresqlContainer.getInstance();

}
