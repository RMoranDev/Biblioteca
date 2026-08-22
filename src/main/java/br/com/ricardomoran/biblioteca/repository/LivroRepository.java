package br.com.ricardomoran.biblioteca.repository;

import br.com.ricardomoran.biblioteca.config.DatabaseConnection;
import br.com.ricardomoran.biblioteca.model.Livro;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {

    public Livro salvar(Livro livro) {
        String sql = """
                INSERT INTO LIVRO (titulo, autor, editora, isbn, anoPublicacao)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setString(4, livro.getIsbn());
            stmt.setShort(5, (short) livro.getAnoPublicacao().getValue());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    livro.setId(generatedKeys.getLong(1));
                }
            }

            return livro;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar livro no banco de dados", e);
        }
    }

    public Livro buscarPorId(long id) {
        String sql = "SELECT * FROM LIVRO WHERE idLivro = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLivro(rs);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por ID: " + id, e);
        }
    }

    public List<Livro> buscarTodos() {
        String sql = "SELECT * FROM LIVRO ORDER BY titulo";
        List<Livro> livros = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Enquanto houver registros no banco, ele cria o objeto e adiciona na lista
            while (rs.next()) {
                livros.add(mapearLivro(rs));
            }
            return livros;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os livros", e);
        }
    }

    public boolean atualizar(Livro livro) {
        String sql = """
            UPDATE LIVRO
            SET titulo = ?, autor = ?, editora = ?, isbn = ?, anoPublicacao = ?
            WHERE idLivro = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getEditora());
            stmt.setString(4, livro.getIsbn());
            stmt.setShort(5, (short) livro.getAnoPublicacao().getValue());
            stmt.setLong(6, livro.getId());

            // Se alterou 1 ou mais linhas, retorna true. Caso contrário, false.
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar livro com ID: " + livro.getId(), e);
        }
    }

    public boolean deletar(long id) {
        String sql = "DELETE FROM LIVRO WHERE idLivro = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            // Retorna true se a deleção foi bem-sucedida, false se o ID não existia
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar livro por ID: " + id, e);
        }
    }



    private Livro mapearLivro(ResultSet rs) throws SQLException {
        Livro livro = new Livro(
                rs.getString("titulo"),
                rs.getString("autor"),
                rs.getString("editora"),
                rs.getString("isbn"),
                Year.of(rs.getShort("anoPublicacao"))
        );
        livro.setId(rs.getLong("idLivro"));
        return livro;
    }
}
