package hanghae99.reboot.notification.product.service;

import hanghae99.reboot.notification.product.domain.Product;

public interface ProductService {

    Product getProductById(Long productId);

    void refillProductStockById(Long productId);
}
