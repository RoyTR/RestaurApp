SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema restaurapp_dev
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `restaurapp_dev` DEFAULT CHARACTER SET latin1 ;
USE `restaurapp_dev` ;

-- -----------------------------------------------------
-- Table `restaurapp_dev`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`usuario` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `apellidos` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `username` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `email` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `facebook_id` BIGINT(20) NULL DEFAULT NULL,
  `password` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `is_admin` ENUM('Yes','No') CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL DEFAULT 'No',
  `access_token` VARCHAR(40) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL DEFAULT NULL,
  `created_by` INT(10) UNSIGNED NOT NULL,
  `updated_by` INT(10) UNSIGNED NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `usuario_created_by_foreign` (`created_by` ASC),
  INDEX `usuario_updated_by_foreign` (`updated_by` ASC),
  CONSTRAINT `usuario_created_by_foreign`
    FOREIGN KEY (`created_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`),
  CONSTRAINT `usuario_updated_by_foreign`
    FOREIGN KEY (`updated_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`categoria` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `descripcion` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `created_by` INT(10) UNSIGNED NOT NULL,
  `updated_by` INT(10) UNSIGNED NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `categoria_created_by_foreign` (`created_by` ASC),
  INDEX `categoria_updated_by_foreign` (`updated_by` ASC),
  CONSTRAINT `categoria_created_by_foreign`
    FOREIGN KEY (`created_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`),
  CONSTRAINT `categoria_updated_by_foreign`
    FOREIGN KEY (`updated_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`foto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`foto` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `url` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `url_min` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `formato` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `created_by` INT(10) UNSIGNED NOT NULL,
  `updated_by` INT(10) UNSIGNED NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `foto_created_by_foreign` (`created_by` ASC),
  INDEX `foto_updated_by_foreign` (`updated_by` ASC),
  CONSTRAINT `foto_created_by_foreign`
    FOREIGN KEY (`created_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`),
  CONSTRAINT `foto_updated_by_foreign`
    FOREIGN KEY (`updated_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`grupo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`grupo` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `created_by` INT(10) UNSIGNED NOT NULL,
  `updated_by` INT(10) UNSIGNED NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `grupo_created_by_foreign` (`created_by` ASC),
  INDEX `grupo_updated_by_foreign` (`updated_by` ASC),
  CONSTRAINT `grupo_created_by_foreign`
    FOREIGN KEY (`created_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`),
  CONSTRAINT `grupo_updated_by_foreign`
    FOREIGN KEY (`updated_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`grupo_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`grupo_usuario` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `grupo_id` INT(10) UNSIGNED NOT NULL,
  `usuario_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `grupo_usuario_grupo_id_foreign` (`grupo_id` ASC),
  INDEX `grupo_usuario_usuario_id_foreign` (`usuario_id` ASC),
  CONSTRAINT `grupo_usuario_grupo_id_foreign`
    FOREIGN KEY (`grupo_id`)
    REFERENCES `restaurapp_dev`.`grupo` (`id`),
  CONSTRAINT `grupo_usuario_usuario_id_foreign`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`migrations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`migrations` (
  `migration` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `batch` INT(11) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`preferencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`preferencia` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `usuario_id` INT(10) UNSIGNED NOT NULL,
  `categoria_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `preferencia_usuario_id_foreign` (`usuario_id` ASC),
  INDEX `preferencia_categoria_id_foreign` (`categoria_id` ASC),
  CONSTRAINT `preferencia_categoria_id_foreign`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `restaurapp_dev`.`categoria` (`id`),
  CONSTRAINT `preferencia_usuario_id_foreign`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`restaurante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`restaurante` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(64) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `latitud` DECIMAL(10,2) NULL DEFAULT NULL,
  `longitud` DECIMAL(10,2) NULL DEFAULT NULL,
  `descripcion` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `foto_id` INT(10) UNSIGNED NULL DEFAULT NULL,
  `puntuacion_total` DECIMAL(10,2) NULL DEFAULT NULL,
  `created_by` INT(10) UNSIGNED NOT NULL,
  `updated_by` INT(10) UNSIGNED NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `restaurante_foto_id_foreign` (`foto_id` ASC),
  INDEX `restaurante_created_by_foreign` (`created_by` ASC),
  INDEX `restaurante_updated_by_foreign` (`updated_by` ASC),
  CONSTRAINT `restaurante_created_by_foreign`
    FOREIGN KEY (`created_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`),
  CONSTRAINT `restaurante_foto_id_foreign`
    FOREIGN KEY (`foto_id`)
    REFERENCES `restaurapp_dev`.`foto` (`id`),
  CONSTRAINT `restaurante_updated_by_foreign`
    FOREIGN KEY (`updated_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`recomendacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`recomendacion` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `comentario` VARCHAR(255) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
  `puntuacion` INT(11) NOT NULL,
  `usuario_id` INT(10) UNSIGNED NOT NULL,
  `restaurante_id` INT(10) UNSIGNED NOT NULL,
  `grupo_id` INT(10) UNSIGNED NOT NULL,
  `created_by` INT(10) UNSIGNED NOT NULL,
  `updated_by` INT(10) UNSIGNED NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deleted_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `recomendacion_usuario_id_foreign` (`usuario_id` ASC),
  INDEX `recomendacion_restaurante_id_foreign` (`restaurante_id` ASC),
  INDEX `recomendacion_grupo_id_foreign` (`grupo_id` ASC),
  INDEX `recomendacion_created_by_foreign` (`created_by` ASC),
  INDEX `recomendacion_updated_by_foreign` (`updated_by` ASC),
  CONSTRAINT `recomendacion_created_by_foreign`
    FOREIGN KEY (`created_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`),
  CONSTRAINT `recomendacion_grupo_id_foreign`
    FOREIGN KEY (`grupo_id`)
    REFERENCES `restaurapp_dev`.`grupo` (`id`),
  CONSTRAINT `recomendacion_restaurante_id_foreign`
    FOREIGN KEY (`restaurante_id`)
    REFERENCES `restaurapp_dev`.`restaurante` (`id`),
  CONSTRAINT `recomendacion_updated_by_foreign`
    FOREIGN KEY (`updated_by`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`),
  CONSTRAINT `recomendacion_usuario_id_foreign`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `restaurapp_dev`.`usuario` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`recomendacion_foto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`recomendacion_foto` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `recomendacion_id` INT(10) UNSIGNED NOT NULL,
  `foto_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `recomendacion_foto_recomendacion_id_foreign` (`recomendacion_id` ASC),
  INDEX `recomendacion_foto_foto_id_foreign` (`foto_id` ASC),
  CONSTRAINT `recomendacion_foto_foto_id_foreign`
    FOREIGN KEY (`foto_id`)
    REFERENCES `restaurapp_dev`.`foto` (`id`),
  CONSTRAINT `recomendacion_foto_recomendacion_id_foreign`
    FOREIGN KEY (`recomendacion_id`)
    REFERENCES `restaurapp_dev`.`recomendacion` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `restaurapp_dev`.`restaurante_categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `restaurapp_dev`.`restaurante_categoria` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `restaurante_id` INT(10) UNSIGNED NOT NULL,
  `categoria_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `restaurante_categoria_restaurante_id_foreign` (`restaurante_id` ASC),
  INDEX `restaurante_categoria_categoria_id_foreign` (`categoria_id` ASC),
  CONSTRAINT `restaurante_categoria_categoria_id_foreign`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `restaurapp_dev`.`categoria` (`id`),
  CONSTRAINT `restaurante_categoria_restaurante_id_foreign`
    FOREIGN KEY (`restaurante_id`)
    REFERENCES `restaurapp_dev`.`restaurante` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
