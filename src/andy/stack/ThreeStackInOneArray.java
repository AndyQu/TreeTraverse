package andy.stack;

import andy.util.Log;

public class ThreeStackInOneArray {
	private static final int MAX=501;
	private static final int Count=MAX/2;
	private int[] array=new int[MAX];
	private int s1_index;
	private int s2_index;
	private int s3_index;
	private int free_index;
	public ThreeStackInOneArray(){
		for(free_index=Count*2;free_index>=2;free_index-=2){
			if(free_index==Count*2){
				array[free_index]=-1;
			}else{
				array[free_index]=free_index+2;
			}
			array[free_index-1]=0;
		}
		free_index=2;
		s1_index=-1;
		s2_index=-1;
		s3_index=-1;
	}
	public boolean push(int whichStack,int e){
		if(isFull()){
			Log.en("Stack is full. Can't push:"+e+" into stack "+whichStack);
			return false;
		}
		int avai_index=free_index;
		free_index=array[free_index];
		Log.en("free_index:"+avai_index+"->"+free_index);
		array[avai_index-1]=e;
		switch(whichStack){
			case 1:
				array[avai_index]=s1_index;
				s1_index=avai_index;
				break;
			case 2:
				array[avai_index]=s2_index;
				s2_index=avai_index;
				break;
			case 3:
				array[avai_index]=s3_index;
				s3_index=avai_index;
				break;
			default:
				return false;
		}
		return true;
	}
	
	public int pop(int whichS){
		if(isEmpty(whichS)){
			return Integer.MIN_VALUE;
		}
		int pop_index;
		int v;
		switch(whichS){
			case 1:
				pop_index=s1_index;
				s1_index=array[s1_index];
				break;
			case 2:
				pop_index=s2_index;
				s2_index=array[s2_index];
				break;
			case 3:
				pop_index=s3_index;
				s3_index=array[s3_index];
				break;
			default:
				return Integer.MIN_VALUE;
		}
		v=array[pop_index-1];
		array[pop_index]=free_index;
		array[pop_index-1]=0;
		free_index=pop_index;
		return v;
	}
	
	
	public int peek(int whichS){
		if(isEmpty(whichS)){
			return Integer.MIN_VALUE;
		}
		switch(whichS){
			case 1:
				return array[s1_index-1];
			case 2:
				return array[s2_index-1];
			case 3:
				return array[s3_index-1];
			default:
				return Integer.MIN_VALUE;
		}
	}
	
	public boolean isFull(){
		return free_index==-1;
	}
	public boolean isEmpty(int whichS){
		switch(whichS){
			case 1:
				return s1_index<0;
			case 2:
				return s2_index<0;
			case 3:
				return s3_index<0;
			default:
				return false;
		}
	}
	
	public static void main(String[]args){
		ThreeStackInOneArray stack=new ThreeStackInOneArray();
		int value=0;
		while(!stack.isFull()){
			stack.push(1, value);
			stack.push(2, value);
			stack.push(3, value);
			value++;
		}
		Log.en("stack 1 content:");
		while(!stack.isEmpty(1)){
			Log.enn(stack.pop(1)+",");
		}
		Log.en("");
		
		Log.en("stack 2 content:");
		while(!stack.isEmpty(2)){
			Log.enn(stack.pop(2)+",");
		}
		
		while(!stack.isFull()){
			stack.push(3, value);
			value++;
		}
		Log.en("\nstack 3 content:");
		while(!stack.isEmpty(3)){
			Log.enn(stack.pop(3)+",");
		}
		Log.en("");
	}
}
