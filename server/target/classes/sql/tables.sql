-- -----------------------------------------------------
-- Table `fulmer`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fulmer`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `surname` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(45) NULL DEFAULT NULL,
  `role` ENUM('ADMIN', 'CLIENT') NOT NULL DEFAULT 'CLIENT',
  `email` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `fulmer`.`company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fulmer`.`company` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `address` VARCHAR(50) NOT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `phone_number` VARCHAR(45) NULL DEFAULT NULL,
  `id_user` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `fulmer`.`dollar_rate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fulmer`.`dollar_rate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `EUR` DOUBLE NOT NULL,
  `BYN` DOUBLE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `fulmer`.`report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fulmer`.`report` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_company` INT NOT NULL,
  `id_dollar_rate` INT NOT NULL,
  `date` DATE NOT NULL,
  `x1` DOUBLE NOT NULL,
  `x2` DOUBLE NOT NULL,
  `x3` DOUBLE NOT NULL,
  `x4` DOUBLE NOT NULL,
  `x5` DOUBLE NOT NULL,
  `x6` DOUBLE NOT NULL,
  `x7` DOUBLE NOT NULL,
  `x8` DOUBLE NOT NULL,
  `x9` DOUBLE NOT NULL,
  `H` DOUBLE NULL DEFAULT NULL,
  `currency` ENUM('USD', 'EUR', 'BYN') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;

-- -----------------------------------------------------
-- Table `fulmer`.`user_report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `fulmer`.`user_report` (
  `id_user` INT NOT NULL,
  `id_report` INT NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET utf8;
