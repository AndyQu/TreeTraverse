package andy.stack;

import java.util.Stack;

import andy.util.Log;

public class HanoiTower {
	public static class Node{
		int start;
		int end;
		int from;
		int to;
		public Node(int start,int end,int from,int to){
			this.start=start;
			this.end=end;
			this.from=from;
			this.to=to;
		}
	}
	private static void doitUsingStack(int[]array,int start,int end,int from,int to){
		Node lastPoped=null;
		Stack<Node>stack=new Stack<Node>();
		while(true){
			
			if(lastPoped==null){
				if(start==end){
					Log.en("move "+array[start]+" from "+from+" to "+to);
					lastPoped=new Node(start,end,from,to);
				}else if(start<end){
					stack.push(new Node(start,end,from,to));
					start=start+1;
					to=get(from,to);
				}else{
					Log.en("start>end.Error.Stop.");
					return;
				}
			}else{
				if(stack.isEmpty()){
					Log.en("stack empty. Stop.");
					return;
				}
				Node top= stack.peek();
				//left node
				if(top.start+1==lastPoped.start 
						&& top.end==lastPoped.end 
						&& top.from==lastPoped.from
						&& top.to!=lastPoped.to){
					//go to middle
					start=top.start;
					end=top.start;
					from=top.from;
					to=top.to;
					lastPoped=null;
				}
				//middle node
				else if(top.start==lastPoped.start 
						&& lastPoped.start==lastPoped.end
						&& top.from==lastPoped.from 
						&& top.to==lastPoped.to){
					//go to right
					start=top.start+1;
					end=top.end;
					from=get(top.from,top.to);
					to=top.to;
					lastPoped=null;
				}
				//right node
				else if(top.start+1==lastPoped.start 
						&& top.end==lastPoped.end 
						&& top.from!=lastPoped.from
						&& top.to==lastPoped.to){
					lastPoped=stack.pop();
				}
			}
		}
	}
	
	private static void doit(int[]array,int start,int end,int from, int to){
		if(start>end){
			Log.en("error.");
			return;
		}
		if(start==end){
			Log.en("move "+array[start]+" from "+from+" to "+to);
			return;
		}
		int other=get(from,to);
		if(other<1||other>3){
			Log.en("other error");
			return;
		}
		
		doit(array,start+1,end,from,other);
		doit(array,start,start,from,to);
		doit(array,start+1,end,other,to);
	}
	private static int get(int from,int to){
		switch(from){
		case 1:
			switch(to){
			case 2:
				return 3;
			case 3:
				return 2;
			}
		case 2:
			switch(to){
			case 1:
				return 3;
			case 3:
				return 1;
			}
		case 3:
			switch(to){
			case 1:
				return 2;
			case 2:
				return 1;
			}
		}
		return 0;
	}
	
	public static void main(String[]args){
		int[]array=new int[]{3,2,1,0};
		doit(array,0,array.length-1,1,3);
		Log.en("=====");
		doitUsingStack(array,0,array.length-1,1,3);
		//doit(new int[]{0,1,2,3,4,5,6,7,8,9},0,9,1,3);
	}
}
