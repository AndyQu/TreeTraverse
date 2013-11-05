package algorithm.tree.oberon;

import algorithm.tree.data.MultiNode;

public class MultiOberonStack {
    private MultiNode parent=null;
    private MultiNode stackTop=null;
    public MultiOberonStack(MultiNode root){
        parent=root;
        stackTop=root;
    }
    public boolean isEmpty(){
        return parent==stackTop;
    }
    public MultiNode top(){
        return stackTop;
    }
    public void push(MultiNode cursor, int index){
        if(cursor==null){
            System.out.println("(push)null");
            return;
        }
        if(index<0||stackTop.getTotal()<=index){
            System.out.println("(push)index out of bound:"+index+","+stackTop.getTotal());
            return;
        }
        if(cursor!=stackTop.childs[index]){
            System.out.println("(push)cursor!=stackTop.childs[index]."+cursor.getName()+","+stackTop.childs[index].getName());
            return;
        }
        MultiNode tmp=stackTop.childs[index];
        stackTop.childs[index]=parent;
        parent=stackTop;
        stackTop=tmp;
        parent.setVcount(index+1);
    }
    public void pop(){
        MultiNode tmp=parent.childs[parent.getVcount()-1];
        parent.childs[parent.getVcount()-1]=stackTop;
        stackTop=parent;
        parent=tmp;
    }
}
