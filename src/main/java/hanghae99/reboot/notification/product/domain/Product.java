package hanghae99.reboot.notification.product.domain;

import hanghae99.reboot.notification.product.domain.status.StockStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reStockRound", nullable = false)
    private Integer reStockRound;

    @Column(name = "stockStatus", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StockStatus stockStatus;
}
