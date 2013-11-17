package andy.dp.elementry;

import andy.util.Log;

public class knapsack {
	private static void doit(int[]weights){
		int Sum=0;
		int N=weights.length;
		for(int i=0;i<N;i++){
			Sum+=weights[i];
		}
		int[][]count=new int[N][Sum+1];
		count[0][0]=1;
		for(int j=1;j<=Sum;j++){
			if(j==weights[0]){
				count[0][j]=1;
			}else{
				count[0][j]=0;
			}
		}
		for(int i=1;i<N;i++){
			for(int s=0;s<=Sum;s++){
					count[i][s]=count[i-1][s];
					if(s>=weights[i]){
						count[i][s]+=count[i-1][s-weights[i]];
					}
			}
		}
		for(int i=0;i<=Sum;i++){
			Log.en("number of sum "+i+":"+count[N-1][i]);
		}
	}
	
	private static void doitQuick(int[]weights){
		int Sum=0;
		int N=weights.length;
		for(int i=0;i<N;i++){
			Sum+=weights[i];
		}
		int[]count=new int[Sum+1];
		for(int i=1;i<Sum+1;i++){
			count[i]=0;
		}
		count[0]=1;
		
		for(int i=1;i<N;i++){
			for(int s=Sum;s>=0;s--){
				if(s>=weights[i]){
					count[s]+=count[s-weights[i]];
				}
			}
		}
		for(int i=0;i<=Sum;i++){
			Log.en("number of sum "+i+":"+count[i]);
		}
	}
	public static void main(String[]args){
		int[]weights=new int[]{2,3,3,3,3,4,5,12,43,23};
		doitQuick(weights);
		Log.en("===============");
		doit(weights);
	}
}
