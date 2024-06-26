package rey.bos.highload.sn.external;

import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import rey.bos.highload.sn.core.config.BaeldungPostgresqlContainer;
import rey.bos.highload.sn.core.config.CoreConfig;
import rey.bos.highload.sn.external.config.TestExternalConfig;

@SpringBootTest(classes = {CoreConfig.class})
@ContextConfiguration(classes = TestExternalConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles({"tc", "tc-auto", "test"})
public class TestClass {

    @ClassRule
    public static PostgreSQLContainer<BaeldungPostgresqlContainer> postgreSQLContainer
        = BaeldungPostgresqlContainer.getInstance();

}
