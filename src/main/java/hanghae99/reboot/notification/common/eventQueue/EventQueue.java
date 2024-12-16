package hanghae99.reboot.notification.common.eventQueue;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EventQueue<T> {
    private final ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();

    // 메시지 추가
    public void addEvent(T event) {
        queue.offer(event);
    }

    public T peekEvent() {
        return queue.peek();
    }

    // 메시지 가져오기
    public T getEvent() {
        return queue.poll(); // 큐에서 메시지 가져오고 제거
    }

    // 큐가 비었는지 확인
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // 큐 크기 확인
    public int size() {
        return queue.size();
    }
}
