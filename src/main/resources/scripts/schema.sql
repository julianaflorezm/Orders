CREATE TABLE USERS(
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    USERNAME VARCHAR NOT NULL,
    PASSWORD VARCHAR NOT NULL
);

CREATE TABLE PRODUCTS(
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    PRODUCTNAME VARCHAR NOT NULL,
    DESCRIPTION VARCHAR NOT NULL,
    BASEPRICE DECIMAL NOT NULL,
    TAXRATE DECIMAL NOT NULL,
    STATUS VARCHAR NOT NULL,
    INVENTORYQUANTITY INT NOT NULL
);

ALTER TABLE PRODUCTS ALTER COLUMN ID RESTART WITH 2;