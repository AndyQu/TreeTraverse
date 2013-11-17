package andy.array;

import andy.util.Log;
import andy.util.StringUtil;

public class LCS {
	private static int[] doit(int[] A, int[] B) {
		int[][] length = new int[A.length][B.length];
		int[][] indexA = new int[A.length][B.length];
		int[][] indexB = new int[A.length][B.length];
		int[][] value = new int[A.length][B.length];
		
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B.length; j++) {
				int j_1_size = -1;
				int i_1_size = -1;

				//compute c[i-x][j-1]
				int v = B[j];
				int kA = i;
				for (kA = i; kA >= 0; kA--) {
					if (A[kA] == v) {
						break;
					}
				}
				if (kA >= 1 && j>=1) {
					j_1_size = length[kA-1][j-1];
				} else if (kA == 0) {
					j_1_size = 0;
				}

				//compute c[i-1][j-x]
				v = A[i];
				int kB = j;
				for (kB = j; kB >= 0; kB--) {
					if (B[kB] == v) {
						break;
					}
				}
				if (kB >= 1 && i>=1) {
					i_1_size = length[i - 1][kB-1];
				} else if (kB == 0) {
					i_1_size = 0;
				}

				//max
				length[i][j] = Math.max(j_1_size + 1, i_1_size + 1);
				if (j >= 1) {
					length[i][j] = Math.max(length[i][j], length[i][j - 1]);
				}
				if (i >= 1) {
					length[i][j] = Math.max(length[i][j], length[i - 1][j]);
				}
				if(length[i][j]==j_1_size+1){
					indexA[i][j]=kA-1;
					indexB[i][j]=j-1;
					if(j_1_size+1>0){
						value[i][j]=B[j];
					}
				}else if(length[i][j]==i_1_size+1){
					indexA[i][j]=i-1;
					indexB[i][j]=kB-1;
					if(i_1_size+1>0){
						value[i][j]=A[i];
					}
				}else if(j>=1 && length[i][j]==length[i][j-1]){
					indexA[i][j]=i;
					indexB[i][j]=j-1;
					value[i][j]=-1;
				}else if(i>=1 && length[i][j]==length[i-1][j]){
					indexA[i][j]=i-1;
					indexB[i][j]=j;
					value[i][j]=-1;
				}else{
					Log.en("should not enter this");
				}
			}
		}
		
		int[]result =new int[Math.max(A.length, B.length)];
		int i=0;
		int a=A.length-1;
		int b=B.length-1;
		
		while(true){
			if(a<0||b<0){
				break;
			}
			if(value[a][b]!=-1){
				Log.en("index:"+a+",index:"+b);
				Log.en("size:"+length[a][b]);
				Log.en("value:"+value[a][b]);
				result[i]=value[a][b];
				i++;
			}
			int ta=a;
			int tb=b;
			a=indexA[ta][tb];
			b=indexB[ta][tb];
		}
		return result;
	}
	
	public static void main(String[]args){
		int[]A={70,60,50,90,110,115,119,112};
		int[]B={50,60,70,90,110,112,115,119};
//		int[]A={70,50,60};
//		int[]B={60,50};
//		int[] lcs=LCS.doit(A, B);
//		for(int i=0;i<lcs.length;i++){
//			Log.e(""+lcs[i]);
//		}
		
		Log.en(LCS.doit("MADA","MADAMI"));
	}
	
	public static String doit(String A,String B){
		char[]res=doit(A.toCharArray(),B.toCharArray());
		return new String(res);
	}
	
	public static char[] doit(char[] A, char[] B) {
		int[][] length = new int[A.length][B.length];
		int[][] indexA = new int[A.length][B.length];
		int[][] indexB = new int[A.length][B.length];
		int[][] value = new int[A.length][B.length];
		for(int i=0;i<A.length;i++){
			for(int j=0;j<B.length;j++){
				length[i][j]=0;
				indexA[i][j]=-1;
				indexB[i][j]=-1;
				value[i][j]=-1;
			}
		}
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < B.length; j++) {
				if(i==0&&j==0){
					if(A[0]==B[0]){
						length[0][0]=1;
						indexA[0][0]=-1;
						indexB[0][0]=-1;
						value[0][0]=A[0];
					}
					continue;
				}
				if(i==0){
					if(A[i]==B[j]){
						length[i][j]=1;
						indexA[i][j]=-1;
						indexB[i][j]=-1;
						value[i][j]=A[i];
					}else{
						length[i][j]=length[i][j-1];
						indexA[i][j]=i;
						indexB[i][j]=j-1;
						value[i][j]=-1;
					}
				}else if(j==0){
					if(A[i]==B[j]){
						length[i][j]=1;
						indexA[i][j]=-1;
						indexB[i][j]=-1;
						value[i][j]=A[i];
					}else{
						length[i][j]=length[i-1][j];
						indexA[i][j]=i-1;
						indexB[i][j]=j;
						value[i][j]=-1;
					}
				}else{
					if(A[i]==B[j]){
						length[i][j]=length[i-1][j-1]+1;
						indexA[i][j]=i-1;
						indexB[i][j]=j-1;
						value[i][j]=A[i];
					}else{
						if(length[i-1][j]<length[i][j-1]){
							length[i][j]=length[i][j-1];
							indexA[i][j]=i;
							indexB[i][j]=j-1;
							value[i][j]=-1;
						}else{
							length[i][j]=length[i-1][j];
							indexA[i][j]=i-1;
							indexB[i][j]=j;
							value[i][j]=-1;
						}
					}
				}
			}
		}
		
		StringBuffer result =new StringBuffer();
		int a=A.length-1;
		int b=B.length-1;
		
		while(true){
			if(a<0||b<0){
				break;
			}
			if(value[a][b]!=-1){
				//Log.e("index:"+a+",index:"+b);
				//Log.e("size:"+length[a][b]);
				//Log.e("value:"+value[a][b]);
				result.append((char)value[a][b]);
			}
			int ta=a;
			int tb=b;
			a=indexA[ta][tb];
			b=indexB[ta][tb];
		}
		String res=StringUtil.reverse(result.toString());
		return res.toCharArray();
	}
}
