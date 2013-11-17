package andy.dp.upper_intermediate;

import andy.util.Log;

public class QuickSum {
	private static void doit(String str, int sum) {
		int N = str.length();
		int MaxSum = 101;
		int[][][] mins = new int[N][N][MaxSum];
		// initialization
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int k = 0; k < MaxSum; k++) {
					mins[i][j][k] = -1;
				}
			}
			mins[i][i][charToInt(str.charAt(i))] = 0;
		}
		for (int step = 1; step <= N - 1; step++) {
			for (int start = 0; start + step < N; start++) {
				int end = start + step;
				/**
				 * need to regard start~end as a total integer
				 */
				int great=charsToInt(str, start, end);
				if(great<MaxSum){
					mins[start][end][great]=0;
				}
				for (int i = start; i < end; i++) {
					for (int s1 = 0; s1 < MaxSum; s1++) {
						if (mins[start][i][s1] == -1) {
							continue;
						}
						for (int s2 = 0; s2 < MaxSum; s2++) {
							if (mins[i + 1][end][s2] == -1) {
								continue;
							}
							int t = s1 + s2;
							if(t>=MaxSum){
								continue;
							}
							int adds = 1 + mins[start][i][s1]
									+ mins[i + 1][end][s2];
							if (mins[start][end][t] == -1) {
								mins[start][end][t] = adds;
							} else {
								if (mins[start][end][t] > adds) {
									mins[start][end][t] = adds;
								}
							}
						}
					}
				}
			}
		}
		Log.en("min additions:"+mins[0][N-1][sum]);
	}

	private static int charToInt(char c) {
		return c - '0';
	}
	
	private static int charsToInt(String cs, int start, int end){
		int res=0;
		for(int i=start;i<=end;i++){
			res=res*10+charToInt(cs.charAt(i));
		}
		return res;
	}
	
	public static void main(String[]args){
		doit("99999",45);
		doit("1110",3);
		doit("0123456789",45);
		doit("99999",100);
		doit("382834",100);
		doit("9230560001",71);
	}
}
