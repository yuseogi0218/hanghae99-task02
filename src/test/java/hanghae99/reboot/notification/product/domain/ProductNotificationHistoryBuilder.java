package hanghae99.reboot.notification.product.domain;

import org.assertj.core.api.Assertions;

public class ProductNotificationHistoryBuilder {
    public static ProductNotificationHistory build2() {
        Product product = ProductBuilder.build_2reStock();

        ProductNotificationHistory productNotificationHistory = ProductNotificationHistory.builder()
                .product(product)
                .build();
        productNotificationHistory.updateLastSentUserId(500L);

        return productNotificationHistory;
    }

    public static void assertProductNotificationHistory(ProductNotificationHistory actualProductNotificationHistory, ProductNotificationHistory expectedProductNotificationHistory) {
        Assertions.assertThat(actualProductNotificationHistory.getLastSentUserId()).isEqualTo(expectedProductNotificationHistory.getLastSentUserId());
        Assertions.assertThat(actualProductNotificationHistory.getReStockRound()).isEqualTo(expectedProductNotificationHistory.getReStockRound());
        Assertions.assertThat(actualProductNotificationHistory.getReStockNotificationStatus()).isEqualTo(expectedProductNotificationHistory.getReStockNotificationStatus());
    }
}
