package hanghae99.reboot.notification.product.unit.service;

import hanghae99.reboot.notification.common.ServiceUnitTest;
import hanghae99.reboot.notification.common.exception.CustomException;
import hanghae99.reboot.notification.product.domain.Product;
import hanghae99.reboot.notification.product.domain.ProductBuilder;
import hanghae99.reboot.notification.product.domain.status.StockStatus;
import hanghae99.reboot.notification.product.exception.ProductErrorCode;
import hanghae99.reboot.notification.product.repository.ProductRepository;
import hanghae99.reboot.notification.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class ProductServiceImplUnitTest extends ServiceUnitTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    /**
     * 상품 DB Id 로 상품 조회 성공
     */
    @Test
    public void getProductById_성공() {
        // given
        Long productId = 1L;
        Product expectedProduct = ProductBuilder.build();

        // stub
        when(productRepository.findTopById(productId)).thenReturn(Optional.of(expectedProduct));

        // when
        Product actualProduct = productService.getProductById(productId);

        // then
        Assertions.assertThat(actualProduct).isEqualTo(expectedProduct);
    }

    /**
     * 상품 DB Id 로 상품 조회 실패
     * - 실패 사유 : 상품 DB Id에 해당하는 상품이 존재하지 않습니다.
     */
    @Test
    public void getProductById_실패_NOT_FOUND_PRODUCT() {
        // given
        Long unknownProductId = 0L;

        // stub
        when(productRepository.findTopById(unknownProductId)).thenReturn(Optional.empty());

        // when
        Assertions.assertThatThrownBy(() -> productService.getProductById(unknownProductId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ProductErrorCode.NOT_FOUND_PRODUCT.getMessage());
    }

    /**
     * 상품 재고 재입고
     */
    @Test
    public void reStockById() {
        // given
        Long productId = 1L;
        Product product = ProductBuilder.build();
        Integer expectedReStockRound = product.getReStockRound() + 1;

        // stub
        when(productRepository.findTopById(productId)).thenReturn(Optional.of(product));

        // when
        productService.reStockById(productId);

        // then
        Assertions.assertThat(product.getReStockRound()).isEqualTo(expectedReStockRound);
        Assertions.assertThat(product.getStockStatus()).isEqualTo(StockStatus.IN_STOCK);
    }

    /**
     * 상품 재고 재입고 실패
     * 실패 사유 : 상품 DB Id에 해당하는 상품이 존재하지 않습니다.
     */
    @Test
    public void reStockById_실패_NOT_FOUND_PRODUCT() {
        // given
        Long unknownProductId = 0L;

        // stub
        when(productRepository.findTopById(unknownProductId)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> productService.reStockById(unknownProductId))
                .isInstanceOf(CustomException.class)
                .hasMessage(ProductErrorCode.NOT_FOUND_PRODUCT.getMessage());
    }
}
