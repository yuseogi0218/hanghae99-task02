package hanghae99.reboot.notification.product.domain;

import org.assertj.core.api.Assertions;

public class ProductBuilder {

    public static Product build() {
        Product product = Product.builder().build();
        product.reStock();

        return product;
    }

    public static Product build_2reStock() {
        Product product = Product.builder().build();
        product.reStock();
        product.reStock();

        return product;
    }

    public static Product build_OUT_OF_STOCK() {
        Product product = Product.builder().build();

        product.decreaseStock();

        return product;
    }

    public static void assertProduct(Product actualProduct, Product expectedProduct) {
        Assertions.assertThat(actualProduct.getReStockRound()).isEqualTo(expectedProduct.getReStockRound());
        Assertions.assertThat(actualProduct.getStockStatus()).isEqualTo(expectedProduct.getStockStatus());
    }
}
