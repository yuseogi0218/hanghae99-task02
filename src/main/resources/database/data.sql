INSERT INTO Product(id, reStockRound, stockStatus) VALUES(1, 2, 'IN_STOCK');
INSERT INTO Product(id, reStockRound, stockStatus) VALUES(2, 1, 'OUT_OF_STOCK');
INSERT INTO Product(id, reStockRound, stockStatus) VALUES(3, 1, 'IN_STOCK');

INSERT INTO ProductNotificationHistory(id, productId, reStockRound, reStockNotificationStatus, lastSentUserId) VALUES(1, 1, 1, 'COMPLETED', 3500);
INSERT INTO ProductNotificationHistory(id, productId, reStockRound, reStockNotificationStatus, lastSentUserId) VALUES(2, 1, 2, 'COMPLETED', 3500);

INSERT INTO ProductNotificationHistory(id, productId, reStockRound, reStockNotificationStatus, lastSentUserId) VALUES(3, 2, 1, 'CANCELED_BY_SOLD_OUT', 750);

INSERT INTO ProductNotificationHistory(id, productId, reStockRound, reStockNotificationStatus, lastSentUserId) VALUES(4, 3, 1, 'CANCELED_BY_ERROR', 250);
