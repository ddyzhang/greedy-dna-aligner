import java.util.*;

public class Greedy {
	
	static String a = "ACTTCTACATA";
	static String b = "ACTTCTACTTC";

	static int M = a.length()-1;
	static int N = b.length()-1;

	// Match, mismatch, and indel scores
	static int mat = 4;
	static int mis = -2;
	static int ind = -4;

	// Predefined X for X-drop
	static double X = 2;

	static double TPrime = 0;
	static double T = 0;
	
	// Default constructor 
	public Greedy() {
		
	}
	
	public static int SPrime(int i, int j, int d) {
		return ((i+j)*mat/2) - d*(mat-mis); 
	}
	
	public static void main(String[] args) {
		int i=0;
		
		//initializing all values to -infinity for prunning
		int[][] R = new int[Math.max(M, N)+1][Math.max(M, N)+1];
		for(int x = 0; x <= Math.max(M, N); x++){
			for(int y = 0; y <= Math.max(M, N); y++){
				R[x][y] = Integer.MIN_VALUE;
			}
		}
		
		int[] T = new int[Math.max(M, N) + 1];
		while ((i<Math.min(M, N)) && (a.charAt(i) == b.charAt(i))) {
			i++;
		}
		R[0][0] = i;
		int TPrime = Greedy.SPrime(i, i, 0);
		T[0] = TPrime;
		int d = 0;
		int L = 0;
		int U = 0;
		while ((L > U + 2)) {
			d++;
			int dprime = (int)Math.max((d - Math.floor((X+mat/2) / (mat-mis)) - 1), 0);
			for (int k=Math.max(0,L-1); k<=U+1; k++) {
				int firstI= Integer.MIN_VALUE;
				int secondI= Integer.MIN_VALUE;
				int thirdI= Integer.MIN_VALUE;
				
				if (L < k) {
					firstI = R[d-1][k-1] + 1;
				} 
				if ((L <= k) && (k <= U)) {
					secondI = R[d-1][k] + 1;
				} 
				if (k < U) {
					thirdI = R[d-1][k+1];
				}
				
				i = Math.max(firstI, Math.max(secondI, thirdI));
				
				int j = i-k;
				if ((i > Integer.MIN_VALUE) && (Greedy.SPrime(i, j, d) >= (T[dprime]-X))) {
					while((i<M - 1) && (j<N - 1) && (a.charAt(i) == b.charAt(j))) {
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
			
			
			//line 19 of psuedocode
			for(int k = 0; k <= Math.max(M, N); k++){
				if(R[d][k] > Integer.MIN_VALUE){
					L = k;
					break;
				}
			}
			//line 20 of psuedocode
			for(int k = Math.max(M, N); k >= 0; k--){
				if(R[d][k] > Integer.MIN_VALUE){
					U = k;
					break;
				}
			}
			
			//line 21 of psuedocode
			for(int k = Math.max(M, N); k >= 0; k--){
				if(R[d][k] == N + k){
					L = Math.max(L, k + 2);
					break;
				}
			}
			
			//line 22 of psuedocode
			for(int k = 0; k <= Math.max(M, N); k++){
				if(R[d][k] == M){
					U = Math.min(U, k - 2);
					break;
				}
			}
			
		}
		System.out.println("T Prime is: " + TPrime);	
	}
}