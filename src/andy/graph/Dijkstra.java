package andy.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Dijkstra�㷨����һ�����work��
 * 1.ÿ���ߵ�weight>0
 * 2.���ڱߵ�weight<0�����ǲ����ڸ�ֵ�Ļ�
 *
 */
public class Dijkstra {
	public static final int Infinite=999999;
	private static int NotVisited=0;
	private static final int NeighborNotVisited=1;
	private static final int NeighborVisited=2;
	/*
	 * This algorithm uses broad first search, which is wrong.
	 */
	public static void doit_wrong(int[][] array){
		int n=array[0].length;
		int[] weight=new int[n];
		for(int i=0;i<weight.length;i++){
			weight[i]=Infinite;//infinite
		}
		int[] preIndex=new int[n];
		for(int i=0;i<preIndex.length;i++){
			preIndex[i]=-1;
		}
		int[] status=new int[n];
		//status 0:not visited
		//status 1:visited,adjacent nodes not visited
		//status 2:visited,adjacent nodes visited
		
		
		
		weight[0]=0;
		status[0]=NeighborNotVisited;
		Queue<Integer> borderList=new LinkedList<Integer>();
		borderList.add(0);
		
		while(!borderList.isEmpty()){
			int cursor=borderList.remove();
			int[]neighbors=array[cursor];
			for(int child=0;child<neighbors.length;child++){
				int w=neighbors[child];
				if(w>=Infinite){
					continue;
				}
				if(w+weight[cursor]<weight[child]){
					weight[child]=w+weight[cursor];
					preIndex[child]=cursor;
				}
				if(status[child]==NotVisited){
					status[child]=NeighborNotVisited;
					borderList.add(child);
					log("enqueue:"+child);
				}
			}
			status[cursor]=NeighborVisited;
		}
		
		for(int i=0;i<n;i++){
			int index=i;
			log("node:"+(index+1)+",weight:"+weight[index]);
			while(index>=0){
				log(""+(index+1));
				index=preIndex[index];
			}
		}
	}
	
	
	
	public static void doit_right(int[][]array){
		int n=array[0].length;
		int[] weight=new int[n];
		for(int i=0;i<weight.length;i++){
			weight[i]=Infinite;//infinite
		}
		int[] preIndex=new int[n];
		for(int i=0;i<preIndex.length;i++){
			preIndex[i]=-1;
		}
//		boolean[] status=new boolean[n];
//		for(int i=0;i<status.length;i++){
//			status[i]=false;
//		}
		
		weight[0]=0;
		//status[0]=false;
		Set<Integer>unvisited=new HashSet<Integer>();
		unvisited.add(0);
		while(!unvisited.isEmpty()){
			//find the minimum element
			Integer[]tmp=new Integer[1];
			tmp=(Integer[])unvisited.toArray(tmp);
			int min=tmp[0];
			Integer cursor=tmp[0];
			for(int i=1;i<tmp.length;i++){
				if(weight[tmp[i]]<min){
					min=weight[tmp[i]];
					cursor=tmp[i];
				}
			}
			
			unvisited.remove(cursor);
			log("dequeue:"+cursor);
			int[]childs=array[cursor];
			for(int child=0;child<childs.length;child++){
				int w=childs[child];
				if(weight[child]>weight[cursor]+w){
					weight[child]=weight[cursor]+w;
					preIndex[child]=cursor;
					unvisited.add(child);
					
					//log("enqueue:"+child);
				}
			}
		}
		
		for(int i=0;i<n;i++){
			int index=i;
			log("node:"+(index+1)+",weight:"+weight[index]);
			while(index>=0){
				log(""+(index+1));
				index=preIndex[index];
			}
		}
	}
	
	/*
	 * ��������ڽ���̰���㷨Ѱ�����·����ʱ��Ϊ�˱������negative weight�γɵĻ���
	 * ���·���ϵĽڵ�����жϡ����Ҫ������½ڵ���·�����Ѿ����ڣ����ټ�������ڵ㡣
	 * 
	 * ����㷨�Ǵ���ģ������������ڵ�:A��B��C��dist(A,B)=41, dist(A,C)=40,dist(B,C)=-2��
	 * ����������㷨�Ľ����
	 *  min_dist(A,B)=38,path(A,B)=A->C->B;
	 *  min_dist(A,C)=40,path(A,C)=A->C��
	 *  min_dist(A,B)����ȷ�ģ���min_dist(A,C)�Ǵ���ġ�
	 *  ��ȷ���ǣ�
	 *  min_dist(A,C)=39,path(A,C)=A->B->C��
	 *  
	 *  �ó�����𰸵�ԭ���ǣ����ڲ�ȡ��̰���㷨�������޷��ҳ�A->B->C����·����A��Զ����ѡ��B��Ϊ��һ���ڵ㡣
	 * 
	 */
	public static void doit_wrong_with_negative_weight(int[][]array){
		int n=array[0].length;
		int[] weight=new int[n];
		for(int i=0;i<weight.length;i++){
			weight[i]=Infinite;//infinite
		}
		int[] preIndex=new int[n];
		for(int i=0;i<preIndex.length;i++){
			preIndex[i]=-1;
		}
//		boolean[] status=new boolean[n];
//		for(int i=0;i<status.length;i++){
//			status[i]=false;
//		}
		
		weight[0]=0;
		//status[0]=false;
		Set<Integer>unvisited=new HashSet<Integer>();
		unvisited.add(0);
		while(!unvisited.isEmpty()){
			//find the minimum element
			Integer[]tmp=new Integer[1];
			tmp=(Integer[])unvisited.toArray(tmp);
			int min=tmp[0];
			Integer cursor=tmp[0];
			for(int i=1;i<tmp.length;i++){
				if(weight[tmp[i]]<min){
					min=weight[tmp[i]];
					cursor=tmp[i];
				}
			}
			
			unvisited.remove(cursor);
			log("dequeue:"+cursor);
			int[]childs=array[cursor];
			for(int child=0;child<childs.length;child++){
				int w=childs[child];
				if(weight[child]>weight[cursor]+w){
					//first,check whether the path already contains "child"
					int t=cursor;
					while(t!=child && t>=0){
						t=preIndex[t];
					}
					if(t==child)//found
						continue;
						
					weight[child]=weight[cursor]+w;
					preIndex[child]=cursor;
					unvisited.add(child);
					
					//log("enqueue:"+child);
				}
			}
		}
		
		for(int i=0;i<n;i++){
			int index=i;
			log("node:"+(index+1)+",weight:"+weight[index]);
			while(index>=0){
				log(""+(index+1));
				index=preIndex[index];
			}
		}
	}
	
	
	public static void main(String[]args){
		//doit_wrong(makeUpGraph_that_proves_upper_function_wrong());
		//doit_right(makeUpGraph_that_proves_upper_function_wrong());
		//log("");
		//doit_right(makeUpGraph());
		doit_right(makeUpGraph_with_negative_without_cycle());
	}
	
	public static int[][] makeUpGraph_with_negative_without_cycle(){
		int[][]array={
				{0,			15,			10,			Infinite,	Infinite,	21},
				{15,		0,			19,			33,			1,			Infinite},
				{10,		19,			0,			27,			Infinite,	Infinite},
				{Infinite,	33,			27,			0,			3,			45},
				{Infinite,	1,			Infinite,	3,			0,			21},
				{-20,		Infinite,	Infinite,	45,			21,		0}
				
		};
		return array;
	}
	
	public static int[][] makeUpGraph(){
		int[][]array={
				{0,			15,			10,			Infinite,	Infinite,	1},
				{15,		0,			19,			33,			1,			Infinite},
				{10,		19,			0,			27,			Infinite,	Infinite},
				{Infinite,	33,			27,			0,			3,			45},
				{Infinite,	1,			Infinite,	3,			0,			1},
				{1,			Infinite,	Infinite,	45,			1,			0}
				
		};
		return array;
	}
	
	public static int[][]	makeUpGraph_that_proves_first_function_wrong(){
		int[][]array={
				{0,			100,		Infinite,	Infinite,	Infinite,	1},
				{100,		0,			4,			Infinite,	Infinite,	Infinite},
				{Infinite,	4,			0,			4,			Infinite,	Infinite},
				{Infinite,	Infinite,	4,			0,			1,			Infinite},
				{Infinite,	Infinite,	Infinite,	1,			0,			1},
				{1,			Infinite,	Infinite,	Infinite,	1,			0}
				
		};
		return array;
	}
	public static void log(String s){
		System.out.println(s);
	}
}
