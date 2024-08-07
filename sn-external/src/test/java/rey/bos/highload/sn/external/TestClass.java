package rey.bos.highload.sn.external;

import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import rey.bos.highload.sn.core.config.BaeldungPostgresqlContainer;
import rey.bos.highload.sn.core.config.CoreConfig;
import rey.bos.highload.sn.external.config.TestExternalConfig;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@SpringBootTest(classes = {CoreConfig.class})
@ContextConfiguration(classes = TestExternalConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles({"tc", "tc-auto", "test"})
@Testcontainers
@Slf4j
public class TestClass {

    @ClassRule
    public static BaeldungPostgresqlContainer postgreSQLContainer = BaeldungPostgresqlContainer.getInstance();

    public static RabbitMQContainer rabbitMQContainer;

    static {
        rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.10.6-management-alpine")
            .withStartupTimeout(Duration.of(100, SECONDS));
        rabbitMQContainer.start();
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        log.info("url ->{}",rabbitMQContainer.getAmqpUrl());
        log.info("port ->{}",rabbitMQContainer.getHttpPort());

        registry.add("spring.rabbitmq.host",() -> rabbitMQContainer.getHost());
        registry.add("spring.rabbitmq.port",() -> rabbitMQContainer.getAmqpPort());
    }

}
