package hanghae99.reboot.notification.product.repository;

import hanghae99.reboot.notification.product.domain.ProductNotificationHistory;
import hanghae99.reboot.notification.product.domain.ProductUserNotificationHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductUserNotificationHistoryRepository {
    void saveAll(List<ProductUserNotificationHistory> productUserNotificationHistories);
}
