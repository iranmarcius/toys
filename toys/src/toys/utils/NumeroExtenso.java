package toys.utils;

/**
 * Converte números em seu formato por extenso.
 */
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NumeroExtenso {
	private List<Integer> nro;
	private BigInteger num;

	private String qualificadores[][] = {
			{"centavo", "centavos"},
			{"", ""},
			{"mil", "mil"},
			{"milhão", "milhões"},
			{"bilhão", "bilhões" },
			{"trilhão", "trilhões"},
			{"quatrilhão", "quatrilhões"},
			{"quintilhão", "quintilhões"},
			{"sextilhão", "sextilhões"},
			{"septilhão", "septilhões"}
	};

	private String numeros[][] = {
			{
				"zero",
				"um",
				"dois",
				"três",
				"quatro",
				"cinco",
				"seis",
				"sete",
				"oito",
				"nove",
				"dez",
				"onze",
				"doze",
				"treze",
				"quatorze",
				"quinze",
				"desesseis",
				"desessete",
				"dezoito",
				"desenove"
			}, {
				"vinte",
				"trinta",
				"quarenta",
				"cinquenta",
				"sessenta",
				"setenta",
				"oitenta",
				"noventa"
			}, {
				"cem",
				"cento",
				"duzentos",
				"trezentos",
				"quatrocentos",
				"quinhentos",
				"seiscentos",
				"setecentos",
				"oitocentos",
				"novecentos"
			}
	};

	/**
	 * Construtor
	 */
	public NumeroExtenso() {
		super();
		nro = new ArrayList<Integer>();
	}

	/**
	 * Construtor
	 * @param dec valor para colocar por extenso
	 */
	public NumeroExtenso(BigDecimal dec) {
		this();
		setNumber(dec);
	}

	/**
	 * Constructor for the Extenso object
	 * @param dec valor para colocar por extenso
	 */
	public NumeroExtenso(double dec) {
		this();
		setNumber(dec);
	}

	/**
	 * Sets the Number attribute of the Extenso object
	 * @param dec The new Number value
	 */
	public void setNumber(BigDecimal dec) {
		// Converte para inteiro arredondando os centavos
		num = dec.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(
				BigDecimal.valueOf(100)).toBigInteger();

		// Adiciona valores
		nro.clear();
		if (num.equals(BigInteger.ZERO)) {
			// Centavos
			nro.add(Integer.valueOf(0));
			// Valor
			nro.add(Integer.valueOf(0));
		} else {
			// Adiciona centavos
			addRemainder(100);

			// Adiciona grupos de 1000
			while (!num.equals(BigInteger.ZERO)) {
				addRemainder(1000);
			}
		}
	}

	public void setNumber(double dec) {
		setNumber(new BigDecimal(dec));
	}

	/**
	 * Description of the Method
	 */
	public void show() {
		Iterator<Integer> valores = nro.iterator();
		while (valores.hasNext()) {
			System.out.println(valores.next().intValue());
		}
		System.out.println(toString());
	}

	public String toString(BigDecimal n) {
		setNumber(n);
		return toString();
	}

	/**
	 * Description of the Method
	 * @return Description of the Returned Value
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (int ct = nro.size() - 1; ct > 0; ct--) {
			// Se ja existe texto e o atual não é zero
			if (buf.length() > 0 && !ehGrupoZero(ct))
				buf.append(", ");
			buf.append(numToString(nro.get(ct).intValue(), ct));
		}
		if (buf.length() > 0) {
			if (ehUnicoGrupo()) {
				buf.append(" de ");
			}
			while (buf.toString().endsWith(" ")) {
				buf.setLength(buf.length() - 1);
			}
			if (ehPrimeiroGrupoUm()) {
				buf.insert(0, "h");
			}
			if ((nro.size() == 2) && (nro.get(1).intValue() == 1)) {
				buf.append(" real");
			} else {
				buf.append(" reais");
			}
			if (nro.get(0).intValue() != 0) {
				buf.append(" e ");
			}
		}
		if (nro.get(0).intValue() != 0) {
			buf.append(numToString(nro.get(0).intValue(), 0));
		}
		return buf.toString();
	}

	private boolean ehPrimeiroGrupoUm() {
		return nro.get(nro.size() - 1).intValue() == 1;
	}

	/**
	 * Adds a feature to the Remainder attribute of the NumeroExtenso object
	 * @param divisor The feature to be added to the Remainder attribute
	 */
	private void addRemainder(int divisor) {
		// Encontra newNum[0] = num modulo divisor, newNum[1] = num dividido
		// divisor
		BigInteger[] newNum = num.divideAndRemainder(BigInteger
				.valueOf(divisor));

		// Adiciona modulo
		nro.add(Integer.valueOf(newNum[1].intValue()));

		// Altera numero
		num = newNum[0];
	}

	/**
	 * Description of the Method
	 * @return Description of the Returned Value
	 */
	private boolean ehUnicoGrupo() {
		if (nro.size() <= 3) {
			return false;
		}
		if (!ehGrupoZero(1) && !ehGrupoZero(2)) {
			return false;
		}
		boolean hasOne = false;
		for (int i = 3; i < nro.size(); i++) {
			if (nro.get(i).intValue() != 0) {
				if (hasOne) {
					return false;
				}
				hasOne = true;
			}
		}
		return true;
	}

	boolean ehGrupoZero(int ps) {
		if (ps <= 0 || ps >= nro.size())
			return true;
		return ((Integer) nro.get(ps)).intValue() == 0;
	}

	/**
	 * Description of the Method
	 * @param numero Description of Parameter
	 * @param escala Description of Parameter
	 * @return Description of the Returned Value
	 */
	private String numToString(int numero, int escala) {
		int unidade = numero % 10;
		int dezena = numero % 100;
		int centena = numero / 100;
		StringBuffer buf = new StringBuffer();
		if (numero != 0) {
			if (centena != 0) {
				if ((dezena == 0) && (centena == 1)) {
					buf.append(numeros[2][0]);
				} else {
					buf.append(numeros[2][centena]);
				}
			}

			if ((buf.length() > 0) && (dezena != 0)) {
				buf.append(" e ");
			}
			if (dezena > 19) {
				dezena /= 10;
				buf.append(numeros[1][dezena - 2]);
				if (unidade != 0) {
					buf.append(" e ");
					buf.append(numeros[0][unidade]);
				}
			} else if (centena == 0 || dezena != 0) {
				buf.append(numeros[0][dezena]);
			}

			buf.append(" ");
			if (numero == 1) {
				buf.append(qualificadores[escala][0]);
			} else {
				buf.append(qualificadores[escala][1]);
			}
		}

		return buf.toString();
	}

}
