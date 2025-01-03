package hanghae99.reboot.notification.product.repository;

import hanghae99.reboot.notification.product.domain.ProductNotificationHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductNotificationHistoryRepository extends CrudRepository<ProductNotificationHistory, Long> {

    @Query(value = "select * from productNotificationHistory pnh " +
            "where pnh.id = :id " +
            "and (pnh.reStockNotificationStatus = 'IN_PROGRESS' or pnh.reStockNotificationStatus = 'CANCELED_BY_ERROR')" +
            "limit 1",
            nativeQuery = true)
    Optional<ProductNotificationHistory> findTopByIdAndReStockNotificationStatusIsInProgressOrCanceledByError(Long id);

    @Query(value = "select * from productNotificationHistory pnh " +
            "where pnh.productId = :productId " +
            "and (pnh.reStockNotificationStatus = 'IN_PROGRESS' or pnh.reStockNotificationStatus = 'CANCELED_BY_ERROR')" +
            "limit 1",
            nativeQuery = true)
    Optional<ProductNotificationHistory> findTopByProductIdAndReStockNotificationStatusIsInProgressOrCanceledByError(Long productId);
}
