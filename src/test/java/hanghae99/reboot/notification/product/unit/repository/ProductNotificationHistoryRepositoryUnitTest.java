package hanghae99.reboot.notification.product.unit.repository;

import hanghae99.reboot.notification.common.RepositoryUnitTest;
import hanghae99.reboot.notification.product.domain.ProductNotificationHistory;
import hanghae99.reboot.notification.product.domain.ProductNotificationHistoryBuilder;
import hanghae99.reboot.notification.product.repository.ProductNotificationHistoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories(basePackageClasses = ProductNotificationHistoryRepository.class)
public class ProductNotificationHistoryRepositoryUnitTest extends RepositoryUnitTest {

    @Autowired
    ProductNotificationHistoryRepository productNotificationHistoryRepository;

    @Test
    public void findTopByIdAndReStockNotificationStatusIsNotCompleted_존재_O() {
        // given
        Long expectedProductNotificationHistoryId = 3L;
        Long expectedProductId = 2L;
        ProductNotificationHistory expectedProductNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_SOLD_OUT();

        // when
        Optional<ProductNotificationHistory> optionalProductNotificationHistory =
                productNotificationHistoryRepository.findTopByIdAndReStockNotificationStatusIsNotCompleted(expectedProductNotificationHistoryId);

        // then
        Assertions.assertThat(optionalProductNotificationHistory.isPresent()).isTrue();
        optionalProductNotificationHistory.ifPresent(
                actualProductNotificationHistory -> {
                    Assertions.assertThat(actualProductNotificationHistory.getId()).isEqualTo(expectedProductNotificationHistoryId);
                    Assertions.assertThat(actualProductNotificationHistory.getProduct().getId()).isEqualTo(expectedProductId);
                    ProductNotificationHistoryBuilder.assertProductNotificationHistory(actualProductNotificationHistory, expectedProductNotificationHistory);
                }
        );
    }

    @Test
    public void findTopByIdAndReStockNotificationStatusIsNotCompleted_존재_x() {
        // given
        Long productNotificationHistoryId = 1L;

        // when
        Optional<ProductNotificationHistory> optionalProductNotificationHistory =
                productNotificationHistoryRepository.findTopByIdAndReStockNotificationStatusIsNotCompleted(productNotificationHistoryId);

        // then
        Assertions.assertThat(optionalProductNotificationHistory.isEmpty()).isTrue();
    }

    @Test
    public void findTopByProductIdAndReStockNotificationStatusIsNotCompleted_존재_O() {
        // given
        Long expectedProductNotificationHistoryId = 3L;
        Long expectedProductId = 2L;
        ProductNotificationHistory expectedProductNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_SOLD_OUT();

        // when
        Optional<ProductNotificationHistory> optionalProductNotificationHistory =
                productNotificationHistoryRepository.findTopByProductIdAndReStockNotificationStatusIsNotCompleted(expectedProductId);

        // then
        Assertions.assertThat(optionalProductNotificationHistory.isPresent()).isTrue();
        optionalProductNotificationHistory.ifPresent(
                actualProductNotificationHistory -> {
                    Assertions.assertThat(actualProductNotificationHistory.getId()).isEqualTo(expectedProductNotificationHistoryId);
                    Assertions.assertThat(actualProductNotificationHistory.getProduct().getId()).isEqualTo(expectedProductId);
                    ProductNotificationHistoryBuilder.assertProductNotificationHistory(actualProductNotificationHistory, expectedProductNotificationHistory);
                }
        );
    }

    @Test
    public void findTopByProductIdAndReStockNotificationStatusIsNotCompleted_존재_X() {
        // given
        Long productId = 1L;

        // when
        Optional<ProductNotificationHistory> optionalProductNotificationHistory =
                productNotificationHistoryRepository.findTopByProductIdAndReStockNotificationStatusIsNotCompleted(productId);

        // then
        Assertions.assertThat(optionalProductNotificationHistory.isEmpty()).isTrue();
    }
}
