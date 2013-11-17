package andy.array;

public class LongestIncreSub {

	public static void doit(int[] array){
		int[]link=new int[array.length];
		int[]result	=new int[array.length+1];
		int[]indexes=new int[array.length+1];
		result[1]=array[0];
		indexes[1]=0;
		indexes[0]=-1;
		int Start=1;
		int End=1;

		for(int i=1;i<array.length;i++){
			int v=array[i];
			int index;
			int start=Start;
			int end=End;
			while(true){
				index=(end+start)/2;
				if(index==end || index==start){
					break;
				}
				if(v==result[index]){
					break;
				}else if(v>result[index]){
					start=index;
				}else if(v<result[index]){
					end=index;
				}
			}
			if(v==result[index]){
				continue;
			}
			if(v<result[start]){
				result[start]=v;
				indexes[start]=i;
				link[i]=indexes[start-1];
			}else if(v>result[start] && v<result[end]){
				result[end]=v;
				indexes[end]=i;
				link[i]=indexes[end-1];
			}else if(v>array[end] && end==End){
				End++;
				result[End]=v;
				indexes[End]=i;
				link[i]=indexes[End-1];
			}
		}
		for(int i=Start;i<=End;i++){
			log(i+":"+result[i]);
		}
		log("longest increasing sub list size:"+(End-Start+1));
		int index=indexes[End];
		while(index>=0){
			int v=array[index];
			log(""+v);
			index=link[index];
		}
	}
	
	public static void log(String s){
		System.out.println(s);
	}
}
