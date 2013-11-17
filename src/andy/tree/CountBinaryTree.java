package andy.tree;

import java.util.Stack;

public class CountBinaryTree {

	public static class Element{
		public Node n=null;
		public int num=0;
	}
	public static int doit(Node root){
		Node cursor=root;
		Element poped=null;
		Stack<Element>stack=new Stack<Element>();
		while(true){
			if(poped==null){
				if(cursor==null){
					break;
				}
				if(cursor.left!=null){
					Element tmp=new Element();
					tmp.n=cursor;
					tmp.num=1;
					stack.push(tmp);
					cursor=cursor.left;
				}else if(cursor.right!=null){
					Element tmp=new Element();
					tmp.n=cursor;
					tmp.num=1;
					stack.push(tmp);
					cursor=cursor.right;
				}else{
					poped=new Element();
					poped.n=cursor;
					poped.num=1;
				}
			}else{
				if(stack.isEmpty()){
					break;
				}
				Element e=stack.peek();
				if(e.n.left==poped.n){
					e.num+=poped.num;
					if(e.n.right!=null){
						cursor=e.n.right;
						poped=null;
					}else{
						poped=stack.pop();
						if(poped.n==root){
							return poped.num;
						}
					}
				}else if(e.n.right==poped.n){
					e.num+=poped.num;
					poped=stack.pop();
					if(poped.n==root){
						return poped.num;
					}
				}
			}
		}
		return 0;
	}
}
