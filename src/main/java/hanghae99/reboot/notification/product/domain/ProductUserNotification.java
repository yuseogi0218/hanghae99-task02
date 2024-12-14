package hanghae99.reboot.notification.product.domain;

import hanghae99.reboot.notification.common.util.BooleanAttributeConverter;
import hanghae99.reboot.notification.product.domain.status.ReStockNotificationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductUserNotification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class ProductUserNotification {

    @EmbeddedId
    private ProductUserNotificationId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId", nullable = false, updatable = false)
    private Product product;

    @Column(name = "userId", insertable = false, nullable = false, updatable = false)
    private Long userId;

    @Column(name = "isActivate", nullable = false)
    @Convert(converter = BooleanAttributeConverter.class)
    private Boolean isActivate;

    @CreatedDate
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    public void activateNotification() {
        isActivate = Boolean.TRUE;
    }

    public void disActivateNotification() {
        isActivate = Boolean.FALSE;
    }
}
