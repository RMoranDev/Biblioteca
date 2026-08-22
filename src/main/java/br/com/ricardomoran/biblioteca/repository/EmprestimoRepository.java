package br.com.ricardomoran.biblioteca.repository;

import br.com.ricardomoran.biblioteca.config.DatabaseConnection;
import br.com.ricardomoran.biblioteca.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoRepository {

    public Emprestimo salvar(Emprestimo emprestimo) {
        String sql = """
                INSERT INTO emprestimo (idUsuario, idExemplar, dataEmprestimo, dataPrevDevolucao, dataDevolucao)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, emprestimo.getUsuario().getId());
            stmt.setLong(2, emprestimo.getExemplar().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setTimestamp(4, Timestamp.valueOf(emprestimo.getDataPrevistaDevolucao()));
            if (emprestimo.getDataDevolucao() != null) {
                stmt.setTimestamp(
                        5,
                        Timestamp.valueOf(emprestimo.getDataDevolucao())
                );
            } else {
                stmt.setNull(5, Types.TIMESTAMP);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    emprestimo.setId(generatedKeys.getLong(1));
                }
            }

            return emprestimo;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar empréstimo no banco de dados", e);
        }
    }

    public Emprestimo buscarPorId(long id) {
        String sql = """
            SELECT
                e.idEmprestimo,
                e.idUsuario,
                e.idExemplar,
                e.dataEmprestimo,
                e.dataPrevDevolucao,
                e.dataDevolucao,

                u.nome AS usuario_nome,
                u.cpf AS usuario_cpf,
                u.email AS usuario_email,
                u.telefone AS usuario_telefone,

                ex.codPatrimonio,
                ex.status,

                l.idLivro,
                l.titulo AS livro_titulo,
                l.autor AS livro_autor,
                l.editora AS livro_editora,
                l.isbn AS livro_isbn,
                l.anoPublicacao AS livro_anoPublicacao

            FROM EMPRESTIMO e
            JOIN USUARIO u
                ON e.idUsuario = u.idUsuario
            JOIN EXEMPLAR ex
                ON e.idExemplar = ex.idExemplar
            JOIN LIVRO l
                ON ex.idLivro = l.idLivro
            WHERE e.idEmprestimo = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEmprestimo(rs);
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empréstimo por ID: " + id, e);
        }
    }

    public List<Emprestimo> buscarTodos() {
        String sql = """
            SELECT
                e.idEmprestimo,
                e.idUsuario,
                e.idExemplar,
                e.dataEmprestimo,
                e.dataPrevDevolucao,
                e.dataDevolucao,

                u.nome AS usuario_nome,
                u.cpf AS usuario_cpf,
                u.email AS usuario_email,
                u.telefone AS usuario_telefone,

                ex.codPatrimonio,
                ex.status,

                l.idLivro,
                l.titulo AS livro_titulo,
                l.autor AS livro_autor,
                l.editora AS livro_editora,
                l.isbn AS livro_isbn,
                l.anoPublicacao AS livro_anoPublicacao

            FROM EMPRESTIMO e
            JOIN USUARIO u
                ON e.idUsuario = u.idUsuario
            JOIN EXEMPLAR ex
                ON e.idExemplar = ex.idExemplar
            JOIN LIVRO l
                ON ex.idLivro = l.idLivro
            """;

        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                emprestimos.add(mapearEmprestimo(rs));
            }

            return emprestimos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os empréstimos", e);
        }
    }

    public boolean atualizar(Emprestimo emprestimo) {
        String sql = """
            UPDATE EMPRESTIMO
            SET idUsuario = ?,
                idExemplar = ?,
                dataPrevDevolucao = ?,
                dataDevolucao = ?
            WHERE idEmprestimo = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, emprestimo.getUsuario().getId());
            stmt.setLong(2, emprestimo.getExemplar().getId());

            stmt.setTimestamp(
                    3,
                    Timestamp.valueOf(emprestimo.getDataPrevistaDevolucao())
            );

            if (emprestimo.getDataDevolucao() != null) {
                stmt.setTimestamp(
                        4,
                        Timestamp.valueOf(emprestimo.getDataDevolucao())
                );
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }

            stmt.setLong(5, emprestimo.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar empréstimo com ID: "
                            + emprestimo.getId(), e
            );
        }
    }

    public boolean deletar(long id) {
        String sql = "DELETE FROM EMPRESTIMO WHERE idEmprestimo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao deletar empréstimo por ID: " + id, e
            );
        }
    }


    private Emprestimo mapearEmprestimo(ResultSet rs) throws SQLException {

        Usuario usuario = new Usuario(
                rs.getString("usuario_nome"),
                rs.getString("usuario_cpf"),
                rs.getString("usuario_email"),
                rs.getString("usuario_telefone")
        );

        usuario.setId(rs.getLong("idUsuario"));

        Livro livro = new Livro(
                rs.getString("livro_titulo"),
                rs.getString("livro_autor"),
                rs.getString("livro_editora"),
                rs.getString("livro_isbn"),
                Year.of(rs.getShort("livro_anoPublicacao"))
        );

        livro.setId(rs.getLong("idLivro"));

        Exemplar exemplar = new Exemplar(
                rs.getString("codPatrimonio"),
                livro
        );

        exemplar.setId(rs.getLong("idExemplar"));
        exemplar.setStatus(
                StatusExemplar.valueOf(rs.getString("status"))
        );

        LocalDateTime dataEmprestimo =
                rs.getTimestamp("dataEmprestimo").toLocalDateTime();

        LocalDateTime dataPrevistaDevolucao =
                rs.getTimestamp("dataPrevDevolucao").toLocalDateTime();

        Timestamp timestampDevolucao = rs.getTimestamp("dataDevolucao");

        LocalDateTime dataDevolucao =
                timestampDevolucao != null
                        ? timestampDevolucao.toLocalDateTime()
                        : null;

        Emprestimo emprestimo = new Emprestimo(
                usuario,
                exemplar,
                dataEmprestimo,
                dataPrevistaDevolucao,
                dataDevolucao
        );

        emprestimo.setId(rs.getLong("idEmprestimo"));

        return emprestimo;
    }
}
