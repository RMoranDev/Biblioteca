package br.com.ricardomoran.biblioteca.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    public static Connection getConnection() {
        Properties properties = new Properties();

        try (InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "Arquivo db.properties não encontrado"
                );
            }

            properties.load(input);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Erro ao ler o arquivo db.properties", e
            );

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao conectar ao banco de dados", e
            );
        }
    }
}