package andy.dp.upper_intermediate;

import java.util.HashMap;
import java.util.Map;

import andy.util.Log;
import andy.util.QuickSort;

/**
 * This algorithm is not correct. Need to be modified for cases where there exist equal elements.
 * @author aichongqu
 *
 */
public class Jewelry {
	private static long doitCorrect(int[]items){
		//compute combinations
		int N=items.length;
		long[][]combinations=new long[N+1][N+1];
		for(int i=1;i<N+1;i++){
			combinations[i][1]=i;
			for(int j=2;j<=i;j++){
				combinations[i][j]=combinations[i-1][j]+combinations[i-1][j-1];
			}
			for(int j=i+1;j<N+1;j++){
				combinations[i][j]=0;
			}
		}
		
		//compute Max Sum
		int Sum=0;
		for(int i=0;i<N;i++){
			Sum+=items[i];
		}
		
		//sort
		QuickSort.doit(items, true);
		
		//compute left part sums
		int[][]left_weight=new int[N][Sum+1];
		
		for(int s=0;s<Sum+1;s++){
			if(items[0]==s){
				left_weight[0][s]=1;
			}else{
				left_weight[0][s]=0;
			}
		}
		left_weight[0][0]=1;
		for(int i=1;i<N;i++){
			for(int s=0;s<Sum+1;s++){
				left_weight[i][s]=left_weight[i-1][s];
				if(s>=items[i]){
					left_weight[i][s]+=left_weight[i-1][s-items[i]];
				}
			}
		}
		
		//compute right part sums
		int[][]right_weight=new int[N][Sum+1];
		
		for(int s=0;s<Sum+1;s++){
			if(items[N-1]==s){
				right_weight[N-1][s]=1;
			}else{
				right_weight[N-1][s]=0;
			}
		}
		right_weight[N-1][0]=1;
		for(int i=N-2;i>=0;i--){
			for(int s=0;s<Sum+1;s++){
				right_weight[i][s]=right_weight[i+1][s];
				if(s>=items[i]){
					right_weight[i][s]+=right_weight[i+1][s-items[i]];
				}
			}
		}
		for(int i=0;i<N;i++){
			for(int s=0;s<Sum+1;s++){
				//Log.enn(right_weight[i][s]+",");
			}
			//Log.en("");
		}
		
		long total=0;
		for(int left_end=0;left_end<=N-2;){
			int v=items[left_end];
			int count=1;
			while(left_end+count<=N-1 && items[left_end+count]==v){
				count++;
			}
			//TODO
			for(int j=left_end;j<=left_end+count-1 && j+1<N;j++){
				int num=j-left_end+1;
				for(int s=num*v;s<Sum+1;s++){
					if(left_end>=1){
						int left_count=left_weight[left_end-1][s-num*v];
						total+=combinations[count][num]
							*left_count
							*right_weight[j+1][s];
					}else{
						total+=combinations[count][num]
								*right_weight[j+1][num*v];
						break;
					}
				}
			}
			left_end+=count;
		}
		return total;
	}
	
	private static long doit(int[]items){
		QuickSort.doit(items, true);
		Log.en("After quick sort:");
		for(int i=0;i<items.length;i++){
			Log.en(""+items[i]);
		}
		int total=0;
		for(int i=0;i<items.length;){
			int j=i+1;
			while(j<items.length && items[i]==items[j]){
				j++;
			}
			int N=j-i;
			total=N*(N-1)/2;
			i=j;
		}
		
		Map<Integer,Integer>left_m1=new HashMap<Integer,Integer>();
		Map<Integer,Integer>left_m2=new HashMap<Integer,Integer>();
		left_m1.put(items[0],1);
		left_m2.put(items[0], 1);
		int left_sum=items[0];
		for(int i=1;i<items.length;i++){
			//compute right part sums
			Map<Integer,Integer>right_m1=new HashMap<Integer,Integer>();
			Map<Integer,Integer>right_m2=new HashMap<Integer,Integer>();
			right_m1.put(items[i], 1);
			right_m2.put(items[i], 1);
			for(int j=i+1;j<items.length && items[j]<=left_sum;j++){
				Integer base=right_m2.get(items[j]);
//				if(base!=null){
//					right_m2.put(items[j], 1+base);
//				}else{
//					right_m2.put(items[j], 1);
//				}
				for(Integer k:right_m1.keySet()){
					int count=right_m1.get(k);
					int nk=k+items[j];
					int nCount=count;
					base=right_m2.get(nk);
					if(base!=null){
						nCount+=base;
					}
					right_m2.put(nk, nCount);
				}
				right_m1.clear();
				right_m1.putAll(right_m2);
			}
			//compute equals,add to total
			for(Integer sum:left_m1.keySet()){
				Integer count=right_m1.get(sum);
				if(count!=null){
					total=total+left_m1.get(sum)*right_m1.get(sum);
				}
			}
			
			//compute left part sums
			Integer base =left_m2.get(items[i]);
			if(base!=null){
				left_m2.put(items[i], 1+base);
			}else{
				left_m2.put(items[i], 1);
			}
			for(Integer k:left_m1.keySet()){
				int count=left_m1.get(k);
				int nk=k+items[i];
				int nCount=count;
				base=left_m2.get(nk);
				if(base!=null){
					nCount+=base;
				}
				left_m2.put(nk, nCount);
			}
			left_m1.clear();
			left_m1.putAll(left_m2);
			//left sum
			left_sum+=items[i];
		}
		return total;
	}
	public static void main(String[]args){
		long total;
//		total=doit(new int[]{1,2,5,3,4,5});
//		Log.en("valid:"+total);
//		
//		total=doit(new int[]{1000,1000,1000,1000,1000,
//				 1000,1000,1000,1000,1000,
//				 1000,1000,1000,1000,1000,
//				 1000,1000,1000,1000,1000,
//				 1000,1000,1000,1000,1000,
//				 1000,1000,1000,1000,1000});
//		Log.en("valid:"+total+"\n");
//		
//		total=doit(new int[]{1,2,3,4,5});
//		Log.en("valid:"+total+"\n");
//		
//		total=doit(new int[]{7,7,8,9,10,11,1,2,2,3,4,5,6});
//		Log.en("valid:"+total+"\n");
//		
//		total=doit(new int[]{123,217,661,678,796,964,54,111,417,526,917,923});
//		Log.en("valid:"+total+"\n");
		
		
		Log.en("\n\n============");
		total=doitCorrect(new int[]{5,5,5});
		Log.en("valid:"+total);
		
//		total=doitCorrect(new int[]{1,2,5,3,4,5});
//		Log.en("valid:"+total);
//		
		total=doitCorrect(new int[]{1000,1000,1000,1000,1000,
				 1000,1000,1000,1000,1000,
				 1000,1000,1000,1000,1000,
				 1000,1000,1000,1000,1000,
				 1000,1000,1000,1000,1000,
				 1000,1000,1000,1000,1000});
		Log.en("valid:"+total+"\n");
//		//18252025766940
//		//my answer:366598772992136940
//		
//		total=doitCorrect(new int[]{1,2,3,4,5});
//		Log.en("valid:"+total+"\n");
//		
//		total=doitCorrect(new int[]{7,7,8,9,10,11,1,2,2,3,4,5,6});
//		Log.en("valid:"+total+"\n");
//		
//		total=doitCorrect(new int[]{123,217,661,678,796,964,54,111,417,526,917,923});
//		Log.en("valid:"+total+"\n");
	}
}
