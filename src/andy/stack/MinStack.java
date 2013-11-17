package andy.stack;

import java.util.Stack;

import andy.util.Log;

public class MinStack extends Stack<Integer>{
	public class MinEntry{
		public int min;
		public int count;
		public MinEntry(int min,int count){
			this.min=min;
			this.count=count;
		}
	}
	Stack<MinEntry>minS=new Stack<MinEntry>();
	public void push(int a){
		if(minS.isEmpty()){
			minS.push(new MinEntry(a,1));
		}else{
			MinEntry e=minS.peek();
			if(a<e.min){
				minS.push(new MinEntry(a,1));
			}else{
				e.count++;
			}
		}
		super.push(a);
	}
	@Override
	public Integer pop(){
		if(this.isEmpty()){return null;}
		Integer a=this.pop();
		MinEntry e=minS.peek();
		e.count--;
		if(e.count==0){
			minS.pop();
		}else if(e.count<0){
			Log.en("(ERROR)min count is "+e.count);
		}
		return a;
	}
	
	public int min(){
		if(minS.isEmpty()){return Integer.MAX_VALUE;}
		return minS.peek().min;
	}
}
