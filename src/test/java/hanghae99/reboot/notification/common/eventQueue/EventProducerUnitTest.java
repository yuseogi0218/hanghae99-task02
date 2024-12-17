package hanghae99.reboot.notification.common.eventQueue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventProducerUnitTest {

    @InjectMocks
    private EventProducer eventProducer;

    @Mock
    private EventQueue<Long> eventQueue;

    @Test
    public void publishEvent() {
        // given
        Long event = 1L;

        // when
        eventProducer.publishEvent(event);

        // then
        verify(eventQueue, times(1)).addEvent(event);
    }
}
