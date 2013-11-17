package andy.util;

public class QuickSort {
	private static Comparable incro=new Comparable() {
		@Override
		public boolean bigThan(int a, int b) {
			return a>b;
		}

		@Override
		public boolean smallThan(int a, int b) {
			return a<b;
		}
	};
	private static Comparable decro=new Comparable() {
		@Override
		public boolean bigThan(int a,int b) {
			return a<b;
		}
		@Override
		public boolean smallThan(int a, int b) {
			return a>b;
		}
	};
	public static void doit(int[]array,boolean incre){
		sub(array,0,array.length-1,incre?incro:decro);
	}
	private static void sub(int[]array,int start,int end, Comparable com){
		if(start>=end){return;}
		if(start+1==end){
			if(com.bigThan(array[start], array[end])){
				switchE(array,start,end);
			}
			return;
		}
		int v=array[(start+end)/2];
		int si=start;
		int ei=end;
		int equalLoc=start-1;
		for(si=start;si<=ei;){
			if(com.bigThan(array[si], v)){
				while(com.bigThan(array[ei],v)){
					ei--;
				}
				if(ei<=si){
					break;
				}
				switchE(array,si,ei);
				ei--;
			}else if(array[si]==v){
				equalLoc++;
				if(equalLoc==si){
					si++;
				}else{
					switchE(array,si,equalLoc);
					si++;
				}
			}else{
				si++;
			}
		}
		for(int i=start;i<=equalLoc;i++){
			switchE(array,i,(si-1-(i-start)));
		}
		sub(array,start,start+(si-equalLoc-1),com);
		sub(array,si,end,com);
	}
	private static void switchE(int[]array, int a,int b){
		int tmp=array[a];
		array[a]=array[b];
		array[b]=tmp;
	}
	public static interface Comparable{
		public boolean bigThan(int a,int b);
		public boolean smallThan(int a,int b);
	}
	
	public static void main(String[]args){
		int[]array=new int[]{29,382,12,32,324,43};
		doit(array,true);
		for(int i=0;i<array.length;i++){
			Log.en(""+array[i]);
		}
	}
}
