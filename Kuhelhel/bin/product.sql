--��ǰ ���̺�, ������� : 2
CREATE TABLE PRODUCT (
	`ID` CHAR(10) NOT NULL,
	`CATEGORY` VARCHAR(10) NULL DEFAULT NULL,
	`NM` VARCHAR(50) NULL DEFAULT NULL,
	`PUBLICSHER` VARCHAR(50) NULL DEFAULT NULL,
	`PRICE` INT(10) NULL DEFAULT NULL,
	`COUNT` INT(10) NULL DEFAULT NULL,
	PRIMARY KEY (`ID`)
);