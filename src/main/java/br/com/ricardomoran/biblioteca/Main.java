package br.com.ricardomoran.biblioteca;

import br.com.ricardomoran.biblioteca.config.DatabaseConnection;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try (Connection connection = DatabaseConnection.getConnection()) {

            System.out.println("Conexão realizada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro na conexão!");
            e.printStackTrace();
        }
    }
}