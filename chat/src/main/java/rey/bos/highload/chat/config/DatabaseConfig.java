package rey.bos.highload.chat.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories(basePackages = "rey.bos.highload.chat.io.repository")
public class DatabaseConfig {

    @Bean
    public SpringLiquibase liquibase(DataSource ds) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/liquibase/changelog.xml");
        liquibase.setDataSource(ds);
        return liquibase;
    }

}