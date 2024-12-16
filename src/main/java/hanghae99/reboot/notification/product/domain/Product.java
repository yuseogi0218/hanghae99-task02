package hanghae99.reboot.notification.product.domain;

import hanghae99.reboot.notification.product.domain.status.StockStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "Product")
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reStockRound", nullable = false)
    private Integer reStockRound = 0;

    @Column(name = "stockStatus", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StockStatus stockStatus = StockStatus.OUT_OF_STOCK;

    @Builder
    public Product() {}

    public void reStock() {
        // 이전에 재고가 없는 상태에서, 재고 채우기
        reStockRound ++;
        stockStatus = StockStatus.IN_STOCK;
    }

    public void decreaseStock() {
        // 모든 재고를 다 소진한다면
        stockStatus = StockStatus.OUT_OF_STOCK;
    }
}
