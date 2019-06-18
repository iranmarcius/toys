/*
 * Criado em 18/08/2009 16:06:55
 */
package toys.bancos;

import toys.DateToys;
import toys.NumberToys;
import toys.exceptions.ToysRuntimeException;

import java.util.Date;

/**
 * Gera códigos específicos utilizados pelo Banco Sudameris.
 *
 * @author Iran
 */
public class ProcessadorBoletoSudameris extends ProcessadorBoleto {
    public static final String BANCO = "215";
    private String agencia;
    private String conta;

    /**
     * Construtor padrão.
     *
     * @param agencia Número da agência para ser utilizado na geração dos valores.
     * @param conta   Número da conta para ser utilizador na geração dos valores.
     */
    public ProcessadorBoletoSudameris(String agencia, String conta, String nossoNumero, Date vencimento, Double valor, boolean gerarDV) {
        super(nossoNumero, vencimento, valor, gerarDV);
        this.agencia = agencia;
        this.conta = conta;
        gerarCodigoBarras();
        gerarLinhaDigitavel();
    }

    @Override
    protected int getDVCodigo(long codigoOriginal) {
        int soma = 0;
        String idStr = Long.toString(codigoOriginal);
        for (int i = 0; i < idStr.length(); i++)
            soma += Integer.valueOf(idStr.substring(i, i + 1)) * (9 - i);
        int resto = soma % 11;
        if (resto == 0)
            return 1;
        else if (resto == 1)
            return 0;
        else
            return 11 - resto;
    }

    @Override
    protected void gerarCodigoBarras() {
        if (codigo == null)
            throw new ToysRuntimeException("O codigo do banco deve ser gerado antes do codigo de barras");

        StringBuilder parte1 = new StringBuilder();
        StringBuilder parte2 = new StringBuilder();

        parte1.append(BANCO, 0, 3).append("9");

        int diferencaDias = DateToys.deltaDays(vencimento, dataBase, true);
        parte2
                .append(String.format("%04d", diferencaDias))
                .append(String.format("%011.2f", valor).replaceAll("[,.]", ""))
                .append(agencia, 0, 3)
                .append(conta.replaceAll("-", ""))
                .append(codigo)
                .append("00000");

        int multiplicador = 2;
        int soma = 0;
        StringBuilder sb = new StringBuilder().append(parte1).append(parte2);
        for (int i = sb.length() - 1; i >= 0; i--) {
            soma += Integer.valueOf(sb.substring(i, i + 1)) * multiplicador;
            if (++multiplicador > 9)
                multiplicador = 2;
        }

        dac = "";
        int resto = soma % 11;
        if (resto == 0 || resto == 1 || resto == 10)
            dac = "1";
        else
            dac = Integer.toString(11 - resto);

        codigoBarras = String.format("%s%s%s", parte1, dac, parte2);
    }

    @Override
    protected void gerarLinhaDigitavel() {
        if (dac == null)
            throw new ToysRuntimeException("O codigo de barras deve ser gerado antes da linha digitavel");

        StringBuilder parte1 = new StringBuilder()
                .append(BANCO, 0, 3)
                .append("9")
                .append(agencia, 0, 3)
                .append(conta, 0, 2);
        parte1.append(NumberToys.modulo10(parte1.toString()));

        StringBuilder parte2 = new StringBuilder()
                .append(conta.replaceAll("-", "").substring(2))
                .append(codigo, 0, 4);
        parte2.append(NumberToys.modulo10(parte2.toString()));

        StringBuilder parte3 = new StringBuilder()
                .append(codigo, 4, 9)
                .append("00000");
        parte3.append(NumberToys.modulo10(parte3.toString()));

        int dias = DateToys.deltaDays(vencimento, dataBase, true);
        StringBuilder parte4 = new StringBuilder()
                .append(String.format("%04d", dias))
                .append(String.format("%011.2f", valor).replaceAll("[,.]", ""));

        linhaDigitavel =
                parte1.substring(0, 5) + "." + parte1.substring(5) + "  " +
                        parte2.substring(0, 5) + "." + parte2.substring(5) + "  " +
                        parte3.substring(0, 5) + "." + parte3.substring(5) + "  " +
                        dac + "  " +
                        parte4;
    }

}
