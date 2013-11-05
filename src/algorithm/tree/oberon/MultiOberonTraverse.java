package algorithm.tree.oberon;

import java.util.Stack;

import algorithm.tree.data.MultiNode;


public class MultiOberonTraverse {
    public static void stackTraverse(MultiNode root){
        MultiNode fakeRoot=new MultiNode("(fake root)", 2);
        fakeRoot.childs[0]=root;
        fakeRoot.setVcount(1);
        MultiOberonStack stack=new MultiOberonStack(fakeRoot);
        MultiNode cursor=root;
        int index=0;
        System.out.println("================Oberon Stack Multi Traverse===============");
        while(cursor!=null || !stack.isEmpty() ){
            if(cursor!=null){
                //System.out.println("visit and push:"+cursor.getName());
                stack.push(cursor, index);
                index=findNextValidChildIndex(cursor);
                if(index>=cursor.getTotal()){
                    cursor.setVcount(cursor.getTotal());
                    cursor=null;
                }else{
                    cursor.setVcount(index+1);
                    cursor=cursor.childs[index];
                }
            }else{
                cursor=stack.top();
                if(cursor.getVcount()>=cursor.getTotal()){
                    System.out.println("visit and pop:"+cursor.getName());
                    stack.pop();
                    cursor=null;
                }else{
                    index=findNextValidChildIndex(cursor);
                    if(index>=cursor.getTotal()){
                        cursor.setVcount(cursor.getTotal());
                        cursor=null;
                    }else{
                        cursor.setVcount(index+1);
                        cursor=cursor.childs[index];
                    }
                }
            }
        }
    }
    
    private static int findNextValidChildIndex(MultiNode cursor){
        int i=cursor.getVcount();
        while(true){
            if(i>=cursor.getTotal()){
                break;
            }
            if(cursor.childs[i]!=null && cursor.childs[i].getVcount()<=0){
                break;
            }
            i++;
        }
        return i;
    }
    
    
    
    public static void traverse(MultiNode root){
        MultiNode cursor=root;
        Stack<MultiNode>stack=new Stack<MultiNode>();
        System.out.println("================Oberon Multi Traverse===============");
        while(cursor!=null || !stack.isEmpty() ){
            if(cursor!=null){
                //System.out.println("visit:"+cursor.getName());
                stack.push(cursor);
                cursor=findNextValidChild(cursor);
            }else{
                cursor=stack.peek();
                if(cursor.getVcount()>=cursor.getTotal()){
                    System.out.println("visit:"+cursor.getName());
                    stack.pop();
                    cursor=null;
                }else{
                    cursor=findNextValidChild(cursor);
                }
            }
        }
    }
    
    private static MultiNode findNextValidChild(MultiNode cursor){
        boolean flag=false;
        while(true){
            if(cursor.childs[cursor.getVcount()]!=null && cursor.childs[cursor.getVcount()].getVcount()<=0){
                break;
            }
            cursor.setVcount(cursor.getVcount()+1);
            if(cursor.getVcount()>=cursor.getTotal()){
                flag=true;
                break;
            }
        }
        if(flag){
            cursor=null;
        }else{
            cursor.setVcount(cursor.getVcount()+1);
            cursor=cursor.childs[cursor.getVcount()-1];
        }
        return cursor;
    }
}
