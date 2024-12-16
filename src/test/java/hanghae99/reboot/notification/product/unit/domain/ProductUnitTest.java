package hanghae99.reboot.notification.product.unit.domain;

import hanghae99.reboot.notification.product.domain.Product;
import hanghae99.reboot.notification.product.domain.ProductBuilder;
import hanghae99.reboot.notification.product.domain.status.StockStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductUnitTest {

    @Test
    void refillStock() {
        // given
        Product product = ProductBuilder.build();
        Integer expectedReStockRound = product.getReStockRound() + 1;

        // when
        product.refillStock();

        // then
        Assertions.assertThat(product.getReStockRound()).isEqualTo(expectedReStockRound);
        Assertions.assertThat(product.getStockStatus()).isEqualTo(StockStatus.IN_STOCK);
    }

    @Test
    void decreaseStock() {
        // given
        Product product = ProductBuilder.build();

        // when
        product.decreaseStock();

        // then
        Assertions.assertThat(product.getStockStatus()).isEqualTo(StockStatus.OUT_OF_STOCK);
    }
}
