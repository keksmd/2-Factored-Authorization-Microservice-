package podpivasniki.shortify.site.axontest.config;

import org.axonframework.deadline.DeadlineManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    DeadlineManager deadlinemanager(){
        return new DeadlineManager();
    }
}
