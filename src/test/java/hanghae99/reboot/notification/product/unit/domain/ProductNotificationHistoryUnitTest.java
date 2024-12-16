package hanghae99.reboot.notification.product.unit.domain;

import hanghae99.reboot.notification.product.domain.Product;
import hanghae99.reboot.notification.product.domain.ProductBuilder;
import hanghae99.reboot.notification.product.domain.ProductNotificationHistory;
import hanghae99.reboot.notification.product.domain.ProductNotificationHistoryBuilder;
import hanghae99.reboot.notification.product.domain.status.ReStockNotificationStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductNotificationHistoryUnitTest {

    @Test
    public void constructorWithProduct() {
        // given
        Product product = ProductBuilder.build();

        // when
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistory.builder()
                .product(product)
                .build();

        // then
        Assertions.assertThat(productNotificationHistory.getProduct()).isEqualTo(product);
        Assertions.assertThat(productNotificationHistory.getLastSentUserId()).isNull();
        Assertions.assertThat(productNotificationHistory.getReStockRound()).isEqualTo(product.getReStockRound());
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.IN_PROGRESS);
    }

    @Test
    public void updateLastSentUserId() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build2();
        Long expectedLastSentUserId = 1000L;

        // when
        productNotificationHistory.updateLastSentUserId(expectedLastSentUserId);

        // then
        Assertions.assertThat(productNotificationHistory.getLastSentUserId()).isEqualTo(expectedLastSentUserId);
    }

    @Test
    public void progressNotification() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_SOLD_OUT();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.CANCELED_BY_SOLD_OUT);

        // when
        productNotificationHistory.progressNotification();

        // then
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.IN_PROGRESS);
    }

    @Test
    public void statusIsInProgress_True() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build2();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.IN_PROGRESS);

        // when & then
        Assertions.assertThat(productNotificationHistory.statusIsInProgress()).isTrue();
    }

    @Test
    public void statusIsInProgress_False_CANCELED_BY_SOLD_OUT() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_SOLD_OUT();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.CANCELED_BY_SOLD_OUT);

        // when & then
        Assertions.assertThat(productNotificationHistory.statusIsInProgress()).isFalse();
    }

    @Test
    public void statusIsInProgress_False_CANCELED_BY_ERROR() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_ERROR();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.CANCELED_BY_ERROR);

        // when & then
        Assertions.assertThat(productNotificationHistory.statusIsInProgress()).isFalse();
    }

    @Test
    public void canceledBySoldOut() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build2();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.IN_PROGRESS);

        // when
        productNotificationHistory.canceledBySoldOut();

        // then
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.CANCELED_BY_SOLD_OUT);
    }

    @Test
    public void canceledByError() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build2();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.IN_PROGRESS);

        // when
        productNotificationHistory.canceledByError();

        // then
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.CANCELED_BY_ERROR);
    }

    @Test
    public void statusIsCanceled_True_CANCELED_BY_SOLD_OUT() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_SOLD_OUT();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.CANCELED_BY_SOLD_OUT);

        // when & then
        Assertions.assertThat(productNotificationHistory.statusIsCanceled()).isTrue();
    }

    @Test
    public void statusIsCanceled_True_CANCELED_BY_ERROR() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_ERROR();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.CANCELED_BY_ERROR);

        // when & then
        Assertions.assertThat(productNotificationHistory.statusIsCanceled()).isTrue();
    }

    @Test
    public void statusIsCanceled_False() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build2();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.IN_PROGRESS);

        // when & then
        Assertions.assertThat(productNotificationHistory.statusIsCanceled()).isFalse();
    }

    @Test
    public void complete() {
        // given
        ProductNotificationHistory productNotificationHistory = ProductNotificationHistoryBuilder.build2();
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.IN_PROGRESS);

        // when
        productNotificationHistory.complete();

        // then
        Assertions.assertThat(productNotificationHistory.getReStockNotificationStatus()).isEqualTo(ReStockNotificationStatus.COMPLETED);
    }
}
