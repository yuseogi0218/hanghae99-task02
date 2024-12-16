package hanghae99.reboot.notification.product.service;

import hanghae99.reboot.notification.common.exception.CustomException;
import hanghae99.reboot.notification.product.domain.Product;
import hanghae99.reboot.notification.product.exception.ProductErrorCode;
import hanghae99.reboot.notification.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findTopById(productId).orElseThrow(() -> new CustomException(ProductErrorCode.NOT_FOUND_PRODUCT));
    }

    @Transactional
    @Override
    public void reStockById(Long productId) {
        Product product = getProductById(productId);
        product.reStock();
    }
}
