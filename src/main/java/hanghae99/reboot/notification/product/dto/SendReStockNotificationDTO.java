package hanghae99.reboot.notification.product.dto;

import hanghae99.reboot.notification.product.domain.Product;
import hanghae99.reboot.notification.product.domain.ProductUserNotificationHistory;
import lombok.Builder;

@Builder
public record SendReStockNotificationDTO(
        Long productId,
        Long userId,
        Integer reStockRound
) {
    public ProductUserNotificationHistory toEntity(Product product) {
        return ProductUserNotificationHistory.builder()
                .product(product)
                .userId(this.userId)
                .reStockRound(this.reStockRound)
                .build();
    }
}
