-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema projektni_bp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projektni_bp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projektni_bp` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8mb3_unicode_ci;
USE `projektni_bp` ;

-- -----------------------------------------------------
-- Table `projektni_bp`.`KORISNIK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`KORISNIK` (
  `idKorisnika` INT NOT NULL AUTO_INCREMENT,
  `korisnickoIme` VARCHAR(45) NOT NULL,
  `ime` VARCHAR(45) NOT NULL,
  `prezime` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `lozinka` TEXT NOT NULL,
  `uloga` TINYINT(1) NOT NULL DEFAULT 0,
  `brojTelefona` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`idKorisnika`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`PRIKOLICA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`PRIKOLICA` (
  `idPrikolice` INT NOT NULL AUTO_INCREMENT,
  `vrsta` VARCHAR(45) NOT NULL,
  `nosivost` INT NOT NULL,
  `godinaProizvodnje` YEAR(4) NOT NULL,
  `registarskaOznaka` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`idPrikolice`),
  UNIQUE INDEX `registarskaOznaka_UNIQUE` (`registarskaOznaka` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`KAMION`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`KAMION` (
  `idKamiona` INT NOT NULL AUTO_INCREMENT,
  `tip` VARCHAR(45) NOT NULL,
  `marka` VARCHAR(45) NOT NULL,
  `konjskeSnage` INT NOT NULL,
  `idPrikolice` INT NULL,
  `vrstaGoriva` VARCHAR(20) NOT NULL,
  `godinaProizvodnje` YEAR(4) NOT NULL,
  `registarskaOznaka` VARCHAR(12) NOT NULL,
  `kilometraza` INT NOT NULL,
  PRIMARY KEY (`idKamiona`),
  INDEX `fk_KAMION_PRIKOLICA1_idx` (`idPrikolice` ASC) VISIBLE,
  UNIQUE INDEX `registarskaOznaka_UNIQUE` (`registarskaOznaka` ASC) VISIBLE,
  CONSTRAINT `fk_KAMION_PRIKOLICA1`
    FOREIGN KEY (`idPrikolice`)
    REFERENCES `projektni_bp`.`PRIKOLICA` (`idPrikolice`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`VOZAC`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`VOZAC` (
  `idKorisnika` INT NOT NULL,
  `idKamiona` INT NULL,
  `brojDozvole` VARCHAR(45) NOT NULL,
  `licenca` VARCHAR(45) NOT NULL,
  `dostupnost` TINYINT NOT NULL,
  PRIMARY KEY (`idKorisnika`),
  INDEX `fk_VOZAC_KAMION1_idx` (`idKamiona` ASC) VISIBLE,
  CONSTRAINT `fk_table1_KORISNIK`
    FOREIGN KEY (`idKorisnika`)
    REFERENCES `projektni_bp`.`KORISNIK` (`idKorisnika`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_VOZAC_KAMION1`
    FOREIGN KEY (`idKamiona`)
    REFERENCES `projektni_bp`.`KAMION` (`idKamiona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`DISPECER`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`DISPECER` (
  `idKorisnika` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idKorisnika`),
  CONSTRAINT `fk_table2_KORISNIK1`
    FOREIGN KEY (`idKorisnika`)
    REFERENCES `projektni_bp`.`KORISNIK` (`idKorisnika`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`PROBLEM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`PROBLEM` (
  `idProblem` INT NOT NULL AUTO_INCREMENT,
  `idKorisnika` INT NOT NULL,
  `tekstProblema` VARCHAR(100) NOT NULL,
  `datum` DATE NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idProblem`),
  INDEX `fk_PROBLEM_VOZAC1_idx` (`idKorisnika` ASC) VISIBLE,
  CONSTRAINT `fk_PROBLEM_VOZAC1`
    FOREIGN KEY (`idKorisnika`)
    REFERENCES `projektni_bp`.`VOZAC` (`idKorisnika`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`TURA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`TURA` (
  `idTure` INT NOT NULL AUTO_INCREMENT,
  `vrijemePolaska` DATETIME NOT NULL,
  `vrijemeDolaska` DATETIME NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `idVozaca` INT NOT NULL,
  `idDispecera` INT NOT NULL,
  `ukupnaCijenaTure` DECIMAL NOT NULL,
  PRIMARY KEY (`idTure`),
  INDEX `fk_TURA_VOZAC1_idx` (`idVozaca` ASC) VISIBLE,
  INDEX `fk_TURA_DISPECER1_idx` (`idDispecera` ASC) VISIBLE,
  CONSTRAINT `fk_TURA_VOZAC1`
    FOREIGN KEY (`idVozaca`)
    REFERENCES `projektni_bp`.`VOZAC` (`idKorisnika`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TURA_DISPECER1`
    FOREIGN KEY (`idDispecera`)
    REFERENCES `projektni_bp`.`DISPECER` (`idKorisnika`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`FIRMA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`FIRMA` (
  `idFirme` INT NOT NULL AUTO_INCREMENT,
  `naziv` VARCHAR(45) NOT NULL,
  `mejl` VARCHAR(45) NOT NULL,
  `ziroRacun` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`idFirme`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`TURA_FIRMA_IZVORISTA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`TURA_FIRMA_IZVORISTA` (
  `idTure` INT NOT NULL,
  `idFirme` INT NOT NULL,
  `vrijemeUtovara` DATETIME NOT NULL,
  PRIMARY KEY (`idTure`, `idFirme`),
  INDEX `fk_TURA_FIRMA_IZVORISTA_FIRMA1_idx` (`idFirme` ASC) VISIBLE,
  CONSTRAINT `fk_TURA_FIRMA_IZVORISTA_TURA1`
    FOREIGN KEY (`idTure`)
    REFERENCES `projektni_bp`.`TURA` (`idTure`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TURA_FIRMA_IZVORISTA_FIRMA1`
    FOREIGN KEY (`idFirme`)
    REFERENCES `projektni_bp`.`FIRMA` (`idFirme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`TURA_FIRMA_ODREDISTA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`TURA_FIRMA_ODREDISTA` (
  `idFirme` INT NOT NULL,
  `idTure` INT NOT NULL,
  `vrijemeIstovara` DATETIME NOT NULL,
  PRIMARY KEY (`idFirme`, `idTure`),
  INDEX `fk_TURA_FIRMA_ODREDISTA_TURA1_idx` (`idTure` ASC) VISIBLE,
  CONSTRAINT `fk_TURA_FIRMA_ODREDISTA_FIRMA1`
    FOREIGN KEY (`idFirme`)
    REFERENCES `projektni_bp`.`FIRMA` (`idFirme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TURA_FIRMA_ODREDISTA_TURA1`
    FOREIGN KEY (`idTure`)
    REFERENCES `projektni_bp`.`TURA` (`idTure`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`TOVAR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`TOVAR` (
  `idTovara` INT NOT NULL AUTO_INCREMENT,
  `opis` MEDIUMTEXT NOT NULL,
  `masa` INT NOT NULL,
  `opasan` TINYINT NOT NULL,
  `idTure_Izvorista` INT NOT NULL,
  `idFirme_Izvorista` INT NOT NULL,
  `idFirme_Odredista` INT NOT NULL,
  `idTure_Odredista` INT NOT NULL,
  PRIMARY KEY (`idTovara`),
  INDEX `fk_TOVAR_TURA_FIRMA_IZVORISTA1_idx` (`idTure_Izvorista` ASC, `idFirme_Izvorista` ASC) VISIBLE,
  INDEX `fk_TOVAR_TURA_FIRMA_ODREDISTA1_idx` (`idFirme_Odredista` ASC, `idTure_Odredista` ASC) VISIBLE,
  CONSTRAINT `fk_TOVAR_TURA_FIRMA_IZVORISTA1`
    FOREIGN KEY (`idTure_Izvorista` , `idFirme_Izvorista`)
    REFERENCES `projektni_bp`.`TURA_FIRMA_IZVORISTA` (`idTure` , `idFirme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TOVAR_TURA_FIRMA_ODREDISTA1`
    FOREIGN KEY (`idFirme_Odredista` , `idTure_Odredista`)
    REFERENCES `projektni_bp`.`TURA_FIRMA_ODREDISTA` (`idFirme` , `idTure`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`KOMENTAR`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`KOMENTAR` (
  `idKomentara` INT NOT NULL AUTO_INCREMENT,
  `idTure` INT NULL,
  `tekst` TINYTEXT NOT NULL,
  PRIMARY KEY (`idKomentara`),
  INDEX `fk_KOMENTAR_TURA1_idx` (`idTure` ASC) VISIBLE,
  CONSTRAINT `fk_KOMENTAR_TURA1`
    FOREIGN KEY (`idTure`)
    REFERENCES `projektni_bp`.`TURA` (`idTure`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`ODRZAVANJE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`ODRZAVANJE` (
  `idOdrzavanja` INT NOT NULL AUTO_INCREMENT,
  `datum` DATE NOT NULL,
  `opis` VARCHAR(45) NOT NULL,
  `KAMION_idKamiona` INT NULL,
  `PRIKOLICA_idPrikolice` INT NULL,
  PRIMARY KEY (`idOdrzavanja`),
  INDEX `fk_ODRZAVANJE_KAMION1_idx` (`KAMION_idKamiona` ASC) VISIBLE,
  INDEX `fk_ODRZAVANJE_PRIKOLICA1_idx` (`PRIKOLICA_idPrikolice` ASC) VISIBLE,
  CONSTRAINT `fk_ODRZAVANJE_KAMION1`
    FOREIGN KEY (`KAMION_idKamiona`)
    REFERENCES `projektni_bp`.`KAMION` (`idKamiona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ODRZAVANJE_PRIKOLICA1`
    FOREIGN KEY (`PRIKOLICA_idPrikolice`)
    REFERENCES `projektni_bp`.`PRIKOLICA` (`idPrikolice`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`ADRESA`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`ADRESA` (
  `idAdrese` INT NOT NULL AUTO_INCREMENT,
  `ulica` VARCHAR(100) NOT NULL,
  `broj` TINYINT(2) NOT NULL,
  `grad` VARCHAR(45) NOT NULL,
  `postanskiBroj` INT NOT NULL,
  `drzava` VARCHAR(45) NOT NULL,
  `idFirme` INT NOT NULL,
  PRIMARY KEY (`idAdrese`),
  INDEX `fk_ADRESA_FIRMA1_idx` (`idFirme` ASC) VISIBLE,
  CONSTRAINT `fk_ADRESA_FIRMA1`
    FOREIGN KEY (`idFirme`)
    REFERENCES `projektni_bp`.`FIRMA` (`idFirme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`DOKUMENT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`DOKUMENT` (
  `idDokumenta` INT NOT NULL AUTO_INCREMENT,
  `tekst` MEDIUMTEXT NOT NULL,
  `cijenaPrevoza` DECIMAL(10,2) NOT NULL,
  `idTovara` INT NOT NULL,
  PRIMARY KEY (`idDokumenta`),
  INDEX `fk_DOKUMENT_TOVAR1_idx` (`idTovara` ASC) VISIBLE,
  CONSTRAINT `fk_DOKUMENT_TOVAR1`
    FOREIGN KEY (`idTovara`)
    REFERENCES `projektni_bp`.`TOVAR` (`idTovara`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projektni_bp`.`TELEFON`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projektni_bp`.`TELEFON` (
  `brojTelefona` VARCHAR(20) NOT NULL,
  `fax` TINYINT NOT NULL,
  `idFirme` INT NOT NULL,
  PRIMARY KEY (`brojTelefona`, `fax`, `idFirme`),
  INDEX `fk_TELEFON_FIRMA1_idx` (`idFirme` ASC) VISIBLE,
  CONSTRAINT `fk_TELEFON_FIRMA1`
    FOREIGN KEY (`idFirme`)
    REFERENCES `projektni_bp`.`FIRMA` (`idFirme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
