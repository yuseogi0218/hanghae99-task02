package hanghae99.reboot.notification.product.service;

import hanghae99.reboot.notification.product.domain.ProductNotificationHistory;
import hanghae99.reboot.notification.product.domain.ProductUserNotificationHistory;
import hanghae99.reboot.notification.product.dto.SendReStockNotificationDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductNotificationService {

    Optional<ProductNotificationHistory> getProductNotificationHistoryByIdAndReStockNotificationStatusIsNotCompleted(Long productNotificationHistoryId);

    void createProductNotificationHistory(Long productId);

    void publishReStockNotificationEvent(Long productId);

    Page<Long> getUserIdOfProductUserNotificationByProductId(Long productId, Long lastSentUserId, Integer size);

    ProductUserNotificationHistory sendReStockNotification(SendReStockNotificationDTO dto);

    void saveAllProductUserNotificationHistories(List<ProductUserNotificationHistory> productUserNotificationHistories);
}
