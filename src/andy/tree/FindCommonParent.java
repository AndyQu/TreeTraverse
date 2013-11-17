package andy.tree;

import java.util.Stack;;

public class FindCommonParent {
	public static class Element{
		Node node=null;
		boolean containA=false;
		boolean containB=false;
	}
	public static Node doit(Node root, Node A, Node B){
		Node cursor=root;
		Element poped=null;
		Stack<Element>stack=new Stack<Element>();
		while(true){
			if(poped==null){
				if(cursor==null){
					break;
				}
				log("visit:"+cursor.value);
				if(cursor.left!=null){
					Element tmp=new Element();
					tmp.node=cursor;
					stack.push(tmp);
					if(cursor==A){
						tmp.containA=true;
					}else if(cursor==B){
						tmp.containB=true;
					}
					cursor=cursor.left;
				}else if(cursor.right!=null){
					Element tmp=new Element();
					tmp.node=cursor;
					if(cursor==A){
						tmp.containA=true;
					}else if(cursor==B){
						tmp.containB=true;
					}
					stack.push(tmp);
					cursor=cursor.right;
				}else{
					poped=new Element();
					poped.node=cursor;
					if(cursor==A){
						log("found A:"+cursor.value);
						poped.containA=true;
					}else if(cursor==B){
						log("found B:"+cursor.value);
						poped.containB=true;
					}
				}
			}else{
				if(stack.isEmpty()){
					break;
				}
				Element e=stack.peek();
				if(e.node.left==poped.node){
					if((e.containA && poped.containB )||(e.containB && poped.containA)){
						return e.node;
					}else{
						e.containA=e.containA|poped.containA;
						e.containB=e.containB|poped.containB;
						if(e.node.right==null){
							poped=stack.pop();
						}else{
							cursor=e.node.right;
							poped=null;
						}
					}
				}else if(e.node.right==poped.node){
					if((e.containA && poped.containB )||(e.containB && poped.containA)){
						return e.node;
					}else{
						e.containA=e.containA|poped.containA;
						e.containB=e.containB|poped.containB;
						poped=stack.pop();
					}
				}
			}
		}
		return null;
	}
	
	public static Node doitQuick(Node root, Node A, Node B){
		boolean foundA=false;
		boolean foundB=false;
		Node cursor=root;
		Element poped=null;
		Stack<Element>stack=new Stack<Element>();
		while(true){
			if(poped==null){
				if(cursor==null){
					break;
				}
				log("visit:"+cursor.value);
				if(cursor.left!=null){
					Element tmp=new Element();
					tmp.node=cursor;
					if(cursor==A){
						tmp.containA=true;
						foundA=true;
					}else if(cursor==B){
						tmp.containB=true;
						foundB=true;
					}
					if(foundA && foundB){
						poped=tmp;
					}else{
						stack.push(tmp);
						cursor=cursor.left;
					}
				}else if(cursor.right!=null){
					Element tmp=new Element();
					tmp.node=cursor;
					if(cursor==A){
						tmp.containA=true;
						foundA=true;
					}else if(cursor==B){
						tmp.containB=true;
						foundB=true;
					}
					if(foundA && foundB){
						poped=tmp;
					}else{
						stack.push(tmp);
						cursor=cursor.right;
					}
				}else{
					poped=new Element();
					poped.node=cursor;
					if(cursor==A){
						log("found A:"+cursor.value);
						poped.containA=true;
						foundA=true;
					}else if(cursor==B){
						log("found B:"+cursor.value);
						poped.containB=true;
						foundB=true;
					}
				}
			}else{
				if(stack.isEmpty()){
					break;
				}
				Element e=stack.peek();
				if(e.node.left==poped.node){
					if((e.containA && poped.containB )||(e.containB && poped.containA)){
						return e.node;
					}else{
						e.containA=e.containA|poped.containA;
						e.containB=e.containB|poped.containB;
						if(e.node.right==null){
							poped=stack.pop();
						}else{
							cursor=e.node.right;
							poped=null;
						}
					}
				}else if(e.node.right==poped.node){
					if((e.containA && poped.containB )||(e.containB && poped.containA)){
						return e.node;
					}else{
						e.containA=e.containA|poped.containA;
						e.containB=e.containB|poped.containB;
						poped=stack.pop();
					}
				}
			}
		}
		return null;
	}
	
	public static void log(String s){
		System.out.println(s);
	}
}
