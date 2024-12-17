package hanghae99.reboot.notification.product.domain;

import java.util.List;

public class ProductUserNotificationHistoryBuilder {

    public static ProductUserNotificationHistory build1() {
        Product product = ProductBuilder.build1();

        return ProductUserNotificationHistory.builder()
                .product(product)
                .userId(1L)
                .reStockRound(1)
                .build();
    }

    public static ProductUserNotificationHistory build2() {
        Product product = ProductBuilder.build1();

        return ProductUserNotificationHistory.builder()
                .product(product)
                .userId(2L)
                .reStockRound(1)
                .build();
    }

    public static List<ProductUserNotificationHistory> buildList() {
        return List.of(build1(), build2());
    }
}
