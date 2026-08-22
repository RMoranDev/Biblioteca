package br.com.ricardomoran.biblioteca.repository;

import br.com.ricardomoran.biblioteca.model.Usuario;
import br.com.ricardomoran.biblioteca.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public Usuario salvar(Usuario usuario) {
        String sql = """
                INSERT INTO USUARIO (nome, cpf, email, telefone)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getLong(1));
                }
            }

            return usuario;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário no banco de dados", e);
        }
    }

    public Usuario buscarPorId(long id) {
        String sql = "SELECT * FROM USUARIO WHERE idUsuario = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID: " + id, e);
        }
    }

    public List<Usuario> buscarTodos() {
        String sql = "SELECT * FROM USUARIO";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Enquanto houver registros no banco, ele cria o objeto e adiciona na lista
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os usuários", e);
        }
    }

    public boolean atualizar(Usuario usuario) {
        String sql = """
                UPDATE USUARIO
                SET nome = ?, cpf = ?, email = ?, telefone = ?
                WHERE idUsuario = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());
            stmt.setLong(5, usuario.getId());

            // Se alterou 1 ou mais linhas, retorna true. Caso contrário, false.
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário com ID: " + usuario.getId(), e);
        }
    }

    public boolean deletar(long id) {
        String sql = "DELETE FROM USUARIO WHERE idUsuario = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            // Retorna true se a deleção foi bem-sucedida, false se o ID não existia
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário por ID: " + id, e);
        }
    }

    // Método auxiliar para reaproveitar em qualquer consulta SELECT
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("email"),
                rs.getString("telefone")
        );
        usuario.setId(rs.getLong("idUsuario"));
        return usuario;
    }
}