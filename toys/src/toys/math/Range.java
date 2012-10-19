/*
 * Criado em 04/11/2005
 */

package toys.math;

/**
 * Bean para armazenamento de faixas de valores numéricos.
 * @author Iran Marcius
 */
public class Range {
	private Object lo;
	private Object hi;
	
	public Range(long lo, long hi) {
		this.lo = Long.valueOf(lo);
		this.hi = Long.valueOf(hi);
	}
	
	public boolean contains(long value) {
		if ((lo instanceof Long) && (hi instanceof Long)) {
			return (value >= ((Long)lo).longValue())
				&& (value <= ((Long)hi).longValue());
		} else {
			return false;
		}
	}
	
	public long getLongLo() {
		return ((Long)lo).longValue();
	}
	
	public long getLongHi() {
		return ((Long)hi).longValue();
	}
	
}
