CREATE DATABASE `biblioteca` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `biblioteca`;

CREATE TABLE `livro` (
                         `idLivro` bigint NOT NULL AUTO_INCREMENT,
                         `titulo` varchar(255) NOT NULL,
                         `autor` varchar(45) NOT NULL,
                         `editora` varchar(45) NOT NULL,
                         `isbn` varchar(20) DEFAULT NULL,
                         `anoPublicacao` smallint NOT NULL,
                         PRIMARY KEY (`idLivro`),
                         UNIQUE KEY `isbn_UNIQUE` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `exemplar` (
                            `idExemplar` bigint NOT NULL AUTO_INCREMENT,
                            `codPatrimonio` varchar(45) NOT NULL,
                            `status` enum('DISPONIVEL','EMPRESTADO','MANUTENCAO','PERDIDO') NOT NULL DEFAULT 'DISPONIVEL',
                            `idLivro` bigint NOT NULL,
                            PRIMARY KEY (`idExemplar`),
                            UNIQUE KEY `codPatrimonio_UNIQUE` (`codPatrimonio`),
                            KEY `fk_EXEMPLAR_LIVRO` (`idLivro`),
                            CONSTRAINT `fk_EXEMPLAR_LIVRO` FOREIGN KEY (`idLivro`) REFERENCES `livro` (`idLivro`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `usuario` (
                           `idUsuario` bigint NOT NULL AUTO_INCREMENT,
                           `nome` varchar(100) NOT NULL,
                           `cpf` varchar(14) NOT NULL,
                           `email` varchar(100) NOT NULL,
                           `telefone` varchar(45) NOT NULL,
                           PRIMARY KEY (`idUsuario`),
                           UNIQUE KEY `cpf_UNIQUE` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `emprestimo` (
                              `idEmprestimo` bigint NOT NULL AUTO_INCREMENT,
                              `idUsuario` bigint NOT NULL,
                              `idExemplar` bigint NOT NULL,
                              `dataEmprestimo` datetime NOT NULL,
                              `dataPrevDevolucao` datetime NOT NULL,
                              `dataDevolucao` datetime DEFAULT NULL,
                              PRIMARY KEY (`idEmprestimo`),
                              KEY `fk_EMPRESTIMO_USUARIO1` (`idUsuario`),
                              KEY `fk_EMPRESTIMO_EXEMPLAR1` (`idExemplar`),
                              CONSTRAINT `fk_EMPRESTIMO_EXEMPLAR1` FOREIGN KEY (`idExemplar`) REFERENCES `exemplar` (`idExemplar`) ON DELETE RESTRICT,
                              CONSTRAINT `fk_EMPRESTIMO_USUARIO1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

