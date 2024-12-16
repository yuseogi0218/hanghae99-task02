package hanghae99.reboot.notification.product.dto;

import lombok.Builder;

@Builder
public record SendReStockNotificationDTO(
        Long productId,
        Long userId,
        Integer reStockRound
) { }
