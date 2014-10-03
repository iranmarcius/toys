/*
 * Criado em 18/08/2009 16:06:55
 */
package toys.utils.bancos;

import java.util.Date;

import toys.utils.DateToys;
import toys.utils.NumberToys;

/**
 * Gera códigos específicos utilizados pelo Banco Sudameris.
 * @author Iran
 */
public class ProcessadorBancoSudameris extends ProcessadorBancoImpl {
	public static final String BANCO = "215";
	
	private String agencia;
	private String conta;

	public ProcessadorBancoSudameris(String agencia, String conta, String nossoNumero, Date vencimento, Double valor) {
		super(nossoNumero, vencimento, valor);
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
		else
			if (resto == 1)
				return 0;
			else
				return 11 - resto;
	}

	@Override
	protected void gerarCodigoBarras() {
		if (codigo == null)
			throw new RuntimeException("O codigo do banco deve ser gerado antes do codigo de barras");

		StringBuffer parte1 = new StringBuffer();
		StringBuffer parte2 = new StringBuffer();

		parte1.append(BANCO.substring(0, 3)).append("9");

		int diferencaDias = DateToys.deltaDays(vencimento, dataBase, true);
		parte2
			.append(String.format("%04d", diferencaDias))
			.append(String.format("%011.2f", valor).replaceAll("[,\\.]", ""))
			.append(agencia.substring(0, 3))
			.append(conta.replaceAll("\\-", ""))
			.append(codigo)
			.append("00000");

		int multiplicador = 2;
		int soma = 0;
		StringBuffer sb = new StringBuffer().append(parte1).append(parte2);
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
			throw new RuntimeException("O codigo de barras deve ser gerado antes da linha digitavel");

		StringBuffer parte1 = new StringBuffer()
			.append(BANCO.substring(0, 3))
			.append("9")
			.append(agencia.substring(0, 3))
			.append(conta.substring(0, 2));
		parte1.append(NumberToys.modulo10(Long.valueOf(parte1.toString())));

		StringBuffer parte2 = new StringBuffer()
			.append(conta.replaceAll("-", "").substring(2))
			.append(codigo.substring(0, 4));
		parte2.append(NumberToys.modulo10(Long.valueOf(parte2.toString())));

		StringBuffer parte3 = new StringBuffer()
			.append(codigo.substring(4, 9))
			.append("00000");
		parte3.append(NumberToys.modulo10(Long.valueOf(parte3.toString())));

		int dias = DateToys.deltaDays(vencimento, dataBase, true);
		StringBuffer parte4 = new StringBuffer()
			.append(String.format("%04d", dias))
			.append(String.format("%011.2f", valor).replaceAll("[,\\.]", ""));

		StringBuffer ld = new StringBuffer()
			.append(parte1.substring(0, 5)).append(".").append(parte1.substring(5)).append("  ")
			.append(parte2.substring(0, 5)).append(".").append(parte2.substring(5)).append("  ")
			.append(parte3.substring(0, 5)).append(".").append(parte3.substring(5)).append("  ")
			.append(dac).append("  ")
			.append(parte4);

		linhaDigitavel = ld.toString();
	}

}
