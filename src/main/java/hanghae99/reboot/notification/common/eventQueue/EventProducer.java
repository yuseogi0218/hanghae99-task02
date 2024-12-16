package hanghae99.reboot.notification.common.eventQueue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventProducer {

    private final EventQueue<Long> eventQueue;

    public void publishEvent(Long event) {
        eventQueue.addEvent(event);
    }
}
