package com.ikt.t01.entities;

public class MinMax {

	private int min, max;

	public MinMax(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}
	
	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String toString() {
		return "Minimalna vrednost: " + min + ", maksimalna vrednost: " + max;
	}
}
