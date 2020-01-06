-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 06-Jan-2020 às 18:48
-- Versão do servidor: 10.1.36-MariaDB
-- versão do PHP: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bancoechamada`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `alunos`
--

CREATE TABLE `alunos` (
  `id_aluno` int(11) NOT NULL,
  `nome_aluno` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  `sexo` varchar(5) NOT NULL,
  `endereco` varchar(45) NOT NULL,
  `data_nascimento` date NOT NULL,
  `telefone_responsavel` varchar(15) NOT NULL,
  `serie` varchar(11) NOT NULL,
  `turma` varchar(11) NOT NULL,
  `turno` varchar(20) NOT NULL,
  `status` int(11) NOT NULL,
  `nome_mae` varchar(45) NOT NULL,
  `nome_pai` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `alunos`
--

INSERT INTO `alunos` (`id_aluno`, `nome_aluno`, `senha`, `sexo`, `endereco`, `data_nascimento`, `telefone_responsavel`, `serie`, `turma`, `turno`, `status`, `nome_mae`, `nome_pai`) VALUES
(1, 'asdc', 'asdc', 'F', 'sdf', '2020-01-01', 'sdvc', '2', 'A', 'Matutino', 0, 'sdv', 'sd'),
(2, 'asdc', 'asdc', 'F', 'sdf', '2020-01-01', 'sdvc', '2', 'A', 'Vespertino', 0, 'sdv', 'sd');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alunos`
--
ALTER TABLE `alunos`
  ADD PRIMARY KEY (`id_aluno`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alunos`
--
ALTER TABLE `alunos`
  MODIFY `id_aluno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
