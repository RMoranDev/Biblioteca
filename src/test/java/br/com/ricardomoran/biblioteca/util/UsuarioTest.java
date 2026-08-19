package br.com.ricardomoran.biblioteca.util;

import br.com.ricardomoran.biblioteca.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsuarioTest {
    @Test
    void deveCriarUmUsuarioValido() {
        Usuario usuario = new Usuario(
                "Ricardo",
                "529.982.247-25",
                "ricardo@email.com",
                "(41) 99999-9999"
        );

        assertEquals("Ricardo", usuario.getNome());
        assertEquals("529.982.247-25", usuario.getCpf());
        assertEquals("ricardo@email.com", usuario.getEmail());
        assertEquals("(41) 99999-9999", usuario.getTelefone());
    }

    @Test
    void deveRejeitarNomeVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(
                    "",
                    "529.982.247-25",
                    "ricardo@email.com",
                    "(41) 99999-9999"
            );
        });
    }

    @Test
    void deveRejeitarCpfInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(
                    "Ricardo",
                    "529.982.247-85",
                    "ricardo@email.com",
                    "(41) 99999-9999"
            );
        });
    }

    @Test
    void deveRejeitarCpfNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(
                    "Ricardo",
                    null,
                    "ricardo@email.com",
                    "(41) 99999-9999"
            );
        });
    }

    @Test
    void deveRejeitarEmailVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(
                    "Ricardo",
                    "529.982.247-25",
                    "",
                    "(41) 99999-9999"
            );
        });
    }

    @Test
    void deveRejeitarTelefoneVazio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(
                    "Ricardo",
                    "529.982.247-25",
                    "ricardo@email.com",
                    ""
            );
        });
    }
}
