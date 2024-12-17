package hanghae99.reboot.notification.product.domain;

import org.assertj.core.api.Assertions;

public class ProductNotificationHistoryBuilder {
    public static ProductNotificationHistory build2() {
        Product product = ProductBuilder.build1();

        ProductNotificationHistory productNotificationHistory = ProductNotificationHistory.builder()
                .product(product)
                .build();
        productNotificationHistory.updateLastSentUserId(3500L);

        return productNotificationHistory;
    }

    public static ProductNotificationHistory build_CANCELED_BY_SOLD_OUT() {
        Product product = ProductBuilder.build2();

        ProductNotificationHistory productNotificationHistory = ProductNotificationHistory.builder()
                .product(product)
                .build();
        productNotificationHistory.updateLastSentUserId(750L);
        productNotificationHistory.canceledBySoldOut();

        return productNotificationHistory;
    }

    public static ProductNotificationHistory build_CANCELED_BY_ERROR() {
        Product product = ProductBuilder.build3();

        ProductNotificationHistory productNotificationHistory = ProductNotificationHistory.builder()
                .product(product)
                .build();
        productNotificationHistory.updateLastSentUserId(250L);
        productNotificationHistory.canceledByError();

        return productNotificationHistory;
    }

    public static ProductNotificationHistory build_5() {
        Product product = ProductBuilder.build1();
        product.reStock();

        ProductNotificationHistory productNotificationHistory = ProductNotificationHistory.builder()
                .product(product)
                .build();

        return productNotificationHistory;
    }

    public static ProductNotificationHistory build_6() {
        Product product = ProductBuilder.build2();
        product.reStock();

        ProductNotificationHistory productNotificationHistory = ProductNotificationHistory.builder()
                .product(product)
                .build();

        return productNotificationHistory;
    }


    public static void assertProductNotificationHistory(ProductNotificationHistory actualProductNotificationHistory, ProductNotificationHistory expectedProductNotificationHistory) {
        Assertions.assertThat(actualProductNotificationHistory.getLastSentUserId()).isEqualTo(expectedProductNotificationHistory.getLastSentUserId());
        Assertions.assertThat(actualProductNotificationHistory.getReStockRound()).isEqualTo(expectedProductNotificationHistory.getReStockRound());
        Assertions.assertThat(actualProductNotificationHistory.getReStockNotificationStatus()).isEqualTo(expectedProductNotificationHistory.getReStockNotificationStatus());
    }
}
