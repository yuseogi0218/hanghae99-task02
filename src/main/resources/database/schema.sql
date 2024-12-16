DROP TABLE IF EXISTS `ProductUserNotification` CASCADE;
DROP TABLE IF EXISTS `ProductUserNotificationHistory` CASCADE;
DROP TABLE IF EXISTS `ProductNotificationHistory` CASCADE;
DROP TABLE IF EXISTS `Product` CASCADE;

CREATE TABLE `Product`
(
    `id`           BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    `reStockRound` INT NOT NULL,
    `stockStatus`  ENUM ('IN_STOCK', 'OUT_OF_STOCK') NOT NULL
);

CREATE TABLE `ProductUserNotification`
(
    `productId`  BIGINT(20)  NOT NULL,
    `userId`     BIGINT(20)  NOT NULL,
    `isActivate` CHAR(1)     NOT NULL,
    `createdAt`  DATETIME(6) NOT NULL,
    `updatedAt`  DATETIME(6) NOT NULL,

    PRIMARY KEY (productId, userId),
    FOREIGN KEY (`productId`) REFERENCES Product(`id`),

    CHECK ( `isActivate` IN ('Y', 'N'))
);

CREATE TABLE `productUserNotificationHistory`
(
    `id` BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    `productId` BIGINT(20) NOT NULL,
    `userId` BIGINT(20) NOT NULL,
    `reStockRound` INT NOT NULL,
    `sentAt` DATETIME(6) NOT NULL,

    FOREIGN KEY (`productId`) REFERENCES Product(`id`)
);

CREATE TABLE `ProductNotificationHistory`
(
    `id`                        BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    `productId`                 BIGINT(20) NOT NULL,
    `lastSentUserId`            BIGINT(20) NULL,
    `reStockRound`              INT NOT NULL,
    `reStockNotificationStatus` ENUM ('CANCELED_BY_ERROR', 'CANCELED_BY_SOLD_OUT', 'COMPLETED', 'IN_PROGRESS') NOT NULL,

    FOREIGN KEY (`productId`) REFERENCES Product(`id`)
);