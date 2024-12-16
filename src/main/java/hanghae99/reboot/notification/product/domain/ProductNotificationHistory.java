package hanghae99.reboot.notification.product.domain;

import hanghae99.reboot.notification.product.domain.status.ReStockNotificationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productNotificationHistory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false, updatable = false)
    private Product product;

    @Column(name = "lastSentUserId")
    private Long lastSentUserId = null;

    @Column(name = "reStockRound", nullable = false, updatable = false)
    private Integer reStockRound;

    @Column(name = "reStockNotificationStatus", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReStockNotificationStatus reStockNotificationStatus = ReStockNotificationStatus.IN_PROGRESS;

    @Builder
    public ProductNotificationHistory(Product product) {
        this.product = product;
        this.reStockRound = product.getReStockRound();
    }

    public void updateLastSentUserId(Long lastSentUserId) {
        this.lastSentUserId = lastSentUserId;
    }

    public void progressNotification() {
        this.reStockNotificationStatus = ReStockNotificationStatus.IN_PROGRESS;
    }

    public boolean statusIsInProgress() {
        return this.reStockNotificationStatus.equals(ReStockNotificationStatus.IN_PROGRESS);
    }

    public void canceledByOutOfStock() {
        this.reStockNotificationStatus = ReStockNotificationStatus.CANCELED_BY_SOLD_OUT;
    }

    public void canceledByError() {
        this.reStockNotificationStatus = ReStockNotificationStatus.CANCELED_BY_ERROR;
    }

    public boolean statusIsCanceled() {
        return (this.reStockNotificationStatus == ReStockNotificationStatus.CANCELED_BY_SOLD_OUT) ||
                (this.reStockNotificationStatus == ReStockNotificationStatus.CANCELED_BY_ERROR);
    }

    public void complete() {
        this.reStockNotificationStatus = ReStockNotificationStatus.COMPLETED;
    }
}
