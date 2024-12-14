package hanghae99.reboot.notification.product.domain;

import hanghae99.reboot.notification.product.domain.status.ReStockNotificationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @Column(name = "lastSentUserId", nullable = false, updatable = false)
    private Long lastSentUserId;

    @Column(name = "reStockRound", nullable = false, updatable = false)
    private Integer reStockRound;

    @Column(name = "reStockNotificationStatus", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReStockNotificationStatus reStockNotificationStatus;
}
