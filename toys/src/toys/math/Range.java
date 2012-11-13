/*
 * Criado em 04/11/2005
 */

package toys.math;

/**
 * Bean para armazenamento de faixas de valores numéricos.
 * @author Iran Marcius
 */
public class Range<T extends Number> {
	private T min;
	private T max;

	public Range(T lo, T hi) {
		this.min = lo;
		this.max = hi;
	}

	public boolean contains(T value) {
		return value.longValue() >= min.longValue() && value.longValue() <= max.longValue();
	}

	public T getMin() {
		return min;
	}

	public T getMax() {
		return max;
	}

}
