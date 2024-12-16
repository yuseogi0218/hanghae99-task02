DROP TABLE IF EXISTS `ProductUserNotification` CASCADE;
DROP TABLE IF EXISTS `ProductUserNotificationHistory` CASCADE;
DROP TABLE IF EXISTS `ProductNotificationHistory` CASCADE;
DROP TABLE IF EXISTS `Product` CASCADE;

CREATE TABLE `Product`
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY,
    `reStockRound` INT NOT NULL,
    `stockStatus`  ENUM ('IN_STOCK', 'OUT_OF_STOCK') NOT NULL
);

CREATE TABLE `ProductUserNotification`
(
    `productId`  BIGINT  NOT NULL,
    `userId`     BIGINT  NOT NULL,
    `isActivate` CHAR    NOT NULL,
    `createdAt`  DATETIME NOT NULL,
    `updatedAt`  DATETIME NOT NULL,

    PRIMARY KEY (productId, userId),
    FOREIGN KEY (`productId`) REFERENCES Product(`id`),

    CHECK ( `isActivate` IN ('Y', 'N'))
);

CREATE TABLE `productUserNotificationHistory`
(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `productId` BIGINT NOT NULL,
    `userId` BIGINT NOT NULL,
    `reStockRound` INT NOT NULL,
    `sentAt` DATETIME NOT NULL,

    FOREIGN KEY (`productId`) REFERENCES Product(`id`)
);

CREATE TABLE `ProductNotificationHistory`
(
    `id`                        BIGINT AUTO_INCREMENT PRIMARY KEY,
    `productId`                 BIGINT NOT NULL,
    `lastSendUserId`            BIGINT NOT NULL,
    `reStockRound`              INT NOT NULL,
    `reStockNotificationStatus` ENUM ('CANCELED_BY_ERROR', 'CANCELED_BY_SOLD_OUT', 'COMPLETED', 'IN_PROGRESS') NOT NULL,

    FOREIGN KEY (`productId`) REFERENCES Product(`id`)
);