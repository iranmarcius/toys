/*
 * Criado em 25/04/2010 11:45:33
 */
package toys.utils.bancos;

import java.text.ParseException;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import toys.utils.DateToys;

/**
 * Gera códigos específicos utilizados pelo Banco Santander.
 */
public class ProcessadorBoletoSantander extends ProcessadorBoleto {
    public static final String BANCO = "033";
    private String convenio;

    /**
     * Construtor.
     * @param convenio Número do convênio. Necessário para a geração dos valores.
     * @see ProcessadorBoleto#ProcessadorBancoImpl(String, Date, Double, boolean)
     */
    public ProcessadorBoletoSantander(String convenio, String nossoNumero, Date vencimento, Double valor, boolean gerarDV) {
        super(nossoNumero, vencimento, valor, gerarDV);
        this.convenio = convenio;
        gerarCodigoBarras();
        gerarLinhaDigitavel();
    }

    @Override
    protected void gerarCodigoBarras() {
        if (codigo == null)
            throw new RuntimeException("O codigo do banco deve ser gerado antes do codigo de barras");

        StringBuilder parte1 = new StringBuilder();
        StringBuilder parte2 = new StringBuilder();

        parte1.append(BANCO).append("9");

        int diferencaDias = DateToys.deltaDays(vencimento, dataBase, true);
        parte2
            .append(String.format("%04d", diferencaDias))
            .append(String.format("%011.2f", valor).replaceAll("[,\\.]", ""))
            .append("9")
            .append(convenio)
            .append(codigo)
            .append("0")
            .append("102");

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

    protected String modulo10(String numero) {
        String sn = numero;
        int m = 2;
        int soma = 0;
        for (int i = sn.length() - 1; i >= 0; i --) {
            int n = Integer.parseInt(sn.substring(i, i + 1));
            String ns = Integer.toString(n * m);
            for (int j = 0; j < ns.length(); j++)
                soma += Integer.valueOf(ns.substring(j, j + 1));
            m = m == 1 ? 2 : 1;
        }
        int resto = soma % 10;
        return String.valueOf(resto != 0 ? 10 - resto : 0);
    }


    @Override
    protected void gerarLinhaDigitavel() {
        if (dac == null)
            throw new RuntimeException("O codigo de barras deve ser gerado antes da linha digitavel");

        StringBuilder parte1 = new StringBuilder()
            .append(BANCO)
            .append("9")
            .append("9")
            .append(convenio.substring(0, 4));
        parte1.append(modulo10(parte1.toString()));

        StringBuilder parte2 = new StringBuilder()
            .append(convenio.substring(4))
            .append(codigo.substring(0, 7));
        parte2.append(modulo10(parte2.toString()));

        StringBuilder parte3 = new StringBuilder()
            .append(codigo.substring(7))
            .append("0")
            .append("102");
        parte3.append(modulo10(parte3.toString()));

        int dias = DateToys.deltaDays(vencimento, dataBase, true);
        StringBuilder parte4 = new StringBuilder()
            .append(String.format("%04d", dias))
            .append(String.format("%011.2f", valor).replaceAll("[,\\.]", ""));

        StringBuilder l = new StringBuilder()
            .append(parte1)
            .append(parte2)
            .append(parte3)
            .append(dac)
            .append(parte4);

        try {
            String mask = "#####.#####.#####.###### #####.###### # ##############";
            JFormattedTextField fmt = new JFormattedTextField(new MaskFormatter(mask));
            fmt.setText(l.toString());
            linhaDigitavel = fmt.getText();
        } catch (ParseException e) {
            throw new RuntimeException("Erro gerando linha digitável.", e);
        }

    }

    @Override
    protected int getDVCodigo(long codigoOriginal) {
        int soma = 0;
        int fator = 2;
        String c = Long.toString(codigoOriginal);

        for (int i = c.length(); i > 0; i--) {
            soma += Integer.valueOf(c.substring(i - 1, i)) * fator;
            if (fator == 9)
                fator = 1;
            fator++;
        }
        int resto = soma % 11;
        if (resto == 0 || resto == 10)
            return 1;
        else if (resto == 1)
            return 0;
        return 11 - resto;
    }

}
