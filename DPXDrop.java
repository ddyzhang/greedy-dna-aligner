import java.util.*;
import java.io.*;
import java.lang.*;

public class DPXDrop {

	private static double s[][];
	
	public static void main(String[] args) {
		String a = "";
		String b = "";

		// Match, mismatch, and indel scores
		int mat = 0;
		int mis = 0;
		int ind = 0;

		// Predefined X for X-drop
		int X = 0;

		int stringLength = 0;
		int TPrime = 0;
		int T = 0;

		// Since we are dealing with decimals in our indices, we double the array size
		s = new double[stringLength*2][stringLength*2];
		s[0][0] = 0;
		int k = 0;
		double L = 0;
		double U = 0;
		while (L <= (U+1)) {
			k++;
			for (double i = Math.ceil(L); i <= (Math.floor(U) + 1); i = i + 0.5) {
				double j = k - i;
				if (isInteger(i)) {
					// TODO First DP recurrence
					double max = 0;
					double first = 0;
					double second = 0;
					double third = 0;
					double fourth = 0;
					if (L <= (i-0.5) && (i-0.5) <= U && a.charAt(i) == b.charAt(j)) {
						first = S(i-0.5, j-0.5) + (mat/2);
					}
					if (L <= (i-0.5) && (i-0.5) <= U && a.charAt(i) != b.charAt(j)) {
						second = S(i-0.5, j-0.5) + (mis/2);
					}
					if (i <= U) {
						third = S(i, j-1) + ind;
					}
					if (L <= i-1) {
						fourth = S(i-1, j) + ind;
					}
					max = Math.max(first, Math.max(second, Math.max(third, fourth)));
					writeS(max, i, j);
				} else {
					// TODO Second DP recurrence
					if (a.charAt((int)i+0.5) == b.charAt((int)j+0.5)) {
						writeS(S(i-0.5, j-0.5) + (mat/2), i, j);
					} else {
						writeS(S(i-0.5, j-0.5) + (mis/2), i, j);
					}
				}
				TPrime = Math.max(TPrime, S(i, j));
				if (S(i, j) < (T - X)) {
					writeS(Double.NEGATIVE_INFINITY, i, j);
				}
			}
					
		}
		System.out.println(TPrime);
	}

	private static boolean isInteger(double x) {
		return ((x == Math.floor(x)) && !Double.isInfinite(x));
	}

	private static double S(double i, double j) {
		return s[(int)i*2][(int)j*2];
	}

	private static void writeS(double value, double i, double j) {
		s[(int)i*2][(int)j*2] = value;
	}

}