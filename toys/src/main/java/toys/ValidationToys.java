/*
 * Criado em 04/06/2008 14:49:20
 */

package toys;

/**
 * Esta classe possui métodos utilitários para validação de informações.
 * @author Iran
 */
public class ValidationToys {

    private ValidationToys() {
        super();
    }

    /**
     * Retorna se um número de CPF é válido.
     * @param cpf String com um número de CPF.
     * @return <code>boolean</code>
     */
    public static boolean isCPFValid(String cpf) {

        // trata o valor nulo
        if (cpf == null)
            return false;

        // verifica o formato da string
        if (!cpf.matches(ToysConsts.RE_CPF) && !cpf.matches("^\\d{11}$"))
            return false;

        // remove os caracteres indesejados
        String cpfLimpo = cpf.replaceAll("[\\-.]", "");

        // verifica se o número do RE_CPF é uma repetição do mesmo número
        int d;
        StringBuilder sb = new StringBuilder()
            .append("^")
            .append(cpfLimpo, 0, 1)
            .append("{11,11}$");
        if (cpfLimpo.matches(sb.toString()))
            return false;

        // converte a string para um array de bytes para fazer os cálculos de validação
        int[] b = StringToys.toIntArray(cpfLimpo);
        if (b == null || b.length < 11)
            return false;

        // calcula o primeiro dígito verificador
        d = 0;
        for (int i = 1; i <= 9; i++)
            d += b[i - 1] * i;
        int d1 = d % 11;
        if (d1 > 9) d1 -= 10;

        // calcula o segundo dígito verificador
        d = d1 * 9;
        for (int i = 0; i <= 8; i++)
            d += b[i] * i;
        int d2 = d % 11;
        if (d2 > 9)
            d2 -= 10;

        return b[9] == d1 && b[10] == d2;
    }

    /**
     * Retorna se o número do CNPJ é válido.
     * @param cnpj String com o número do CNPJ.
     * @return <code>boolean</code>
     */
    public static boolean isCNPJValid(String cnpj) {

        if (cnpj == null)
            return false;

        if (!cnpj.matches(ToysConsts.RE_CNPJ) && !cnpj.matches("^\\d{14}$"))
            return false;

        String cnpjLimpo = cnpj.replaceAll("[.\\-/]", "");

        int d;
        int[] factor = {5, 6, 7, 8, 9, 2, 3, 4, 5, 6, 7, 8, 9};

        // verifica se o cnpj é uma repetição do mesmo número
        StringBuilder sb = new StringBuilder()
            .append("^")
            .append(cnpjLimpo, 0, 1)
            .append("{14}$");
        if (cnpjLimpo.matches(sb.toString()))
            return false;

        int[] b = StringToys.toIntArray(cnpjLimpo);

        // calcula o primeiro dígito verificador
        d = 0;
        for (int i = 1; i < factor.length; i++)
            d += b[i - 1] * factor[i];

        int d1 = d % 11;
        if (d1 > 9) d1 -= 10;

        // calcula o segundo dígito verificador
        d = d1 * 9;
        for (int i = 0; i < factor.length - 1; i++)
            d += b[i] * factor[i];

        int d2 = d % 11;
        if (d2 > 9) d2 -= 10;

        return (b[12] == d1) && (b[13] == d2);
    }

    /**
     * Retorna se um e-mail é válido.
     * @param email Email a ser verificado. Valores nulos serão assumidos como inválidos.
     * @return <code>boolean</code>
     */
    public static boolean isEmailValido(String email) {
        return email != null && email.matches(ToysConsts.RE_EMAIL);
    }

}
