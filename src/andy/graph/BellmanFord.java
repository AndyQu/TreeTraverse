package andy.graph;

import andy.util.Log;

/**
 * Bellman-Ford算法在以下情况下work：
 * 不存在负值环
 * 
 * 问题：既然Dijkstra算法和Bellman-Ford算法在不存在负值环的情况下都能work，为什么不选用Dijkstra算法？
 * @author aichongqu
 *
 */
public class BellmanFord {
	public static final int Infinite = 999999;

	/*
	 * this function is the ordinary version of Bellman-Ford Algorithm
	 */
	public static void doit_without_improve(int[][] edges) {
		int N = edges[0].length;
		int[] dist = new int[N];
		int[] prevIndex=new int[N];

		// initialization
		dist[0] = 0;
		for (int i = 1; i < N; i++) {
			dist[i] = Infinite;
		}
		for(int i=0;i<N;i++){
			prevIndex[i]=-1;
		}

		// loop
		for (int loop = 1; loop <= N - 1; loop++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (i == j || edges[i][j] == Infinite) {
						continue;
					}
					if (dist[j] > dist[i] + edges[i][j]) {
						dist[j] = dist[i] + edges[i][j];
						prevIndex[j]=i;
					}
				}
			}
		}
		boolean existNegativeCycle=false;
		//check for negative cycles
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j || edges[i][j] == Infinite) {
					continue;
				}
				if (dist[j] > dist[i] + edges[i][j]) {
					Log.en("negative cycles exist: distance of node("+(j+1)+") can still be reduced from node("+(i+1)+")");
					Log.en(dist[j]+">"+dist[i]+"+"+edges[i][j]);
					existNegativeCycle=true;
				}
			}
		}
		
		if(existNegativeCycle){
			return;
		}
		//print out paths
		for(int i=0;i<N;i++){
			int index=i;
			Log.en("=========node:"+(index+1)+",weight:"+dist[index]);
			while(index>=0){
				Log.en(""+(index+1));
				index=prevIndex[index];
			}
		}
	}
	
	/**
	 * this function is an improved version of above one.
	 * when dist[j] > dist[i] + edges[i][j], we first check whether node j is already on the path to node i.
	 * If so, that means we are entering a negative cycle. Should not update distance and previous indexes under this situation.
	 * 
	 * However,this function may not produce correct answers.
	 * You can use makeUpGraph_with_negative_cycle_B() to feed this function, and the final result is not satisfactory.
	 * @param edges
	 */
	public static void doit_improved_but_not_correct(int[][] edges) {
		int N = edges[0].length;
		int[] dist = new int[N];
		int[] prevIndex=new int[N];

		// initialization
		dist[0] = 0;
		for (int i = 1; i < N; i++) {
			dist[i] = Infinite;
		}
		for(int i=0;i<N;i++){
			prevIndex[i]=-1;
		}

		// loop
		for (int loop = 1; loop <= N - 1; loop++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (i == j || edges[i][j] == Infinite) {
						continue;
					}
					if (dist[j] > dist[i] + edges[i][j]) {
						//check whether node j is on the path of i
						boolean exist=false;
						int index=prevIndex[i];
						while(index>=0){
							if(index==j){
								exist=true;
								break;
							}
							index=prevIndex[index];
						}
						if(exist){
							continue;
						}
						dist[j] = dist[i] + edges[i][j];
						prevIndex[j]=i;
					}
				}
			}
		}
		boolean existNegativeCycle=false;
		//check for negative cycles
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (i == j || edges[i][j] == Infinite) {
					continue;
				}
				if (dist[j] > dist[i] + edges[i][j]) {
					Log.en("negative cycles exist: distance of node("+(j+1)+") can still be reduced from node("+(i+1)+")");
					Log.en(dist[j]+">"+dist[i]+"+"+edges[i][j]);
					existNegativeCycle=true;
				}
			}
		}
		
		if(existNegativeCycle){
			//return;
		}
		//print out paths
		for(int i=0;i<N;i++){
			int index=i;
			Log.en("=========node:"+(index+1)+",weight:"+dist[index]);
			while(index>=0){
				Log.en(""+(index+1));
				index=prevIndex[index];
			}
		}
	}
	
	public static void main(String[]args){
		doit_without_improve(makeUpGraph_with_negative_without_cycle());
		
		doit_improved_but_not_correct(makeUpGraph_with_negative_cycle_A());
		
		doit_improved_but_not_correct(makeUpGraph_with_negative_cycle_B());
	}
	public static int[][] makeUpGraph_with_negative_without_cycle(){
		int[][]array={
				{0,			37,			40},
				{37,		0,			3},
				{40,		-2,			0}
		};
		return array;
	}
	
	public static int[][]makeUpGraph_with_negative_cycle_B(){
		int[][]array={
				{0,41,40},
				{41,0,-2},
				{40,-2,0}
		};
		return array;
	}
	
	public static int[][]makeUpGraph_with_negative_cycle_A(){
		int[][]array={
				{0,			15,			10,			Infinite,	Infinite,	-100},
				{15,		0,			19,			33,			1,			Infinite},
				{10,		19,			0,			27,			Infinite,	Infinite},
				{Infinite,	33,			27,			0,			3,			45},
				{Infinite,	1,			Infinite,	3,			0,			21},
				{-100,			Infinite,	Infinite,	45,			21,		0}
				
		};
		return array;
	}
}
