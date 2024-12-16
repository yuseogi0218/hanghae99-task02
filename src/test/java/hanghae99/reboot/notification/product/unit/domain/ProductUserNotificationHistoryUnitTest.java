package hanghae99.reboot.notification.product.unit.domain;

import hanghae99.reboot.notification.product.domain.Product;
import hanghae99.reboot.notification.product.domain.ProductBuilder;
import hanghae99.reboot.notification.product.domain.ProductUserNotificationHistory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductUserNotificationHistoryUnitTest {

    @Test
    public void constructor() {
        // given
        Product product = ProductBuilder.build();
        Long userId = 1L;
        Integer restockRound = 1;

        // when
        ProductUserNotificationHistory productUserNotificationHistory = ProductUserNotificationHistory.builder()
                .product(product)
                .userId(userId)
                .reStockRound(restockRound)
                .build();

        // then
        Assertions.assertThat(productUserNotificationHistory.getProduct()).isEqualTo(product);
        Assertions.assertThat(productUserNotificationHistory.getUserId()).isEqualTo(userId);
        Assertions.assertThat(productUserNotificationHistory.getReStockRound()).isEqualTo(restockRound);
    }
}
