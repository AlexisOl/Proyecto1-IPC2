-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 01-09-2021 a las 11:08:47
-- Versión del servidor: 5.7.24
-- Versión de PHP: 7.4.21

CREATE USER 'AlexisOl'@'localhost' IDENTIFIED BY 'AlexisOl123!';
GRANT ALL PRIVILEGES ON *.* TO 'prueba2'@'localhost';
FLUSH PRIVILEGES;


SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_muebleria`
--
CREATE DATABASE IF NOT EXISTS `db_muebleria` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `db_muebleria`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `Cliente` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `nit` varchar(8) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `municipio` varchar(45) DEFAULT NULL,
  `departamento` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_factura`
--

CREATE TABLE `Detalle_factura` (
  `id` int(11) NOT NULL,
  `factura_id` int(11) NOT NULL,
  `ensamblar_mueble_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `devolucion`
--

CREATE TABLE `Devolucion` (
  `id` int(11) NOT NULL,
  `fecha_devolucion` date NOT NULL,
  `perdida` double NOT NULL,
  `detalle_factura_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ensamblar_mueble`
--

CREATE TABLE `Ensamblar_mueble` (
  `id` int(11) NOT NULL,
  `mueble_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `identificador` varchar(45) DEFAULT NULL,
  `fecha` date NOT NULL,
  `estado` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ensambla_pieza`
--

CREATE TABLE `Ensambla_pieza` (
  `id` int(11) NOT NULL,
  `mueble_id` int(11) NOT NULL,
  `pieza_id` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `Factura` (
  `id` int(11) NOT NULL,
  `codigo` varchar(45) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `costo` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mueble`
--

CREATE TABLE `Mueble` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `precio` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pieza`
--

CREATE TABLE `Pieza` (
  `id` int(11) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `costo` double NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `Usuario` (
  `id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `tipo` int(11) NOT NULL,
  `estado` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `Cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nit_UNIQUE` (`nit`);

--
-- Indices de la tabla `detalle_factura`
--
ALTER TABLE `Detalle_factura`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_detalle_factura_factura1_idx` (`factura_id`),
  ADD KEY `fk_detalle_factura_ensamblar_mueble1_idx` (`ensamblar_mueble_id`);

--
-- Indices de la tabla `devolucion`
--
ALTER TABLE `Devolucion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_devolucion_detalle_factura1_idx` (`detalle_factura_id`);

--
-- Indices de la tabla `ensamblar_mueble`
--
ALTER TABLE `Ensamblar_mueble`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `identificador_UNIQUE` (`identificador`),
  ADD KEY `fk_ENSAMBLAR_MUEBLE_USUARIO1_idx` (`usuario_id`),
  ADD KEY `fk_ENSAMBLAR_MUEBLE_MUEBLE1_idx` (`mueble_id`);

--
-- Indices de la tabla `ensambla_pieza`
--
ALTER TABLE `Ensambla_pieza`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ENSAMBLA_PIEZA_MUEBLE_idx` (`mueble_id`),
  ADD KEY `fk_ENSAMBLA_PIEZA_PIEZA1_idx` (`pieza_id`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `Factura`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codigo_UNIQUE` (`codigo`),
  ADD KEY `fk_FACTURA_CLIENTE1_idx` (`cliente_id`),
  ADD KEY `fk_FACTURA_USUARIO1_idx` (`usuario_id`);

--
-- Indices de la tabla `mueble`
--
ALTER TABLE `Mueble`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre_UNIQUE` (`nombre`);

--
-- Indices de la tabla `pieza`
--
ALTER TABLE `Pieza`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tipo_UNIQUE` (`tipo`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `Usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `Cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_factura`
--
ALTER TABLE `Detalle_factura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `devolucion`
--
ALTER TABLE `Devolucion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ensamblar_mueble`
--
ALTER TABLE `Ensamblar_mueble`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ensambla_pieza`
--
ALTER TABLE `Ensambla_pieza`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `Factura`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `mueble`
--
ALTER TABLE `Mueble`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pieza`
--
ALTER TABLE `Pieza`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `Usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_factura`
--
ALTER TABLE `Detalle_factura`
  ADD CONSTRAINT `fk_detalle_factura_ensamblar_mueble1` FOREIGN KEY (`ensamblar_mueble_id`) REFERENCES `Ensamblar_mueble` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_detalle_factura_factura1` FOREIGN KEY (`factura_id`) REFERENCES `Factura` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `devolucion`
--
ALTER TABLE `Devolucion`
  ADD CONSTRAINT `fk_devolucion_detalle_factura1` FOREIGN KEY (`detalle_factura_id`) REFERENCES `Detalle_factura` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ensamblar_mueble`
--
ALTER TABLE `Ensamblar_mueble`
  ADD CONSTRAINT `fk_ENSAMBLAR_MUEBLE_MUEBLE1` FOREIGN KEY (`mueble_id`) REFERENCES `Mueble` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ENSAMBLAR_MUEBLE_USUARIO1` FOREIGN KEY (`usuario_id`) REFERENCES `Usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ensambla_pieza`
--
ALTER TABLE `Ensambla_pieza`
  ADD CONSTRAINT `fk_ENSAMBLA_PIEZA_MUEBLE` FOREIGN KEY (`mueble_id`) REFERENCES `Mueble` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_ENSAMBLA_PIEZA_PIEZA1` FOREIGN KEY (`pieza_id`) REFERENCES `Pieza` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `Factura`
  ADD CONSTRAINT `fk_FACTURA_CLIENTE1` FOREIGN KEY (`cliente_id`) REFERENCES `Cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_FACTURA_USUARIO1` FOREIGN KEY (`usuario_id`) REFERENCES `Usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
