--�Ǹ� ���̺�, ������� : 3
CREATE TABLE SALE (
	`NO` INT(10) UNSIGNED NOT NULL,
	`MAIL` VARCHAR(50) NOT NULL,
	`ID` CHAR(10) NOT NULL,
	`CNT` INT(10) NULL DEFAULT NULL,
	`TOTAL` INT(10) NULL DEFAULT NULL,
	`RENT_DATE` DATETIME NULL DEFAULT NULL,
	`TURN_DATE` DATETIME NULL DEFAULT NULL,
	`IS_TURN` TINYINT(1) NULL DEFAULT NULL,
	PRIMARY KEY (`NO`),
	INDEX `FK_sale_customer` (`MAIL`),
	INDEX `FK_sale_product` (`ID`),
	CONSTRAINT `FK_sale_customer` FOREIGN KEY (`MAIL`) REFERENCES `customer` (`MAIL`),
	CONSTRAINT `FK_sale_product` FOREIGN KEY (`ID`) REFERENCES `product` (`ID`)
);