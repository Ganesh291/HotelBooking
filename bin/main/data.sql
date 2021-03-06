DROP TABLE IF EXISTS HOTELOWNER;
CREATE TABLE HOTELOWNER (
ID INT AUTO_INCREMENT PRIMARY KEY,
ROOM_NO VARCHAR(240) UNIQUE NOT NULL,
STATUS VARCHAR(240) NOT NULL,
CUSTOMER_ID INT,
AIR_CONDITIONER VARCHAR(240) NOT NULL,
WIFI VARCHAR(240) NOT NULL,
DOUBLE_COT VARCHAR(240) NOT NULL,
PRICE INT NOT NULL
);
INSERT INTO HOTELOWNER (ROOM_NO,STATUS,CUSTOMER_ID,AIR_CONDITIONER,WIFI,DOUBLE_COT,PRICE) VALUES ('R101','AVAILABLE',NULL,'NO','NO','YES',1500);
INSERT INTO HOTELOWNER (ROOM_NO,STATUS,CUSTOMER_ID,AIR_CONDITIONER,WIFI,DOUBLE_COT,PRICE) VALUES ('R102','AVAILABLE',NULL,'NO','YES','NO',1600);
INSERT INTO HOTELOWNER (ROOM_NO,STATUS,CUSTOMER_ID,AIR_CONDITIONER,WIFI,DOUBLE_COT,PRICE) VALUES ('R103','UNAVAILABLE',1,'NO','YES','YES',1800);
INSERT INTO HOTELOWNER (ROOM_NO,STATUS,CUSTOMER_ID,AIR_CONDITIONER,WIFI,DOUBLE_COT,PRICE) VALUES ('R104','AVAILABLE',NULL,'YES','NO','NO',1700);
INSERT INTO HOTELOWNER (ROOM_NO,STATUS,CUSTOMER_ID,AIR_CONDITIONER,WIFI,DOUBLE_COT,PRICE) VALUES ('R105','AVAILABLE',NULL,'YES','NO','YES',1900);
INSERT INTO HOTELOWNER (ROOM_NO,STATUS,CUSTOMER_ID,AIR_CONDITIONER,WIFI,DOUBLE_COT,PRICE) VALUES ('R106','UNAVAILABLE',2,'YES','YES','NO',1900);
INSERT INTO HOTELOWNER (ROOM_NO,STATUS,CUSTOMER_ID,AIR_CONDITIONER,WIFI,DOUBLE_COT,PRICE) VALUES ('R107','AVAILABLE',NULL,'YES','YES','YES',2000);
INSERT INTO HOTELOWNER (ROOM_NO,STATUS,CUSTOMER_ID,AIR_CONDITIONER,WIFI,DOUBLE_COT,PRICE) VALUES ('R108','AVAILABLE',NULL,'NO','NO','NO',1500);
COMMIT;

DROP TABLE IF EXISTS CUSTOMER;
CREATE TABLE CUSTOMER(
CUSTOMER_ID INT AUTO_INCREMENT PRIMARY KEY,
CUSTOMER_NAME VARCHAR(240) NOT NULL,
ROOM_NO VARCHAR(240),
AGE INT NOT NULL,
CITY VARCHAR(240) NOT NULL,
STATE VARCHAR(240) NOT NULL,
CURRENT_STATUS VARCHAR(240) NOT NULL
);
INSERT INTO CUSTOMER (CUSTOMER_NAME,ROOM_NO,AGE,CITY,STATE,CURRENT_STATUS) VALUES ('STEVE','R103',23,'CHENNAI','TAMILNADU','IN');
INSERT INTO CUSTOMER (CUSTOMER_NAME,ROOM_NO,AGE,CITY,STATE,CURRENT_STATUS) VALUES ('THOR','R106',24,'KANCHEPURAM','TAMILNADU','IN');
INSERT INTO CUSTOMER (CUSTOMER_NAME,ROOM_NO,AGE,CITY,STATE,CURRENT_STATUS) VALUES ('NATASHA',NULL,22,'MADURAI','TAMILNADU','OUT');
COMMIT;