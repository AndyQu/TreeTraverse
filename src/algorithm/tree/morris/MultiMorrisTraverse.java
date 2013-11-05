package algorithm.tree.morris;

import java.util.Stack;

import algorithm.tree.data.MultiNode;


public class MultiMorrisTraverse {
    public static void visit(MultiNode root){
        MultiNode cursor=root;
        Stack<MultiNode>stack=new Stack<MultiNode>();
        System.out.println("================MultiNode Traverse===============");
        while(cursor!=null || !stack.isEmpty()){
            if(cursor!=null){
                //System.out.println("visit:"+cursor.getName());
                cursor.setVcount(1);
                stack.push(cursor);
                cursor=cursor.childs[0];
            }else{
                cursor=stack.peek();
                if(cursor.getVcount()>=cursor.getTotal()){
                    stack.pop();
                    System.out.println("visit:"+cursor.getName());
                    cursor=null;
                }else{
                    cursor.setVcount(cursor.getVcount()+1);
                    cursor=cursor.childs[cursor.getVcount()-1];
                }
            }
        }
    }
    public static void morrisVisit(MultiNode root){
        MultiNode morrisRoot=new MultiNode("(fake root)", 2);
        morrisRoot.childs[0]=root;
        
        System.out.println("================Morris MultiNode Traverse===============");
        MultiNode cursor=morrisRoot;
        MultiMorrisStack stack=new MultiMorrisStack();
        while(cursor!=null || !stack.isEmpty()){
            if(cursor!=null){
                //System.out.println("visit:"+cursor.getName());
                cursor.setVcount(1);
                stack.push(cursor);
                cursor=cursor.childs[0];
            }else{
                cursor=stack.top();
                if(MultiMorrisStack.allChidrenVisited(cursor)){
                    stack.pop();
                    System.out.println("visit:"+cursor.getName());
                    cursor=null;
                }else{
                    cursor.setVcount(cursor.getVcount()+1);
                    cursor=cursor.childs[cursor.getVcount()-1];
                }
            }
        }
    }
}
