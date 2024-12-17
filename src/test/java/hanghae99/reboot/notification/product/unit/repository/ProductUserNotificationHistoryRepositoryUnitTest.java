package hanghae99.reboot.notification.product.unit.repository;

import hanghae99.reboot.notification.product.domain.ProductUserNotificationHistory;
import hanghae99.reboot.notification.product.domain.ProductUserNotificationHistoryBuilder;
import hanghae99.reboot.notification.product.repository.ProductUserNotificationHistoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductUserNotificationHistoryRepositoryUnitTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProductUserNotificationHistoryRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAll() {
        // given
        List<ProductUserNotificationHistory> histories = ProductUserNotificationHistoryBuilder.buildList();

        // when
        repository.saveAll(histories);

        // then
        verify(jdbcTemplate, times(1)).batchUpdate(
                eq("INSERT INTO productUserNotificationHistory (productId, userId, reStockRound, sentAt)" +
                        "VALUES (?, ?, ?, ?)"),
                eq(histories),
                eq(histories.size()),
                any());
    }
}
