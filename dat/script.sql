DROP TABLE IF EXISTS STORE_ITEM;
DROP TABLE IF EXISTS STORE;
DROP TABLE IF EXISTS FACTORY_ITEM;
DROP TABLE IF EXISTS FACTORY;
DROP TABLE IF EXISTS ADDRESS;
DROP TABLE IF EXISTS ITEM;
DROP TABLE IF EXISTS CATEGORY;
CREATE TABLE CATEGORY(
                         ID LONG GENERATED ALWAYS AS IDENTITY,
                         NAME VARCHAR(25) NOT NULL,
                         DESCRIPTION VARCHAR(250) NOT NULL,
                         PRIMARY KEY (ID)
);
CREATE TABLE ITEM (
                      ID LONG GENERATED ALWAYS AS IDENTITY,
                      CATEGORY_ID LONG NOT NULL,
                      NAME VARCHAR(25) NOT NULL,
                      WIDTH DECIMAL(10,2) NOT NULL,
                      HEIGHT DECIMAL(10,2) NOT NULL,
                      LENGTH DECIMAL(10,2) NOT NULL,
                      PRODUCTION_COST DECIMAL(15,2) NOT NULL,
                      SELLING_PRICE DECIMAL(15,2) NOT NULL,
                      PRIMARY KEY (ID),
                      FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(ID)
);
CREATE TABLE ADDRESS(
                        ID LONG GENERATED ALWAYS AS IDENTITY,
                        STREET VARCHAR(50) NOT NULL,
                        HOUSE_NUMBER VARCHAR(10) NOT NULL,
                        CITY VARCHAR(30) NOT NULL,
                        POSTAL_CODE INT NOT NULL,
                        PRIMARY KEY (ID)
);
CREATE TABLE FACTORY(
                        ID LONG GENERATED ALWAYS AS IDENTITY,
                        NAME VARCHAR(50) NOT NULL,
                        ADDRESS_ID LONG NOT NULL,
                        PRIMARY KEY (ID),
                        FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS(ID)
);
CREATE TABLE FACTORY_ITEM(
                             FACTORY_ID LONG NOT NULL,
                             ITEM_ID LONG NOT NULL,
                             PRIMARY KEY (FACTORY_ID, ITEM_ID),
                             FOREIGN KEY (FACTORY_ID) REFERENCES FACTORY(ID),
                             FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ID)
);
CREATE TABLE STORE(
                      ID LONG GENERATED ALWAYS AS IDENTITY,
                      NAME VARCHAR(50) NOT NULL,
                      WEB_ADDRESS VARCHAR(50) NOT NULL,
                      PRIMARY KEY (ID)
);
CREATE TABLE STORE_ITEM(
                           STORE_ID LONG NOT NULL,
                           ITEM_ID LONG NOT NULL,
                           PRIMARY KEY (STORE_ID, ITEM_ID),
                           FOREIGN KEY (STORE_ID) REFERENCES STORE(ID),
                           FOREIGN KEY (ITEM_ID) REFERENCES ITEM(ID)
);
INSERT INTO CATEGORY(NAME, DESCRIPTION) VALUES('Food', 'Category of edible items');
INSERT INTO ITEM(CATEGORY_ID, NAME, WIDTH, HEIGHT, LENGTH, PRODUCTION_COST,
                 SELLING_PRICE) VALUES(1, 'Sarma', 15.0, 5.0, 25.0, 10.0, 20.0);
INSERT INTO ADDRESS(STREET, HOUSE_NUMBER, CITY, POSTAL_CODE) VALUES('Ante
Starčevića', '32', 'Koprivnica', 48000);
INSERT INTO FACTORY(NAME, ADDRESS_ID) VALUES('Podravka', 1);
INSERT INTO FACTORY_ITEM(FACTORY_ID, ITEM_ID) VALUES(1, 1);
INSERT INTO STORE(NAME, WEB_ADDRESS) VALUES('Kupi sve', 'www.kupi-sve.hr');
INSERT INTO STORE_ITEM(STORE_ID, ITEM_ID) VALUES(1, 1);
