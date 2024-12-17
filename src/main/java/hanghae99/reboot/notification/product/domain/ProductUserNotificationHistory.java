package hanghae99.reboot.notification.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "productUserNotificationHistory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductUserNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false, updatable = false)
    private Product product;

    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "reStockRound", nullable = false, updatable = false)
    private Integer reStockRound;

    @Column(name = "sentAt", nullable = false, updatable = false)
    private final LocalDateTime sentAt = LocalDateTime.now();

    @Builder
    public ProductUserNotificationHistory(Product product, Long userId, Integer reStockRound) {
        this.product = product;
        this.userId = userId;
        this.reStockRound = reStockRound;
    }
}
