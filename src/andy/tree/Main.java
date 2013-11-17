package andy.tree;

import andy.array.LCS;
import andy.array.LongestIncreSub;

public class Main {
	public static void main(String[] args){
//		Node root=generateLeftNoneBinaryTree();
//		log(""+IsBinarySearchTree.judge(root));
//		
//		TraverseBinaryTree.doit(root);
//		
//		root=generateRighNoneBinaryTree();
//		log(""+IsBinarySearchTree.judge(root));
//		
//		root=generateBinaryTree();
//		log(""+IsBinarySearchTree.judge(root));
//		
//		log(""+CountBinaryTree.doit(root));
//		log("\n\n");
//		testFindCommonParent();
//		testFindCommonParentQuick();
//
//		int[] array={70,60,50,90,110,115,119,112};
//				//{70,60,50,90,110,115,112,119};
//		LongestIncreSub.doit(array);
		
	}
	
	public static Node generateLeftNoneBinaryTree(){
		Node j=new Node(50,null,null);
		
		Node f=new Node(60,j,null);
		
		Node d=new Node(70,null, f);
		
		Node b=new Node(90,d,null);
		
		Node k=new Node(112,null,null);
		Node m=new Node(119,null,null);
		Node g=new Node(115,k,m);
		
		Node h=new Node(130,null,null);
		
		Node e=new Node(120,g,h);
		
		Node c=new Node(110,null,e);
		
		
		Node a=new Node(100,b,c);
		
		return a;
		
	}
	
	public static Node generateBinaryTree(){
		Node j=new Node(75,null,null);
		
		Node f=new Node(80,j,null);
		
		Node d=new Node(70,null, f);
		
		Node b=new Node(90,d,null);
		
		Node k=new Node(112,null,null);
		Node m=new Node(119,null,null);
		Node g=new Node(115,k,m);
		
		Node h=new Node(130,null,null);
		
		Node e=new Node(120,g,h);
		
		Node c=new Node(110,null,e);
		
		
		Node a=new Node(100,b,c);
		
		return a;
	}
	
	public static Node generateRighNoneBinaryTree(){
		Node j=new Node(75,null,null);
		
		Node f=new Node(80,j,null);
		
		Node d=new Node(70,null, f);
		
		Node b=new Node(90,d,null);
		
		Node k=new Node(40,null,null);
		Node m=new Node(119,null,null);
		Node g=new Node(115,k,m);
		
		Node h=new Node(130,null,null);
		
		Node e=new Node(120,g,h);
		
		Node c=new Node(110,null,e);
		
		
		Node a=new Node(100,b,c);
		
		return a;
	}
	
	public static void testFindCommonParent(){
		Node j=new Node(75,null,null);
		
		Node f=new Node(80,j,null);
		
		Node d=new Node(70,null, f);
		
		Node b=new Node(90,d,null);
		
		Node k=new Node(112,null,null);
		Node m=new Node(119,null,null);
		Node g=new Node(115,k,m);
		
		Node h=new Node(130,null,null);
		
		Node e=new Node(120,g,h);
		
		Node c=new Node(110,null,e);
		
		
		Node a=new Node(100,b,c);
		
		Node A=b;
		Node B=c;
		
		Node parent=FindCommonParent.doit(a, A, B);
		log("common parent of "+A.value+" and "+B.value+":"+parent.value);
	}
	
	public static void testFindCommonParentQuick(){
		Node j=new Node(75,null,null);
		
		Node f=new Node(80,j,null);
		
		Node d=new Node(70,null, f);
		
		Node b=new Node(90,d,null);
		
		Node k=new Node(112,null,null);
		Node m=new Node(119,null,null);
		Node g=new Node(115,k,m);
		
		Node h=new Node(130,null,null);
		
		Node e=new Node(120,g,h);
		
		Node c=new Node(110,null,e);
		
		
		Node a=new Node(100,b,c);
		
		Node A=b;
		Node B=c;
		
		Node parent=FindCommonParent.doitQuick(a, A, B);
		log("common parent of "+A.value+" and "+B.value+":"+parent.value);
	}
	
	public static void log(String s){
		System.out.println(s);
	}
}
