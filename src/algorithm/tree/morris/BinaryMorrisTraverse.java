package algorithm.tree.morris;

import algorithm.tree.data.BinaryNode;

public class BinaryMorrisTraverse {
    public static void preOrderVisit(BinaryNode root){
        System.out.println("================Morris Pre Order Visit===============");
        BinaryMorrisStack stack=new BinaryMorrisStack();
        BinaryNode nroot=new BinaryNode("(morris fake root)");
        nroot.setLeft(root);
        BinaryNode cursor=nroot;
        BinaryNode lastPoped=null;
        while(cursor!=null || !stack.isEmpty()){
            if(cursor!=null){
                System.out.println("visit "+cursor.getName());
                stack.push(cursor);
                cursor=cursor.getLeft();
            }else{
                cursor=stack.top();
                BinaryNode rightChild=BinaryMorrisStack.getRightChild(cursor);
                if(rightChild==lastPoped){
                    lastPoped=stack.top();
                    stack.pop();
                    cursor=null;
                }else{
                    cursor=rightChild;
                    lastPoped=null;
                }
            }
        }
    }
    
    public static void postOrderVisit(BinaryNode root){
        System.out.println("================Morris Post Order Visit===============");
        BinaryMorrisStack stack=new BinaryMorrisStack();
        BinaryNode nroot=new BinaryNode("(morris fake root)");
        nroot.setLeft(root);
        BinaryNode cursor=nroot;
        BinaryNode lastPoped=null;
        while(cursor!=null || !stack.isEmpty()){
            if(cursor!=null){
                stack.push(cursor);
                cursor=cursor.getLeft();
            }else{
                cursor=stack.top();
                BinaryNode rightChild=BinaryMorrisStack.getRightChild(cursor);
                if(rightChild==lastPoped){
                    lastPoped=stack.top();
                    System.out.println("visit "+lastPoped.getName());
                    stack.pop();
                    cursor=null;
                }else{
                    cursor=rightChild;
                    lastPoped=null;
                }
            }
        }
    }
    public static void inOrderVisit(BinaryNode root){
        System.out.println("================Morris In Order Visit===============");
        BinaryMorrisStack stack=new BinaryMorrisStack();
        BinaryNode nroot=new BinaryNode("(morris fake root)");
        nroot.setLeft(root);
        BinaryNode cursor=nroot;
        BinaryNode lastPoped=null;
        while(cursor!=null || !stack.isEmpty()){
            if(cursor!=null){
                stack.push(cursor);
                cursor=cursor.getLeft();
            }else{
                cursor=stack.top();
                //
                BinaryNode rightChild=BinaryMorrisStack.getRightChild(cursor);
                if(rightChild==lastPoped){
                    if(lastPoped==null){
                        System.out.println("visit "+cursor.getName());
                    }
                    lastPoped=stack.top();
                    stack.pop();
                    cursor=null;
                }else{
                    if(rightChild!=null){
                        System.out.println("visit "+cursor.getName());
                    }
                    cursor=rightChild;
                    lastPoped=null;
                }
            }
        }
    }
    public static void inOrder2Visit(BinaryNode root){
        System.out.println("================Morris Post Order Visit===============");
        BinaryMorrisStack stack=new BinaryMorrisStack();
        BinaryNode nroot=new BinaryNode("(morris fake root)");
        nroot.setLeft(root);
        BinaryNode cursor=nroot;
        BinaryNode lastPoped=null;
        while(cursor!=null || !stack.isEmpty()){
            if(cursor!=null){
                stack.push(cursor);
                cursor=cursor.getLeft();
            }else{
                cursor=stack.top();
                System.out.println("visit "+cursor.getName());
                BinaryNode rightChild=BinaryMorrisStack.getRightChild(cursor);
                if(rightChild==lastPoped){
                    lastPoped=stack.top();
                    stack.pop();
                    cursor=null;
                }else{
                    cursor=rightChild;
                    lastPoped=null;
                }
            }
        }
    }
}
