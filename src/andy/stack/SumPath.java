package andy.stack;

import java.util.ArrayList;
import java.util.List;

import andy.util.Log;

public class SumPath {
	public static class Node{
		int value;
		Node left;
		Node right;
		public Node(int v,Node l,Node r){
			value=v;
			left=l;
			right=r;
		}
	}
	private static void doit(Node root,int sum){
		subDoit(root,sum,new ArrayList<Integer>());
	}
	private static void subDoit(Node root, int sum,List<Integer>stack){
		if(root==null){
			Log.en("input tree is empty");
			return;
		}
		Log.en("visit "+root.value);
		stack.add(root.value);
		int s=0;
		for(int i=stack.size()-1;i>=0;i--){
			s+=stack.get(i);
			if(s==sum){
				print(stack,i, stack.size()-1);
			}
		}
		subDoit(root.left,sum,stack);
		subDoit(root.right,sum,stack);
		stack.remove(stack.size()-1);
	}
	private static void print(List<Integer>list,int start,int end){
		for(int i=start;i<=end;i++){
			Log.enn(list.get(i)+",");
		}
		Log.en("");
	}
	
	public static void main(String[]args){
		Node D=new Node(-6,null,null);
		Node C=new Node(2,null,D);
		
		Node G=new Node(-5,null,null);
		Node F=new Node(-4,G,null);
		Node E=new Node(5,null,F);
		
		Node B=new Node(4,C,E);
		
		Node L=new Node(-1,null,null);
		Node K=new Node(1,L,null);
		Node J=new Node(1,null,K);
		Node H=new Node(1,null,J);
		
		Node A=new Node(3,B,H);
		
		doit(A,3);
	}
}
