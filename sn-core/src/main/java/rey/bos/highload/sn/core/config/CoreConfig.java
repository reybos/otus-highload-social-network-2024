package rey.bos.highload.sn.core.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@ComponentScan(basePackages = "rey.bos.highload")
@EnableAutoConfiguration
public class CoreConfig {
}
