package hanghae99.reboot.notification.product.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products/{productId}/notifications")
@RestController
public class NotificationAPI {

    @PostMapping("/re-stock")
    public ResponseEntity<?> sendReStockNotification(
            @PathVariable("productId") Long productId
    ) {
        return ResponseEntity.ok().build();
    }
}
