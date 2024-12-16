INSERT INTO Product(id, reStockRound, stockStatus) VALUES(1, 2, 'IN_STOCK');
INSERT INTO Product(id, reStockRound, stockStatus) VALUES(2, 1, 'IN_STOCK');

INSERT INTO ProductNotificationHistory(id, productId, reStockRound, reStockNotificationStatus, lastSentUserId) VALUES(1, 1, 1, 'COMPLETED', 3500);
INSERT INTO ProductNotificationHistory(id, productId, reStockRound, reStockNotificationStatus, lastSentUserId) VALUES(2, 1, 2, 'IN_PROGRESS', 500);

INSERT INTO ProductNotificationHistory(id, productId, reStockRound, reStockNotificationStatus, lastSentUserId) VALUES(3, 2, 1, 'COMPLETED', 500);
