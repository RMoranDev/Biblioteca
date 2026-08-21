# 📚 Sistema de Gerenciamento de Biblioteca

Projeto desenvolvido em **Java** para gerenciamento de uma biblioteca.

A aplicação está sendo desenvolvida para gerenciar usuários, livros, exemplares e empréstimos, utilizando **Java e JDBC** para comunicação com um banco de dados **MySQL**.

> 🚧 Projeto em desenvolvimento.

---

## 🎯 Objetivo

O objetivo deste projeto é praticar e consolidar conhecimentos em:

- Programação Orientada a Objetos
- Java
- JDBC
- MySQL
- Modelagem de Banco de Dados
- Maven
- Git e GitHub
- Arquitetura em camadas
- Testes unitários

---

## 🛠️ Tecnologias utilizadas

- Java
- MySQL
- JDBC
- Maven
- JUnit
- IntelliJ IDEA
- MySQL Workbench
- BRModelo

---

## 🏗️ Estrutura do projeto

O projeto está sendo organizado em camadas para separar as responsabilidades da aplicação.

```text
src
├── main
│   ├── java
│   │   └── br.com.ricardomoran.biblioteca
│   │       ├── config
│   │       │   └── DatabaseConnection
│   │       │
│   │       ├── model
│   │       │   ├── Emprestimo
│   │       │   ├── Exemplar
│   │       │   ├── Livro
│   │       │   ├── StatusExemplar
│   │       │   └── Usuario
│   │       │
│   │       ├── repository
│   │       │   └── UsuarioRepository
│   │       │
│   │       ├── util
│   │       │   ├── CpfUtil
│   │       │   └── ValidacaoUtil
│   │       │
│   │       └── Main
│   │
│   └── resources
│       └── db.properties
│
└── test
    └── java
```

## 🗄️ Modelagem do Banco de Dados

O banco de dados foi inicialmente modelado utilizando o BRModelo e posteriormente implementado no MySQL Workbench.

Atualmente, o banco possui quatro entidades principais:

- USUARIO
- LIVRO
- EXEMPLAR
- EMPRESTIMO

Relacionamentos
```text
LIVRO
│
│ 1 : N
▼
EXEMPLAR


USUARIO
│
│ 1 : N
▼
EMPRESTIMO
▲
│ N : 1
│
EXEMPLAR
```

## 📦 Entidades

👤 Usuário

Representa uma pessoa cadastrada na biblioteca.

Principais informações:

- ID
- Nome
- CPF
- E-mail
- Telefone

O CPF possui validação e não pode ser duplicado no banco de dados.

## 📖 Livro

Representa as informações gerais de uma obra.

Principais informações:

- ID
- Título
- Autor
- Editora
- ISBN
- Ano de publicação
- 📚 Exemplar

Representa uma cópia física de um livro.

Principais informações:

- ID
- Código de patrimônio
- Status
- Livro relacionado

Status disponíveis:

- DISPONIVEL
- EMPRESTADO
- MANUTENCAO
- PERDIDO
- 🔄 Empréstimo

Representa o empréstimo de um exemplar para um usuário.

Principais informações:

- ID
- Usuário
- Exemplar
- Data do empréstimo
- Data prevista para devolução
- Data da devolução
- 🔌 Conexão com o Banco de Dados

A comunicação entre a aplicação Java e o banco de dados é realizada utilizando JDBC.

A classe responsável por centralizar a criação das conexões é:

```text
DatabaseConnection
        │
        ▼
DriverManager
        │
        ▼
Connection
        │
        ▼
MySQL
```

As configurações do banco ficam no arquivo:

src/main/resources/db.properties

Esse arquivo não é versionado no Git, pois contém informações de acesso ao banco de dados.

Exemplo:

```text
db.url=jdbc:mysql://localhost:3306/biblioteca
db.user=seu_usuario
db.password=sua_senha
```
