package com.github.dwiechert.models;

/**
 * Simple pair class.
 * <p/>
 * Found online - http://stackoverflow.com/a/8446411/864369
 * 
 * @author Dan Wiechert
 *
 * @param <K>
 *            Element one's type.
 * @param <V>
 *            Element two's type.
 */
public class Pair<K, V> {
	private final K element1;
	private final V element2;

	public static <K, V> Pair<K, V> of(final K element1, final V element2) {
		return new Pair<K, V>(element1, element2);
	}

	public Pair(final K element1, final V element2) {
		this.element1 = element1;
		this.element2 = element2;
	}

	public K getElement1() {
		return element1;
	}

	public V getElement2() {
		return element2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (element1 == null ? 0 : element1.hashCode());
		result = prime * result + (element2 == null ? 0 : element2.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Pair)) {
			return false;
		}
		final Pair<?, ?> other = (Pair<?, ?>) obj;
		if (element1 == null) {
			if (other.element1 != null) {
				return false;
			}
		} else if (!element1.equals(other.element1)) {
			return false;
		}
		if (element2 == null) {
			if (other.element2 != null) {
				return false;
			}
		} else if (!element2.equals(other.element2)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pair [element1=" + element1 + ", element2=" + element2 + "]";
	}
}
