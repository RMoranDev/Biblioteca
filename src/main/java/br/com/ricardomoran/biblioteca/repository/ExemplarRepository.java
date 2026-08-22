package br.com.ricardomoran.biblioteca.repository;

import br.com.ricardomoran.biblioteca.config.DatabaseConnection;
import br.com.ricardomoran.biblioteca.model.*;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class ExemplarRepository {

    public Exemplar salvar(Exemplar exemplar) {
        String sql = """
                INSERT INTO exemplar (codPatrimonio, status, idLivro)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, exemplar.getCodigoPatrimonio());
            stmt.setString(2, exemplar.getStatus().name());
            stmt.setLong(3, exemplar.getLivro().getId());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    exemplar.setId(generatedKeys.getLong(1));
                }
            }

            return exemplar;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar exemplar no banco de dados", e);
        }
    }

    public Exemplar buscarPorId(long id) {
        String sql = """
            SELECT *
            FROM exemplar e
            JOIN livro l ON e.idLivro = l.idLivro
            WHERE e.idExemplar = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearExemplar(rs);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar exemplar por ID: " + id, e);
        }
    }

    public List<Exemplar> buscarTodos() {
        String sql = """
            SELECT *
            FROM exemplar e
            JOIN livro l ON e.idLivro = l.idLivro
            """;
        List<Exemplar> exemplares = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Enquanto houver registros no banco, ele cria o objeto e adiciona na lista
            while (rs.next()) {
                exemplares.add(mapearExemplar(rs));
            }
            return exemplares;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os exemplares", e);
        }
    }

    public boolean atualizar(Exemplar exemplar) {
        String sql = """
                UPDATE exemplar
                SET codPatrimonio = ?, status = ?, idLivro = ?
                WHERE idExemplar = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, exemplar.getCodigoPatrimonio());
            stmt.setString(2, exemplar.getStatus().name());
            stmt.setLong(3, exemplar.getLivro().getId());
            stmt.setLong(4, exemplar.getId());

            // Se alterou 1 ou mais linhas, retorna true. Caso contrário, false.
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar exemplar com ID: " + exemplar.getId(), e);
        }
    }

    public boolean deletar(long id) {
        String sql = "DELETE FROM exemplar WHERE idExemplar = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            // Retorna true se a deleção foi bem-sucedida, false se o ID não existia
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar exemplar por ID: " + id, e);
        }
    }


    private Exemplar mapearExemplar(ResultSet rs) throws SQLException {

        Livro livro = new Livro(
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getString("editora"),
                rs.getString("isbn"),
                Year.of(rs.getShort("anoPublicacao"))
        );

        livro.setId(rs.getLong("idLivro"));

        Exemplar exemplar = new Exemplar(
                rs.getString("codPatrimonio"),
                livro
        );

        exemplar.setStatus(
                StatusExemplar.valueOf(rs.getString("status"))
        );

        exemplar.setId(rs.getLong("idExemplar"));

        return exemplar;
    }
}
