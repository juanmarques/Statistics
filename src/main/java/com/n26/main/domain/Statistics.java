package com.n26.main.domain;

/**
 * Created by JuanM on 05/12/2018.
 */
public final class Statistics {

	private final double sum;
	private final double avg;
	private final double max;
	private final double min;
	private final long count;

	public Statistics(double sum, double avg, double max, double min, long count) {
		super();
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}

	public double getSum() {
		return sum;
	}

	public double getAvg() {
		return avg;
	}

	public double getMax() {
		return max == Double.POSITIVE_INFINITY || max == Double.NEGATIVE_INFINITY ? 0 : max;
	}

	public double getMin() {
		return min == Double.NEGATIVE_INFINITY || min == Double.POSITIVE_INFINITY ? 0 : min;
	}

	public long getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "Statistics{" + "sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count=" + count
				+ '}';
	}
}
