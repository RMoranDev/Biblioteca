package br.com.ricardomoran.biblioteca.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CpfUtilTest {

    @Test
    void deveAceitarCpfComMascara() {
        assertTrue(CpfUtil.isCpfValido("529.982.247-25"));
    }

    @Test
    void deveAceitarCpfSemMascara() {
        assertTrue(CpfUtil.isCpfValido("52998224725"));
    }

    @Test
    void deveRejeitarCpfInvalido() {
        assertFalse(CpfUtil.isCpfValido("529.982.247-85"));
    }

    @Test
    void deveRejeitarCpfNulo() {
        assertFalse(CpfUtil.isCpfValido(null));
    }
    @Test
    void deveRejeitarCpfQuantidadeErrada() {
        assertFalse(CpfUtil.isCpfValido("5299822472"));
    }

    @Test
    void deveRejeitarCpfDigitosIguais() {
        assertFalse(CpfUtil.isCpfValido("111.111.111-11"));
    }

    @Test
    void deveRejeitarFormatoInvalido() {
        assertFalse(CpfUtil.isCpfValido("529.982.247.25"));
    }

}
