package hanghae99.reboot.notification.product.repository;

import hanghae99.reboot.notification.product.domain.ProductUserNotification;
import hanghae99.reboot.notification.product.domain.ProductUserNotificationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, ProductUserNotificationId> {

    @Query(value = "select pun.userId " +
            "from ProductUserNotification pun " +
            "where pun.product.id = :productId " +
            "and (:lastSentUserId is null or pun.userId > :lastSentUserId) " +
            "order by pun.updatedAt asc",
            countQuery = "select COUNT(pun) " +
                    "from ProductUserNotification pun " +
                    "where pun.product.id = :productId " +
                    "and (:lastSentUserId is null or pun.userId > :lastSentUserId)")
    Page<Long> findUserIdsByProductIdAfterLastSentUserId(
            @Param("productId") Long productId,
            @Param("lastSentUserId") Long lastSentUserId,
            Pageable pageable);
}
