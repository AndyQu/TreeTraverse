package andy.dp.elementry;

import andy.util.Log;

public class BadNeighbor {
	public static int doit(int[]donations){
		int N=donations.length;
		int[] p00=new int[N];
		int[] p01=new int[N];
		int[] p10=new int[N];
		int[] p11=new int[N];

		for(int i=0;i<N;i++){
			p00[i]=0;
			p01[i]=0;
			p10[i]=0;
			p11[i]=0;
		}
		
		p00[0]=0;
		p11[0]=donations[0];
		p01[0]=0;//invalid state
		p10[0]=0;//invalid state

		p00[1]=0;
		p01[1]=donations[1];
		p10[1]=donations[0];
		p11[1]=0;//invalid state
		
		for(int i=2;i<N;i++){
			int prev=i-1;
			int far=i-2;
			p00[i]=Math.max(
					Math.max(p00[prev],p01[prev]),
					Math.max(p00[far],p01[far]));
			p01[i]=Math.max(
					p00[prev]+donations[i],
					Math.max(p01[far]+donations[i], p00[far]+donations[i]));
			p10[i]=Math.max(
					Math.max(p10[prev], p11[prev]),
					Math.max(p10[far], p11[far])
					);
			if(i!=N-1){
				p11[i]=Math.max(
					p10[prev]+donations[i],
					Math.max(p10[far]+donations[i], p11[far]+donations[i])
					);
			}
		}
		
		return Math.max(
				p00[N-1], 
				Math.max(p01[N-1], p10[N-1]));
	}
	
	public static void main(String[]args){
		Log.en(""+doit(new int[]{10, 3, 2, 5, 7, 8}));
		Log.en(""+doit(new int[]{11, 15 }));
		Log.en(""+doit(new int[]{ 7, 7, 7, 7, 7, 7, 7}));
		Log.en(""+doit(new int[]{ 1, 2, 3, 4, 5, 1, 2, 3, 4, 5}));
		Log.en(""+doit(new int[]{94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,  
				  6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
				  52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72 }));
	}
}
