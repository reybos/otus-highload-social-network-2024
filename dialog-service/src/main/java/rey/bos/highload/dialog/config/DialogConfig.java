package rey.bos.highload.dialog.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@ComponentScan(basePackages = "rey.bos.highload")
@EnableAutoConfiguration
public class DialogConfig {
}
