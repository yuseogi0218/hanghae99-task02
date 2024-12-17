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
    public void findTopByIdAndReStockNotificationStatusIsInProgressOrCanceledByError_존재_O() {
        // given
        Long expectedProductNotificationHistoryId = 4L;
        Long expectedProductId = 3L;
        ProductNotificationHistory expectedProductNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_ERROR();

        // when
        Optional<ProductNotificationHistory> optionalProductNotificationHistory =
                productNotificationHistoryRepository.findTopByIdAndReStockNotificationStatusIsInProgressOrCanceledByError(expectedProductNotificationHistoryId);

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
    public void findTopByIdAndReStockNotificationStatusIsInProgressOrCanceledByError_존재_x() {
        // given
        Long productNotificationHistoryId = 1L;

        // when
        Optional<ProductNotificationHistory> optionalProductNotificationHistory =
                productNotificationHistoryRepository.findTopByIdAndReStockNotificationStatusIsInProgressOrCanceledByError(productNotificationHistoryId);

        // then
        Assertions.assertThat(optionalProductNotificationHistory.isEmpty()).isTrue();
    }

    @Test
    public void findTopByProductIdAndReStockNotificationStatusIsInProgressOrCanceledByError_존재_O() {
        // given
        Long expectedProductNotificationHistoryId = 4L;
        Long expectedProductId = 3L;
        ProductNotificationHistory expectedProductNotificationHistory = ProductNotificationHistoryBuilder.build_CANCELED_BY_ERROR();

        // when
        Optional<ProductNotificationHistory> optionalProductNotificationHistory =
                productNotificationHistoryRepository.findTopByProductIdAndReStockNotificationStatusIsInProgressOrCanceledByError(expectedProductId);

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
    public void findTopByProductIdAndReStockNotificationStatusIsInProgressOrCanceledByError_존재_X() {
        // given
        Long productId = 1L;

        // when
        Optional<ProductNotificationHistory> optionalProductNotificationHistory =
                productNotificationHistoryRepository.findTopByProductIdAndReStockNotificationStatusIsInProgressOrCanceledByError(productId);

        // then
        Assertions.assertThat(optionalProductNotificationHistory.isEmpty()).isTrue();
    }
}
