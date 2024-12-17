package hanghae99.reboot.notification.common.eventQueue;

import hanghae99.reboot.notification.common.exception.CustomException;
import hanghae99.reboot.notification.product.domain.Product;
import hanghae99.reboot.notification.product.domain.ProductNotificationHistory;
import hanghae99.reboot.notification.product.domain.ProductUserNotificationHistory;
import hanghae99.reboot.notification.product.dto.SendReStockNotificationDTO;
import hanghae99.reboot.notification.product.exception.ProductErrorCode;
import hanghae99.reboot.notification.product.service.ProductNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EventConsumer {

    // 1초에 500개의 메시지를 전송할 수 있음
    private final Integer MESSAGE_LIMIT_IN_ONE_SECONDS = 500;

    private final EventQueue<Long> eventQueue;

    private final ProductNotificationService productNotificationService;

    @Transactional
    @Scheduled(fixedDelay = 1000) // 1초마다 실행
    public void consumeEvent() {
        // 현재 이벤트에서 전송한 메시지 개수
        int sentMessageCount = 0;
        List<ProductUserNotificationHistory> productUserNotificationHistories = new ArrayList<>();

        // 이벤트 큐가 비어있지 않거나, 현재 이벤트에서 전송한 메시지 개수가 500개 미만이면
        while (!eventQueue.isEmpty() && sentMessageCount < MESSAGE_LIMIT_IN_ONE_SECONDS) {
            // 수행할 이벤트(재입고 알림 전송 기록 Id) 확인
            Long productNotificationHistoryId = eventQueue.peekEvent();

            // 재입고 알림 전송 기록 DB Id를 사용하여 완료되지 않은 재입고 알림 전송 기록 조회
            Optional<ProductNotificationHistory> productNotificationHistoryOptional
                    = productNotificationService.getProductNotificationHistoryByIdAndReStockNotificationStatusIsNotCompleted(productNotificationHistoryId);

            // 존재하지 않다면, 해당 이벤트를 이벤트 큐에서 제거 후, 다음 이벤트 진행
            if (productNotificationHistoryOptional.isEmpty()) {
                eventQueue.removeEvent();
                continue;
            }

            // 존재한다면 이벤트(알림 전송) 진행
            ProductNotificationHistory productNotificationHistory = productNotificationHistoryOptional.get();

            Product product = productNotificationHistory.getProduct();
            Long productId = product.getId();
            Long lastSentUserId = productNotificationHistory.getLastSentUserId();
            Integer size = (MESSAGE_LIMIT_IN_ONE_SECONDS - sentMessageCount);

            // 알림 전송할 유저 목록 확인
            // productId 에 해당하는 유저 중에서, lastSentUserId 이후 부터, (500 - bulkNotifications.size()) 명 만큼 보낼 수 있음
            // Page -> 다음 페이지가 있는지 확인하기 위함
            Page<Long> productUserNotificationUserIdsPage = productNotificationService.getUserIdOfProductUserNotificationByProductId(productId, lastSentUserId, size);

            Integer reStockRound = productNotificationHistory.getReStockRound();
            List<Long> userIds = productUserNotificationUserIdsPage.getContent();
            for (Long userId : userIds) {
                sentMessageCount++;
                try {
                    SendReStockNotificationDTO sendReStockNotificationDTO = SendReStockNotificationDTO.builder()
                            .productId(productId)
                            .userId(userId)
                            .reStockRound(reStockRound)
                            .build();

                    // 알림 전송
                    ProductUserNotificationHistory productUserNotificationHistory =
                            productNotificationService.sendReStockNotification(sendReStockNotificationDTO);
                    productUserNotificationHistories.add(productUserNotificationHistory);
                } catch (CustomException e) {
                    // 재입고 알림을 보내던 중 재고가 모두 소진된다면, 알림 보내는 것을 중단한다.
                    if (e.getErrorCode().equals(ProductErrorCode.OUT_OF_STOCK.getCode())) {
                        productNotificationHistory.canceledBySoldOut();
                    } else {
                        // 써드 파티 연동에서의 예외
                        productNotificationHistory.canceledByError();
                    }
                    break;
                }
                lastSentUserId = userId;
            }

            // 성공적으로 다 보냈지만, 아직 모든 사람에 대해서 알림을 전송하지 못하였을 경우
            if (!productUserNotificationUserIdsPage.isLast() && productNotificationHistory.statusIsInProgress()) {
                productNotificationHistory.updateLastSentUserId(lastSentUserId);
            // 성공적으로 다 보냈고, 모든 사람에 대해서 알림을 전송한 경우
            } else if (productUserNotificationUserIdsPage.isLast() && productNotificationHistory.statusIsInProgress()) {
                productNotificationHistory.complete();
                eventQueue.removeEvent();
            // 메시지를 보내는 중간에 에러가 발생한 경우
            } else if (productNotificationHistory.statusIsCanceled()) {
                eventQueue.removeEvent();
            }
        }
        // 알림 전송 기록 저장
        productNotificationService.saveAllProductUserNotificationHistories(productUserNotificationHistories);
    }
}
