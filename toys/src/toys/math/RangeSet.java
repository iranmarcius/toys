/*
 * Criado em 14/09/2005
 */

package toys.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import toys.beans.pairs.KeyValue;

/**
 * Classe utilitária para gerenciar conjuntos de faixas de valores.
 * @author Iran Marcius
 */
public class RangeSet {
	private List<KeyValue<String, Range>> ranges;
	
	/**
	 * Construtor default.
	 */
	public RangeSet() {
		super();
		ranges = new ArrayList<KeyValue<String, Range>>(); 
	}
	
	/**
	 * Adiciona uma faixa de longs.
	 * @param name Nome da faixa
	 * @param min Valor mánimo 
	 * @param max Valor míximo
	 */
	public void add(String name, long min, long max) {
		ranges.add(new KeyValue<String, Range>(name, new Range(min, max)));
	}
	
	/**
	 * Adiciona uma nova faixa de longs utilizando o valor máximo da faixa
	 * anterior como mínimo da nova faixa adicionada. Se não existir uma
	 * faixa anterior, o valor mínimo será considerado zero.
	 * @param name Nome da faixa
	 * @param max Valor máximo. O valor mínimo será obtido da faixa anteriormente
	 * adicionada e será incrementado do valor especificado
	 * @param increment Valor que será incrementado ao valor máximo da faixa
	 * anterior para se obter o valor mínimo da nova faixa que será criada.
	 */
	public void addFromLast(String name, long max, long increment) {
		long min = 0;
		if (!ranges.isEmpty()) {
			KeyValue<String, Range> p = ranges.get(ranges.size() - 1);
			Range r = p.getValue();
			min = r.getLongHi() + increment;
		}
		add(name, min, max);
	}
	
	/**
	 * Adiciona uma nova faixa de longs utilizando o valor máximo da faixa
	 * anterior como mínimo da nova faixa adicionada. Se não existir uma
	 * faixa anterior, o valor mínimo será considerado zero.
	 * @param name Nome da faixa
	 * @param max Valor máximo. O valor mínimo será obtido da faixa anteriormente
	 * adicionada e será incrementado de um.
	 */
	public void addFromLast(String name, long max) {
		addFromLast(name, max, 1);
	}
	
	/**
	 * Retorna uma lista read-only com as faixas existentes.
	 * @return <code>List&lt;{@link KeyValue KeyValue&lt;String, Range&gt;}&gt;</code>
	 */
	public List<KeyValue<String, Range>> getRanges() {
		return Collections.unmodifiableList(ranges);
	}
	
	/**
	 * Retorna o par de chave/valor da faixa contendo o valor especificado.
	 * @param value Valor a ser procurado
	 * @return <code>{@link KeyValue KeyValue&lt;String, Range&gt;}</code>
	 */
	public KeyValue<String, Range> rangeFromValue(long value) {
		for (KeyValue<String, Range> p: ranges) {
			Range r = (Range)p.getValue();
			if (r.contains(value)) {
				return p;
			}
		}
		return null;
	}
	
}
