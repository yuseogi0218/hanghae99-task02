package hanghae99.reboot.notification.product.unit.repository;

import hanghae99.reboot.notification.common.RepositoryUnitTest;
import hanghae99.reboot.notification.product.domain.Product;
import hanghae99.reboot.notification.product.domain.ProductBuilder;
import hanghae99.reboot.notification.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories(basePackageClasses = ProductRepository.class)
public class ProductRepositoryUnitTest extends RepositoryUnitTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findTopById_존재_O() {
        // given
        Long expectedProductId = 1L;
        Product expectedProduct = ProductBuilder.build();

        // when
        Optional<Product> optionalProduct = productRepository.findTopById(expectedProductId);

        // then
        Assertions.assertThat(optionalProduct.isPresent()).isTrue();
        optionalProduct.ifPresent(
                actualProduct -> {
                    Assertions.assertThat(actualProduct.getId()).isEqualTo(expectedProductId);
                    ProductBuilder.assertProduct(actualProduct, expectedProduct);
                }
        );
    }

    @Test
    public void findTopById_존재_X() {
        // given
        Long unknownProductId = 0L;

        // when
        Optional<Product> optionalProduct = productRepository.findTopById(unknownProductId);

        // then
        Assertions.assertThat(optionalProduct.isEmpty()).isTrue();
    }
}
