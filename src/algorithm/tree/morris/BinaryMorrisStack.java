package algorithm.tree.morris;

import algorithm.tree.data.BinaryNode;

public class BinaryMorrisStack {
    private BinaryNode stackTop;
    public BinaryMorrisStack(){
        stackTop=null;
    }
    public boolean isEmpty(){
        return stackTop==null;
    }
    public void push(BinaryNode p){
        if(p==null){
            System.out.println("(push)null");
            return;
        }
        stackTop=p;
        if(p.getLeft()!=null){
            linkRightMostToParent(p.getLeft(), p);
        }
    }
    public BinaryNode top(){
        return stackTop;
    }
    private boolean isBackTrace=false;
    private BinaryNode parent=null;
    private BinaryNode rightMost=null;
    
    public void pop(){
        if(isBackTrace){
            if(parent.getLeft()==stackTop){/*reach to the left node of parent. end back trace mode. break loop*/
                stackTop=parent;
                isBackTrace=false;
                parent=null;
                rightMost.setRight(null);
            }else{
                stackTop=getNextStackTop(parent, stackTop);
            }
        }else{
            BinaryNode tmp=stackTop.getRight();
            if(tmp==null){/*stackTop is root*/
                stackTop=null;
            }else{
                if(tmp.getLeft()==stackTop){/*stackTop is left node of parent, and it has no right children*/
                    stackTop.setRight(null);//break loop
                    stackTop=tmp;
                }else{
                    if(isOnRightMostPath(tmp, tmp.getLeft(), stackTop)){
                        parent=tmp;
                        isBackTrace=true;
                        rightMost=stackTop;
                        stackTop=getNextStackTop(parent, stackTop);
                    }else{
                        System.out.println("(pop)"+stackTop.getName()+" is not on right path of "+tmp.getLeft().getName()+";parent is "+tmp.getName());
                    }
                }
            }
        }
    }
    private static void linkRightMostToParent(BinaryNode leftN,BinaryNode parent){
        if(leftN==null || parent==null){
            System.out.println("(linkRightMostToParent)"+leftN+";"+parent);
            return;
        }
        while(leftN.getRight()!=null){
            leftN=leftN.getRight();
        }
        leftN.setRight(parent);
    }
    /**
     * Precondition under which to call this method:
     * leftN is the left child of parent;
     * @param parent
     * @param leftN
     * @param t
     * @return
     */
    public static boolean isOnRightMostPath(BinaryNode parent,BinaryNode leftN, BinaryNode t){
        if(parent==null||leftN==null || t==null){
            System.out.println("(isRightMost)"+parent+";"+leftN+";"+t);
            return false;
        }
        if(t.getRight()!=parent){
            return false;
        }
        if(leftN==t){
            return true;
        }
        while(leftN.getRight()!=null){
            if(leftN.getRight()==t){
                return true;
            }
            leftN=leftN.getRight();
            if(leftN==parent){/*has reach to the loop end point*/
                break;
            }
        }
        return false;
    }
    /**
     * Precondition under which to call this method:
     * "stackTop" is on the right path of "parent"'s left child node.
     * @param parent 
     * @param stackTop
     * @return
     */
    private static BinaryNode getNextStackTop(BinaryNode parent,BinaryNode stackTop){
        if(parent==null||stackTop==null){
            System.out.println("(getNextStackTop)"+parent+";"+stackTop);
            return null;
        }
        BinaryNode leftN=parent.getLeft();
        while(leftN.getRight()!=stackTop){
            leftN=leftN.getRight();
        }
        return leftN;
    }
    public static BinaryNode getRightChild(BinaryNode cursor){
        BinaryNode right=cursor.getRight();
        if(right==null){
            return null;
        }else{
            if(BinaryMorrisStack.isOnRightMostPath(right, right.getLeft(), cursor)){
                return null;
            }else{
                return right;
            }
        }
    }
}
