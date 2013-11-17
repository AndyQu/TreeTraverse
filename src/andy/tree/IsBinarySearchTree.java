package andy.tree;

import java.util.Stack;

public class IsBinarySearchTree {

	public static boolean judge(Node root){
		if(root==null){
			return true;
		}
		Node cursor=root;
		Element popedE=null;
		Stack<Element> stack=new Stack<Element>();
		while(true){
			if(popedE==null){//last iteration did not pop a node
				if(cursor==null){
					break;
				}
				if(cursor.left!=null){
					stack.push(new Element(cursor));
					cursor=cursor.left;
				}else if(cursor.right!=null){
					Element tmp=new Element(cursor);
					tmp.min=cursor.value;
					stack.push(tmp);
					cursor=cursor.right;
				}else{
					popedE=new Element(cursor);
					popedE.min=cursor.value;
					popedE.max=cursor.value;
				}
			}else{//last iteration poped a node
				if(stack.isEmpty()){
					break;
				}
				Element e=stack.peek();
				if(e.n.left==popedE.n){
					if(e.n.value<=popedE.max){
						log(e.n.value+" <= "+ popedE.max);
						return false;
					}else{
						e.min=popedE.min;
						if(e.n.right!=null){
							cursor=e.n.right;
							popedE=null;
						}else{
							e.max=e.n.value;
							popedE=stack.pop();
						}
					}
				}else if(e.n.right==popedE.n){
					if(e.n.value>popedE.min){
						log(e.n.value+" > "+popedE.min);
						return false;
					}else{
						e.max=popedE.max;
						popedE=stack.pop();
					}
				}else{
					log("=============should not enter this");
				}
			}
		}
		return true;
	}
	
	public static void log(String s){
		System.out.println(s);
	}
}
