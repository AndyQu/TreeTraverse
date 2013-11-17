package andy.dp.upper_intermediate;

import andy.util.Log;

/*
 * The first function minStrokes() is taken from http://apps.topcoder.com/forums/?module=Thread&threadID=593452&start=0&mc=7.
 * and produces correct answers for http://community.topcoder.com/stat?c=problem_statement&pm=1215&rd=4555.
 * 
 * The second function is written by myself. It generates 27 instead of 26 for the last test case.
 * I have not figure out the reason.
 */
public class StripePainter {
	private static void minStrokes(String stripes)
	{
		char s[] = stripes.toCharArray();
		int n = s.length;
		int dp[][] = new int[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++)
				dp[i][j]=n+10;
		}
	 
		//initializing D(R), D(G), D(B) ...
		for(int i=0;i<n;i++) dp[i][i] = 1;
	 
		//length of subproblem = i+1
		for(int i=1;i<n;i++)
			//j -> start of current string we evaluate
			for(int j=0;j+i<n;j++)
				//k -> where we break string[j,j+i] into [j,k] [k+1,j+i]
				for(int k=j;k<j+i;k++)
					dp[j][j+i] = Math.min(dp[j][j+i], dp[j][k] + dp[k+1][j+i]
							- (s[j] == s[j+i] ? 1 : 0)); // saving cost
	 
		Log.en("sample code minimum steps:"+dp[0][n-1]);
	}
	
	private static void doit(String stripes){
		
		char[]str=stripes.toCharArray();
		int N=str.length;
		int[][]minCount=new int[N][N];
//		int[][]rows=new int[N][N];
//		int[][]cols=new int[N][N];
//		for(int i=0;i<N;i++){
//			for(int j=0;j<N;j++){
//				rows[i][j]=-1;
//				cols[i][j]=-1;
//			}
//		}
		//initial
		for(int i=0;i<N;i++){
			minCount[i][i]=1;
			for(int j=i+1;j<N;j++){
				minCount[i][j]=N+10;
			}
		}
		
		for(int dist=1;dist<=N-1;dist++){
			for(int i=0;i+dist<N && i<N ;i++){
				if(str[i]!=str[i+dist]){
					//paint head
					char v=str[i];
					int start=i;
					while(start<=i+dist && str[start]==v){
						start++;
					}
					if(minCount[start][i+dist]+1<minCount[i][i+dist]){
//						rows[i][i+dist]=start;
//						cols[i][i+dist]=i+dist;
						minCount[i][i+dist]=minCount[start][i+dist]+1;
					}
					
					//paint tail
					v=str[i+dist];
					int end=i+dist;
					while(end>=i && str[end]==v){
						end--;
					}
					if(minCount[i][end]+1<minCount[i][i+dist]){
//						rows[i][i+dist]=i;
//						cols[i][i+dist]=end;
						minCount[i][i+dist]=minCount[i][end]+1;
					}
				}else{
					char v=str[i];
					int start=i;
					while(start<=i+dist && str[start]==v){
						start++;
					}
					int end=i+dist;
					while(end>=i && str[end]==v){
						end--;
					}
					if(start<=end){
						minCount[i][i+dist]=Math.min(minCount[i][i+dist], 1+minCount[start][end]);
						for(int k=start;k<=end;k++){
							if(str[k]==v){
								minCount[i][i+dist]=Math.min(
										minCount[i][i+dist], 
										minCount[i][k]+minCount[k][i+dist]-1);
							}
						}
					}else{
						minCount[i][i+dist]=1;
					}
				}
			}
		}
		Log.en("minimum steps:"+minCount[0][N-1]);
	}
	
	public static void main(String[]args){
//		doit("RGBGR");
//		doit("RGRG");
//		doit("ABACADA");
//		doit("AABBCCDDCCBBAABBCCDD");
		//doit("BECBDEBABDCADEAEABCACBDBECDEDEACACBEDABEDAD");
		minStrokes("BECBBDDEEBABDCADEAAEABCACBDBEECDEDEACACCBEDABEDADD");
		doit("BECBBDDEEBABDCADEAAEABCACBDBEECDEDEACACCBEDABEDADD");
		doit("RGR");
		//doit("ABCADEAFGAJKA");
		//////BECB D E BABDCADEA EABCACBDBE CDEDEACAC BEDABEDAD
	}
}
