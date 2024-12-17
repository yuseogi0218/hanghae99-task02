package hanghae99.reboot.notification.common.eventQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class EventQueueConfig {

    @Bean
    public EventQueue<Long> eventQueue() {
        return new EventQueue<>();
    }
}
