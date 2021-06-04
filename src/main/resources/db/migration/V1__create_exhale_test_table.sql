CREATE TABLE `exhale_test`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    lung_capacity INT             NOT NULL,
    checked_at    TIMESTAMP       NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

CREATE INDEX lung_capacity_created_at ON exhale_test (checked_at);
