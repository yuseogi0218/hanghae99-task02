package hanghae99.reboot.notification.common.eventQueue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EventQueue<T> {
    private final ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();

    // 이벤트 추가
    public void addEvent(T event) {
        queue.offer(event);
    }

    // 가장 처음 이벤트 확인
    public T peekEvent() {
        return queue.peek();
    }

    // 가장 처음 이벤트 제거
    public void removeEvent() {
        queue.poll();
    }

    // 큐가 비었는지 확인
    public boolean isEmpty() {
        return queue.isEmpty();
    }

}
