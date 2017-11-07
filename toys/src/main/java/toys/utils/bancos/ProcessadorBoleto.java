/*
 * Criado em 18/08/2009 15:57:47
 */
package toys.utils.bancos;

import java.util.Date;

/**
 * Esta classe possui métodos para gerar códigos utilizados pelos bancos.
 * @author Iran
 */
public abstract class ProcessadorBoleto {
	protected Date vencimento;
	protected Double valor;
	protected String codigo;
	protected String dac;
	protected Date dataBase;
	protected String codigoBarras;
	protected String linhaDigitavel;
	
	/**
	 * Construtor padrão para a geração do boleto. 
	 * @param nossoNumero Número do documento.
	 * @param vencimento Vencimento do documento.
	 * @param valor Valor do documento.
	 * @param gerarDV Flag indicando se deve ser gerado um dígito verificador para o documento
	 * à partor do valor do nossoNumero.
	 */
	public ProcessadorBoleto(String nossoNumero, Date vencimento, Double valor, boolean gerarDV) {
		super();
		this.vencimento = vencimento;
		this.valor = valor;
		dataBase = java.sql.Date.valueOf("1997-10-07");
		
		if (gerarDV) {
			if (nossoNumero.length() % 2 == 1 && nossoNumero.startsWith("0"))
				nossoNumero = nossoNumero.substring(1);
			codigo = String.format("%s%d", nossoNumero, getDVCodigo(Long.valueOf(nossoNumero)));
		} else {
			codigo = nossoNumero;
		}
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
