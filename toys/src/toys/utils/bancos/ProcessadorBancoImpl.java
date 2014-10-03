/*
 * Criado em 18/08/2009 15:57:47
 */
package toys.utils.bancos;

import java.util.Date;

/**
 * Esta classe possui métodos para gerar códigos utilizados pelos bancos.
 * @author Iran
 */
public abstract class ProcessadorBancoImpl {
	protected Date vencimento;
	protected Double valor;
	protected String codigo;
	protected String dac;
	protected Date dataBase;
	protected String codigoBarras;
	protected String linhaDigitavel;
	
	public ProcessadorBancoImpl(String nossoNumero, Date vencimento, Double valor) {
		super();
		this.vencimento = vencimento;
		this.valor = valor;
		dataBase = java.sql.Date.valueOf("1997-10-07");
		if (nossoNumero.length() % 2 == 1 && nossoNumero.startsWith("0"))
			nossoNumero = nossoNumero.substring(1);
		codigo = String.format("%s%d", nossoNumero, getDVCodigo(Long.valueOf(nossoNumero)));
	}
	
	public String getCodigoBarras() {
		return codigoBarras;
	}
	
	public String getLinhaDigitavel() {
		return linhaDigitavel;
	}
	
	protected abstract int getDVCodigo(long codigoOriginal);
	protected abstract void gerarCodigoBarras();
	protected abstract void gerarLinhaDigitavel();
	
}
