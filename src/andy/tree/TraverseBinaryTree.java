package andy.tree;

import java.util.Stack;

public class TraverseBinaryTree {

	public static void doit(Node root){
		if(root==null){
			return;
		}
		Node cursor=root;
		Node poped =null;
		Stack<Node> stack=new Stack<Node>();
		while(true){
			if(poped==null ){
				if(cursor==null){
					break;
				}
				//log("visit:"+cursor.value);//pre order traverse
				if(cursor.left!=null){
					stack.push(cursor);
					cursor=cursor.left;
				}else if(cursor.right!=null){
					stack.push(cursor);
					log("visit:"+cursor.value);// in order traverse
					cursor=cursor.right;
				}else{
					poped=cursor;
					log("visit:"+poped.value);// in order traverse
					//log("visit:"+poped.value);//post order traverse
				}
			}else{
				if(stack.isEmpty()){
					break;
				}
				Node t=stack.peek();
				if(t.left==poped){
					log("visit:"+t.value);// in order traverse
					if(t.right==null){
						poped=stack.pop();
						//log("visit:"+poped.value);//post order traverse
					}else{
						cursor=t.right;
						poped=null;
					}
				}else if(t.right==poped){
					poped=stack.pop();
					//log("visit:"+poped.value);//post order traverse
				}
			}
		}
	}
	
	public static void log(String s){
		System.out.println(s);
	}
}
