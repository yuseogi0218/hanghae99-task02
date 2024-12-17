package hanghae99.reboot.notification.product.repository;

import hanghae99.reboot.notification.product.domain.ProductUserNotificationHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductUserNotificationHistoryRepositoryImpl implements ProductUserNotificationHistoryRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAll(List<ProductUserNotificationHistory> productUserNotificationHistories) {
        String sql = "INSERT INTO productUserNotificationHistory (productId, userId, reStockRound, sentAt)" +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                productUserNotificationHistories,
                productUserNotificationHistories.size(),
                (PreparedStatement ps, ProductUserNotificationHistory punh) -> {
                    ps.setLong(1, punh.getProduct().getId());
                    ps.setLong(2, punh.getUserId());
                    ps.setInt(3, punh.getReStockRound());
                    ps.setTimestamp(4, Timestamp.valueOf(punh.getSentAt()));
                });
    }
}
