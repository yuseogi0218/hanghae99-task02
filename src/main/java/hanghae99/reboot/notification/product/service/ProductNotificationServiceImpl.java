package hanghae99.reboot.notification.product.service;

import hanghae99.reboot.notification.common.exception.CustomException;
import hanghae99.reboot.notification.common.eventQueue.EventProducer;
import hanghae99.reboot.notification.product.domain.Product;
import hanghae99.reboot.notification.product.domain.ProductNotificationHistory;
import hanghae99.reboot.notification.product.domain.ProductUserNotificationHistory;
import hanghae99.reboot.notification.product.domain.status.StockStatus;
import hanghae99.reboot.notification.product.dto.SendReStockNotificationDTO;
import hanghae99.reboot.notification.product.exception.ProductErrorCode;
import hanghae99.reboot.notification.product.repository.ProductNotificationHistoryRepository;
import hanghae99.reboot.notification.product.repository.ProductUserNotificationHistoryRepository;
import hanghae99.reboot.notification.product.repository.ProductUserNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductNotificationServiceImpl implements ProductNotificationService {

    private final EventProducer eventProducer;

    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;
    private final ProductUserNotificationRepository productUserNotificationRepository;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;

    private final ProductService productService;

    /**
     * 재입고 알림 전송 기록 조회
     */
    @Override
    public Optional<ProductNotificationHistory> getProductNotificationHistoryByIdAndReStockNotificationStatusIsNotCompleted(Long productNotificationHistoryId) {
        return productNotificationHistoryRepository.findTopByIdAndReStockNotificationStatusIsNotCompleted(productNotificationHistoryId);
    }

    /**
     * 재입고 알림 전송 기록 생성
     */
    @Transactional
    @Override
    public void createProductNotificationHistory(Long productId) {
        Product product = productService.getProductById(productId);

        ProductNotificationHistory productNotificationHistory = ProductNotificationHistory.builder()
                .product(product)
                .build();

        productNotificationHistoryRepository.save(productNotificationHistory);
    }

    /**
     * 재입고 알림 전송 이벤트 발행
     */
    @Transactional
    @Override
    public void publishReStockNotificationEvent(Long productId) {
        Optional<ProductNotificationHistory> productNotificationHistoryOptional =
                productNotificationHistoryRepository.findTopByProductIdAndReStockNotificationStatusIsNotCompleted(productId);

        productNotificationHistoryOptional.ifPresent(
                productNotificationHistory -> {
                    productNotificationHistory.progressNotification();
                    // EventQueue 에 추가
                    eventProducer.publishEvent(productNotificationHistory.getId());
                }
        );
    }

    // productId 에 해당하는 사용자 중에서, lastSentUserId 이후 부터, size 명 만큼 보낼 수 있음
    // Page -> 다음 페이지가 있는지 확인하기 위함
    @Override
    public Page<Long> getUserIdOfProductUserNotificationByProductId(Long productId, Long lastSentUserId, Integer size) {
        return productUserNotificationRepository.findUserIdsByProductIdAfterLastSentUserId(productId, lastSentUserId, PageRequest.of(0, size));
    }

    /**
     * 재입고 알림 전송
     */
    @Transactional
    @Override
    public void sendReStockNotification(SendReStockNotificationDTO dto) {
        Product product = productService.getProductById(dto.productId());

        if (product.getStockStatus().equals(StockStatus.OUT_OF_STOCK)) {
            throw new CustomException(ProductErrorCode.OUT_OF_STOCK);
        }

        ProductUserNotificationHistory productUserNotificationHistory = ProductUserNotificationHistory.builder()
                .product(product)
                .userId(dto.userId())
                .reStockRound(dto.reStockRound())
                .build();

        productUserNotificationHistoryRepository.save(productUserNotificationHistory);
    }
}
