package rey.bos.highload.dialog;

import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import rey.bos.highload.dialog.config.BaeldungPostgresqlContainer;
import rey.bos.highload.dialog.config.DialogConfig;
import rey.bos.highload.dialog.config.TestDialogConfig;

@SpringBootTest(classes = {DialogConfig.class})
@ContextConfiguration(classes = {TestDialogConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles({"tc", "tc-auto", "test"})
@Testcontainers
@Slf4j
public class TestClass {

    @ClassRule
    public static BaeldungPostgresqlContainer postgreSQLContainer = BaeldungPostgresqlContainer.getInstance();

    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

}