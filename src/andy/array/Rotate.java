package andy.array;

import andy.util.Log;

public class Rotate {
	private static void doit(int[][]image){
		int N=image[0].length;
		for(int layer=0;layer<N/2;layer++){
			for(int step=0;step<=N-layer*2-1-1;step++){
				int left_bottom=image[layer+step][layer];
				image[layer+step][layer]=image[N-layer-1][layer+step];
				image[N-layer-1][layer+step]=image[N-layer-1-step][N-layer-1];
				image[N-layer-1-step][N-layer-1]=image[layer][N-layer-1-step];
				image[layer][N-layer-1-step]=left_bottom;
			}
		}
		
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				Log.enn(image[i][j]+" ");
			}
			Log.en("");
		}
	}
	public static void main(String[]args){
		doit(new int[][]{
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 10,11,12},
				{13,14,15,16}
		});
		Log.en("================");
		doit(new int[][]{
				{1, 2, 3, 4, 5},
				{6, 7, 8, 9, 10},
				{11,12,13,14,15},
				{16,17,18,19,20},
				{21,22,23,24,25}
		});
	}
}
