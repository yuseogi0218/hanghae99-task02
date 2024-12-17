package hanghae99.reboot.notification.common.eventQueue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EventQueueUnitTest {

    @Test
    public void addEvent() {
        // given
        EventQueue<Long> eventQueue = new EventQueue<>();
        Long event = 1L;

        // when
        eventQueue.addEvent(event);

        // then
        Assertions.assertThat(eventQueue.isEmpty()).isFalse();
        Assertions.assertThat(eventQueue.peekEvent()).isEqualTo(event);
    }

    @Test
    public void peekEvent() {
        // given
        EventQueue<Long> eventQueue = new EventQueue<>();
        Long firstEvent = 1L;
        Long secondEvent = 2L;
        eventQueue.addEvent(firstEvent);
        eventQueue.addEvent(secondEvent);

        // when
        Long topEvent = eventQueue.peekEvent();

        // then
        Assertions.assertThat(topEvent).isEqualTo(firstEvent);
    }

    @Test
    public void removeEvent() {
        // given
        EventQueue<Long> eventQueue = new EventQueue<>();
        Long firstEvent = 1L;
        Long secondEvent = 2L;
        eventQueue.addEvent(firstEvent);
        eventQueue.addEvent(secondEvent);

        // when
        eventQueue.removeEvent();

        // then
        Assertions.assertThat(eventQueue.peekEvent()).isEqualTo(secondEvent);
    }

    @Test
    public void isEmpty_True() {
        // given
        EventQueue<Long> eventQueue = new EventQueue<>();

        // when & then
        Assertions.assertThat(eventQueue.isEmpty()).isTrue();
    }

    @Test
    public void isEmpty_False() {
        // given
        EventQueue<Long> eventQueue = new EventQueue<>();
        Long event = 1L;
        eventQueue.addEvent(event);

        // when & then
        Assertions.assertThat(eventQueue.isEmpty()).isFalse();
    }
}
