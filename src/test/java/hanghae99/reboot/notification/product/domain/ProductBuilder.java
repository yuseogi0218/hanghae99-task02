package hanghae99.reboot.notification.product.domain;

import org.assertj.core.api.Assertions;

public class ProductBuilder {

    public static Product build() {
        Product product = Product.builder().build();
        product.refillStock();

        return product;
    }

    public static void assertProduct(Product actualProduct, Product expectedProduct) {
        Assertions.assertThat(actualProduct.getReStockRound()).isEqualTo(expectedProduct.getReStockRound());
        Assertions.assertThat(actualProduct.getStockStatus()).isEqualTo(expectedProduct.getStockStatus());
    }
}
