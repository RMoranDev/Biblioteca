package br.com.ricardomoran.biblioteca.util;

public class CpfUtil {

    private static int calcularDigitoVerificador(String base, int pesoInicial) {
        int soma = 0;
        int peso = pesoInicial;

        for (int i = 0; i < base.length(); i++) {
            int digito = base.charAt(i) - '0';
            soma += digito * peso;
            peso--;
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    private static int calcularPrimeiroDigito(String cpf) {
        String base = cpf.substring(0, 9);
        return calcularDigitoVerificador(base, 10);
    }

    private static int calcularSegundoDigito(String cpf, int primeiroDigito) {
        String base = cpf.substring(0, 9) + primeiroDigito;
        return calcularDigitoVerificador(base, 11);
    }

    public static boolean isCpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        String cpfNormalizado = normalizarCpf(cpf);

        if (cpfNormalizado == null) {
            return false;
        }

        if (cpfNormalizado.chars().distinct().count() == 1) {
            return false;
        }

        int primeiroDigito = calcularPrimeiroDigito(cpfNormalizado);
        int segundoDigito = calcularSegundoDigito(cpfNormalizado, primeiroDigito);

        int primeiroInformado = cpfNormalizado.charAt(9) - '0';
        int segundoInformado = cpfNormalizado.charAt(10) - '0';

        return primeiroDigito == primeiroInformado
                && segundoDigito == segundoInformado;
    }

    private static String normalizarCpf(String cpf) {
        if (cpf.matches("\\d{11}")) {
            return cpf;
        }

        if (cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            return cpf.replaceAll("[.-]", "");
        }

        return null;
    }
}
