package hanghae99.reboot.notification.product.repository;

import hanghae99.reboot.notification.product.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findTopById(Long id);
}
