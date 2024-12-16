package hanghae99.reboot.notification.common.eventQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventQueueConfig {

    @Bean
    public EventQueue<Long> eventQueue() {
        return new EventQueue<>();
    }
}
