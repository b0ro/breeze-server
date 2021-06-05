CREATE TABLE `peak_flow`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `value`      INT             NOT NULL,
    `checked_at` TIMESTAMP       NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

CREATE INDEX peak_flow_checked_at ON peak_flow (checked_at);
