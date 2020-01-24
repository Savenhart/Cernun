CREATE DATABASE IF NOT EXISTS simulife;
USE simulife;

CREATE TABLE IF NOT EXISTS world
(
    ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL UNIQUE,
    seed INT NOT NULL
)ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS user
(
    ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL UNIQUE,
    pseudo VARCHAR(10) NOT NULL UNIQUE,
    avatar BLOB NOT NULL,
    password VARCHAR(64) NOT NULL
)ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS droit
(
    userID INT NOT NULL,
    worldID INT NOT NULL,
    isadmin INT(1) NOT NULL DEFAULT 0,
    FOREIGN KEY (userID) REFERENCES user(ID),
    FOREIGN KEY (worldID) REFERENCES world(ID)
)ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS creature
(
    ID INT NOT NULL AUTO_INCREMENT,
    discriminant TIMESTAMP NOT NULL,
    name VARCHAR(20) NOT NULL,
    speed INT NOT NULL,
    energy INT NOT NULL,
    energyMax INT NOT NULL,
    perception INT NOT NULL,
    mass INT NOT NULL,
    diet INT NOT NULL,
    ratioSeaMountain INT NOT NULL,
    posX INT NOT NULL,
    posY INT NOT NULL,
    CONSTRAINT pkcreature PRIMARY KEY (ID, discriminant)
)ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS appartenance
(
    userID INT NOT NULL,
    worldID INT NOT NULL,
    creatureID INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES user(ID),
    FOREIGN KEY (worldID) REFERENCES world(ID),
    FOREIGN KEY (creatureID) REFERENCES creature(ID)
)ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS cell
(
    worldID INT NOT NULL,
    posX INT NOT NULL,
    posY INT NOT NULL,
    biome VARCHAR(20) NOT NULL,
    CONSTRAINT pkcell PRIMARY KEY (worldID, posX, posY),
    FOREIGN KEY (worldID) REFERENCES world(ID)
)ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS food
(
    worldID INT NOT NULL,
    posX INT NOT NULL,
    posY INT NOT NULL,
    energy INT NOT NULL,
    ismeat INT(1) NOT NULL,
    picture BLOB NOT NULL,
    CONSTRAINT pkfood PRIMARY KEY (worldID, posX, posY),
    FOREIGN KEY (worldID) REFERENCES world(ID)
)ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;
