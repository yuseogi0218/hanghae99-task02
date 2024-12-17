package hanghae99.reboot.notification.common.eventQueue;

import hanghae99.reboot.notification.product.domain.ProductNotificationHistory;
import hanghae99.reboot.notification.product.domain.ProductNotificationHistoryBuilder;
import hanghae99.reboot.notification.product.service.ProductNotificationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventConsumerUnitTest {

    private EventConsumer eventConsumer;

    private EventQueue<Long> eventQueue;

    @Mock
    private ProductNotificationServiceImpl productNotificationService;

    @BeforeEach
    public void setUp() {
        eventQueue = new EventQueue<>();
        eventConsumer = new EventConsumer(eventQueue, productNotificationService);
    }

    /**
     * 이벤트 성공
     * 1번 상품 - 300명
     * 2번 상품 - 200명
     */
    @Test
    public void consumeEvent() {
        // given
        Long product1Id = 1L;
        Long product2Id = 2L;
        eventQueue.addEvent(1L);
        eventQueue.addEvent(2L);
        List<Long> product1UserNotificationUserIds = LongStream.rangeClosed(1, 300)
                .boxed()
                .toList();
        Page<Long> product1UserNotificationUserIdsPage = new PageImpl<>(product1UserNotificationUserIds, PageRequest.of(0, product1UserNotificationUserIds.size()), product1UserNotificationUserIds.size());

        List<Long> product2UserNotificationUserIds = LongStream.rangeClosed(1, 200)
                .boxed()
                .toList();
        Page<Long> product2UserNotificationUserIdsPage = new PageImpl<>(product2UserNotificationUserIds, PageRequest.of(0, product2UserNotificationUserIds.size()), product2UserNotificationUserIds.size());

        ProductNotificationHistory productNotificationHistory5 = ProductNotificationHistoryBuilder.build_5();
        ProductNotificationHistory productNotificationHistory6 = ProductNotificationHistoryBuilder.build_6();

        // stub
        when(productNotificationService.getProductNotificationHistoryByIdAndReStockNotificationStatusIsNotCompleted(product1Id)).thenReturn(Optional.of(productNotificationHistory5));
        when(productNotificationService.getProductNotificationHistoryByIdAndReStockNotificationStatusIsNotCompleted(product2Id)).thenReturn(Optional.of(productNotificationHistory6));

        when(productNotificationService.getUserIdOfProductUserNotificationByProductId(productNotificationHistory5.getProduct().getId(), null, 500)).thenReturn(product1UserNotificationUserIdsPage);
        when(productNotificationService.getUserIdOfProductUserNotificationByProductId(productNotificationHistory6.getProduct().getId(), null, 200)).thenReturn(product2UserNotificationUserIdsPage);

        // when
        eventConsumer.consumeEvent();

        // then
        verify(productNotificationService, times(500)).sendReStockNotification(any());
        Assertions.assertThat(eventQueue.isEmpty()).isTrue();
    }
}
