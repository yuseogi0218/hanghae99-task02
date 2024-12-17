package hanghae99.reboot.notification.product.dto;

public class SendReStockNotificationDTOBuilder {
    public static SendReStockNotificationDTO build() {
        return SendReStockNotificationDTO.builder()
                .productId(1L)
                .userId(1L)
                .reStockRound(1)
                .build();
    }
}
