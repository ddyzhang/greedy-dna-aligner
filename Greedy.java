import java.util.*;

public class Greedy {
	
	// add empty space so characters start at 1
	static String a = " AATTATGG";
	static String b = " AATTAAGG";

	static int M = a.length();
	static int N = b.length();

	// Match, mismatch, and indel scores
	static int mat = 2;
	static int mis = -1;
	static int ind = -2;

	// Predefined X for X-drop
	static double X = 2;

	static double TPrime = 0;
	static double T = 0;
	
	// Default constructor 
	public Greedy() {
		
	}
	
	public static int SPrime(int i, int j, int d) {
		return ((i*j)*mat/2) - d*(mat-mis); 
	}
	
	public static void main(String[] args) {
		int i=0;
		int[][] R = new int[Math.max(M, N)][Math.max(M, N)];
		int[] T = new int[Math.max(M, N)];
		while ((i<Math.min(M, N)) && (a.charAt(i+1) == b.charAt(i+1))) {
			i++;
		}
		R[0][0] = i;
		int TPrime = Greedy.SPrime(i, i, 0);
		T[0] = TPrime;
		int d = 0;
		int L = 0;
		int U = 0;
		while (!(L > U+2)) {
			d++;
			int dprime = (int)Math.max((d - Math.floor((X+mat/2) / (mat-mis)) - 1), 0);
			for (int k=Math.max(0,L-1); k<=U+1; k++) {
				// Not too sure about this line here. What are we maxing here? Line 10 on pseudo
				if (L < k) {
					i = R[d-1][k-1] + 1;
				} else if ((L <= k) && (k <= U)) {
					i = R[d-1][k];
				} else if (k < U) {
					i = R[d-1][k+1];
				}
				int j = i-k;
				if ((i > Integer.MIN_VALUE) && (Greedy.SPrime(i, j, d) >= (T[dprime]-X))) {
					while((i<M) && (j<N) && (a.charAt(i+1) == b.charAt(j+1))) {
						i++;
						j++;
					}
					R[d][k] = i;
					TPrime = Math.max(TPrime, Greedy.SPrime(i, j, d));
				} else {
					R[d][k] = Integer.MIN_VALUE;
				}
			}
			T[d] = TPrime;
			
			// I think this is the right range for k
			int tempMax = Integer.MIN_VALUE;
			int tempMin = Integer.MAX_VALUE;
			for (int k=Math.max(0,L-1); k<=U+1; k++) {
				if (R[d][k] > Integer.MIN_VALUE) {
					tempMin = Math.min(tempMin, R[d][k]);
					tempMax = Math.max(tempMax, R[d][k]);
				}
			}
			if (tempMax != Integer.MIN_VALUE) 
				L = tempMin;
			if (tempMin != Integer.MIN_VALUE)
				U = tempMax;
			tempMax = 0;
			tempMin = 0;
			
			for (int k=Math.max(0,L-1); k<=U+1; k++) {
				if (R[d][k] == N+k) {
					tempMax = (Math.max(tempMax, R[d][k]));
				}
				if (R[d][k] == M) {
					tempMin = (Math.min(tempMax, R[d][k]));
				}
			}
			L = Math.max(L, tempMax+2);
			U = Math.min(U, tempMin-2);
			System.out.println("L and U is: " + L + " " + U);
		}
		System.out.println("T Prime is: " + TPrime);	
	}
}