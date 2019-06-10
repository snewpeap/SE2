-- MySQL Script generated by MySQL Workbench
-- Thu Jun  6 20:22:52 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cinema
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinema` DEFAULT CHARACTER SET utf8;
USE `cinema`;

-- -----------------------------------------------------
-- Table `cinema`.`hall`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`hall`;

CREATE TABLE IF NOT EXISTS `cinema`.`hall`
(
    `id`      INT(11)          NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(10)      NOT NULL,
    `column`  INT(10) UNSIGNED NOT NULL,
    `row`     INT(10) UNSIGNED NOT NULL,
    `size`    TINYINT(4)       NOT NULL,
    `is_Imax` TINYINT(4)       NOT NULL,
    `is3D`    TINYINT(4)       NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`movie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`movie`;

CREATE TABLE IF NOT EXISTS `cinema`.`movie`
(
    `id`            INT(11)          NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(45)      NOT NULL,
    `poster`        VARCHAR(255)     NULL     DEFAULT NULL,
    `director`      VARCHAR(45)      NOT NULL,
    `screen_writer` VARCHAR(45)      NOT NULL,
    `starring`      VARCHAR(45)      NOT NULL,
    `type`          VARCHAR(45)      NOT NULL,
    `country`       VARCHAR(45)      NOT NULL,
    `language`      VARCHAR(45)      NOT NULL,
    `duration`      INT(10) UNSIGNED NOT NULL,
    `start_date`    DATE             NOT NULL,
    `description`   TEXT             NOT NULL,
    `status`        TINYINT(4)       NOT NULL DEFAULT '1',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`arrangement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`arrangement`;

CREATE TABLE IF NOT EXISTS `cinema`.`arrangement`
(
    `id`           INT(11)        NOT NULL AUTO_INCREMENT,
    `start_time`   DATE           NOT NULL,
    `end_time`     DATE           NOT NULL,
    `fare`         FLOAT UNSIGNED NOT NULL,
    `hall_id`      INT(11)        NOT NULL,
    `movie_id`     INT(11)        NOT NULL,
    `visible_date` DATE           NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_arrangement_hall1_idx` (`hall_id` ASC),
    INDEX `fk_arrangement_movie1_idx` (`movie_id` ASC),
    CONSTRAINT `fk_arrangement_hall1`
        FOREIGN KEY (`hall_id`)
            REFERENCES `cinema`.`hall` (`id`)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT `fk_arrangement_movie1`
        FOREIGN KEY (`movie_id`)
            REFERENCES `cinema`.`movie` (`id`)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`seat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`seat`;

CREATE TABLE IF NOT EXISTS `cinema`.`seat`
(
    `id`      INT(11) NOT NULL AUTO_INCREMENT,
    `column`  INT(11) NOT NULL,
    `row`     INT(11) NOT NULL,
    `hall_id` INT(11) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_seat_hall1_idx` (`hall_id` ASC),
    CONSTRAINT `fk_seat_hall1`
        FOREIGN KEY (`hall_id`)
            REFERENCES `cinema`.`hall` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`arrangement_seat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`arrangement_seat`;

CREATE TABLE IF NOT EXISTS `cinema`.`arrangement_seat`
(
    `is_locked`      TINYINT(4) NOT NULL DEFAULT '0',
    `arrangement_id` INT(11)    NOT NULL,
    `seat_id`        INT(11)    NOT NULL,
    INDEX `fk_arrangement_seat_arrangement1_idx` (`arrangement_id` ASC),
    INDEX `fk_arrangement_seat_seat1_idx` (`seat_id` ASC),
    CONSTRAINT `fk_arrangement_seat_arrangement1`
        FOREIGN KEY (`arrangement_id`)
            REFERENCES `cinema`.`arrangement` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_arrangement_seat_hall1`
        FOREIGN KEY (`seat_id`)
            REFERENCES `cinema`.`seat` (`id`)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`promotion`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`promotion`;

CREATE TABLE IF NOT EXISTS `cinema`.`promotion`
(
    `id`                INT(11)          NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(45)      NOT NULL,
    `description`       TEXT             NOT NULL,
    `start_time`        DATE             NOT NULL,
    `end_time`          DATE             NOT NULL,
    `specify_movies`    TINYINT(4)       NOT NULL DEFAULT '0',
    `target_amount`     FLOAT UNSIGNED   NOT NULL,
    `coupon_amount`     FLOAT UNSIGNED   NOT NULL,
    `coupon_expiration` INT(10) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`user`;

CREATE TABLE IF NOT EXISTS `cinema`.`user`
(
    `id`       INT(11)     NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(20) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARACTER SET = utf8;

insert into cinema.user(name, password)
values ('root', 'root'),
       ('manager', 'manager'),
       ('staff', 'staff'),
       ('audience', 'audience');

-- -----------------------------------------------------
-- Table `cinema`.`coupon`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`coupon`;

CREATE TABLE IF NOT EXISTS `cinema`.`coupon`
(
    `id`           INT(11) NOT NULL AUTO_INCREMENT,
    `end_time`     DATE    NOT NULL,
    `promotion_id` INT(11) NOT NULL,
    `user_id`      INT(11) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_coupon_promotion1_idx` (`promotion_id` ASC),
    INDEX `fk_coupon_user1_idx` (`user_id` ASC),
    CONSTRAINT `fk_coupon_promotion1`
        FOREIGN KEY (`promotion_id`)
            REFERENCES `cinema`.`promotion` (`id`)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT `fk_coupon_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `cinema`.`user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`movie_like`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`movie_like`;

CREATE TABLE IF NOT EXISTS `cinema`.`movie_like`
(
    `user_id`  INT(11) NOT NULL,
    `movie_id` INT(11) NOT NULL,
    `completeTime`     DATE    NOT NULL,
    INDEX `fk_user_has_movie_movie1_idx` (`movie_id` ASC),
    INDEX `fk_user_has_movie_user1_idx` (`user_id` ASC),
    CONSTRAINT `fk_user_has_movie_movie1`
        FOREIGN KEY (`movie_id`)
            REFERENCES `cinema`.`movie` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_user_has_movie_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `cinema`.`user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`recharge_record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`recharge_record`;

CREATE TABLE IF NOT EXISTS `cinema`.`recharge_record`
(
    `id`              INT(11)        NOT NULL,
    `original_amount` FLOAT UNSIGNED NOT NULL,
    `discount_amount` FLOAT          NOT NULL,
    `completeTime`            DATE           NOT NULL,
    `user_id`         INT(11)        NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_recharge_record_user1_idx` (`user_id` ASC),
    CONSTRAINT `fk_recharge_record_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `cinema`.`user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`refund_strategy`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`refund_strategy`;

CREATE TABLE IF NOT EXISTS `cinema`.`refund_strategy`
(
    `day`        INT(11)        NOT NULL,
    `refundable` TINYINT(4)     NOT NULL DEFAULT '1',
    `percentage` FLOAT UNSIGNED NOT NULL,
    PRIMARY KEY (`day`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `cinema`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`order`;

CREATE TABLE IF NOT EXISTS `cinema`.`order`
(
    `id`              BIGINT(20)     NOT NULL,
    `real_amount`     FLOAT UNSIGNED NOT NULL,
    `original_amount` FLOAT UNSIGNED NOT NULL,
    `completeTime`            DATE           NOT NULL,
    `use_VIPcard`     TINYINT(4)     NOT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `cinema`.`tickets`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`tickets`;

CREATE TABLE IF NOT EXISTS `cinema`.`tickets`
(
    `id`             INT(11)        NOT NULL AUTO_INCREMENT,
    `user_id`        INT(11)        NOT NULL,
    `arrangement_id` INT(11)        NOT NULL,
    `seat_id`        INT(11)        NOT NULL,
    `completeTime`           DATE           NOT NULL,
    `status`         TINYINT(4)     NOT NULL,
    `real_amount`    FLOAT UNSIGNED NOT NULL,
    `orderID`        BIGINT(20)     NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_tickets_user1_idx` (`user_id` ASC),
    INDEX `fk_tickets_arrangement1_idx` (`arrangement_id` ASC),
    INDEX `fk_tickets_seat1_idx` (`seat_id` ASC),
    CONSTRAINT `fk_tickets_arrangement1`
        FOREIGN KEY (`arrangement_id`)
            REFERENCES `cinema`.`arrangement` (`id`)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT `fk_tickets_seat1`
        FOREIGN KEY (`seat_id`)
            REFERENCES `cinema`.`seat` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_tickets_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `cinema`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT `fk_tickets_order1`
        FOREIGN KEY (`orderID`)
            REFERENCES `cinema`.`order` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`vipcard`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`vipcard`;

CREATE TABLE IF NOT EXISTS `cinema`.`vipcard`
(
    `balance` FLOAT UNSIGNED NOT NULL DEFAULT '0',
    `user_id` INT(11)        NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `fk_vipcard_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `cinema`.`user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`vipcard_recharge_reduction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`vipcard_recharge_reduction`;

CREATE TABLE IF NOT EXISTS `cinema`.`vipcard_recharge_reduction`
(
    `target_amount`   INT(10) UNSIGNED NOT NULL,
    `discount_amount` FLOAT            NOT NULL,
    PRIMARY KEY (`target_amount`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`promotion_has_movie`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`promotion_has_movie`;

CREATE TABLE IF NOT EXISTS `cinema`.`promotion_has_movie`
(
    `promotion_id` INT(11) NOT NULL,
    `movie_id`     INT(11) NOT NULL,
    PRIMARY KEY (`promotion_id`, `movie_id`),
    INDEX `fk_promotion_has_movie_movie1_idx` (`movie_id` ASC),
    INDEX `fk_promotion_has_movie_promotion1_idx` (`promotion_id` ASC),
    CONSTRAINT `fk_promotion_has_movie_promotion1`
        FOREIGN KEY (`promotion_id`)
            REFERENCES `cinema`.`promotion` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_promotion_has_movie_movie1`
        FOREIGN KEY (`movie_id`)
            REFERENCES `cinema`.`movie` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `cinema`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`role`;

CREATE TABLE IF NOT EXISTS `cinema`.`role`
(
    `id`        INT         NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(16) NOT NULL DEFAULT 'audience',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
insert into cinema.role
values (1, 'audience'),
       (2, 'staff'),
       (3, 'manager'),
       (4, 'root');


-- -----------------------------------------------------
-- Table `cinema`.`user_has_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cinema`.`user_has_role`;

CREATE TABLE IF NOT EXISTS `cinema`.`user_has_role`
(
    `user_id` INT(11) NOT NULL,
    `role_id` INT     NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    INDEX `fk_user_has_role_role1_idx` (`role_id` ASC),
    INDEX `fk_user_has_role_user1_idx` (`user_id` ASC),
    CONSTRAINT `fk_user_has_role_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `cinema`.`user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_user_has_role_role1`
        FOREIGN KEY (`role_id`)
            REFERENCES `cinema`.`role` (`id`)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE or replace
    ALGORITHM = UNDEFINED
    DEFINER = `root`@`localhost`
    SQL SECURITY DEFINER
    VIEW `cinema`.`staff_view` AS
SELECT `cinema`.`user`.`id`       AS `id`,
       `cinema`.`user`.`name`     AS `name`,
       `cinema`.`user`.`password` AS `password`
FROM (`cinema`.`user`
         JOIN (SELECT `uhr`.`user_id` AS `user_id`
               FROM (`cinema`.`user_has_role` `uhr`
                        LEFT JOIN `cinema`.`role` ON ((`cinema`.`role`.`id` = `uhr`.`role_id`)))
               WHERE (`cinema`.`role`.`role_name` = 'staff')) `r` ON ((`cinema`.`user`.`id` = `r`.`user_id`)))
ORDER BY `cinema`.`user`.`id`;

CREATE or replace VIEW `manager_view` AS
select u.id                        as id,
       u.name                      as name,
       u.password                  as password,
       r.r_name like binary 'root' as is_root
from cinema.user u
         inner join
     (select ro.role_name as r_name,
             uhr.user_id  as uid
      from cinema.role ro
               right join
           cinema.user_has_role uhr
           on ro.id = uhr.role_id
      where ro.role_name = 'manager'
         or ro.role_name = 'root'
     ) r
     on u.id = r.uid
order by id;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
