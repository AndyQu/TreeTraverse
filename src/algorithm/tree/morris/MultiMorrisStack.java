package algorithm.tree.morris;

import java.util.ArrayList;
import java.util.List;

import algorithm.tree.data.MultiNode;

/**
 * There are differences between MultiMorrisStack with BinaryMorrisStack in some aspects.
 * How to select a "left" node, to form a loop back to the parent.
 * That is what findLeftMostValidChid() does.
 * @author aichong
 *
 */
public class MultiMorrisStack {
    private MultiNode stackTop;
    public MultiMorrisStack(){
        stackTop=null;
    }
    public boolean isEmpty(){
        return stackTop==null;
    }
    public MultiNode top(){
        return stackTop;
    }
    public void push(MultiNode cursor){
        if(cursor==null){
            System.out.println("(push/error)null");
            return;
        }
        List<Integer> indexes=findAllExceptRighMost(cursor);
        for(Integer i:indexes){
            linkRightMostToParent(cursor.childs[i], cursor);
        }
        stackTop=cursor;
    }
    private boolean isBackTrace=false;
    private MultiNode parent=null;
    private MultiNode rightMost=null;
    private MultiNode leftChild=null;
    public void pop(){
        if(!isBackTrace){
            MultiNode tmp=stackTop.childs[stackTop.getTotal()-1];
            if(tmp==null){//end
                stackTop=null;
                return;
            }
            //List<Integer> indexes=findAllExceptRighMost(tmp);
            List<Integer>indexes=new ArrayList<Integer>();
            indexes.add(tmp.getVcount()-1);
            boolean flag=false;
            for(Integer i:indexes){
                leftChild=tmp.childs[i];
                if(leftChild==stackTop){
                    leftChild.childs[leftChild.getTotal()-1]=null;
                    stackTop=tmp;
                    flag=true;
                    break;
                }else{
                    if(isOnRightMostPath(tmp, leftChild, stackTop)){
                        isBackTrace=true;
                        parent=tmp;
                        rightMost=stackTop;
                        stackTop=getNextStackTop(leftChild, stackTop, rightMost);
                        flag=true;
                        break;
                    }
                }
            }
            if(!flag){
                System.out.println("(pop)can't find a loop.stackTop:"+stackTop.getName());
            }
        }else{
            if(leftChild==stackTop){
                stackTop=parent;
                isBackTrace=false;
                parent=null;
                rightMost.childs[rightMost.getTotal()-1]=null;
                rightMost=null;
                leftChild=null;
            }else{
                stackTop=getNextStackTop(leftChild, stackTop, rightMost);
            }
        }
    }
    /**
     * 
     * @param leftMost
     * @param parent
     */
    private static void linkRightMostToParent(MultiNode leftMost,MultiNode parent){
        if(leftMost==null || parent==null){
            System.out.println("(linkRightMostToParent)"+leftMost+";"+parent);
            return;
        }
        while(true){
            if(leftMost.childs[leftMost.getTotal()-1]!=null){
                leftMost=leftMost.childs[leftMost.getTotal()-1];
            }else{
                leftMost.childs[leftMost.getTotal()-1]=parent;
                return;
            }
        }
    }
    private static boolean isOnRightMostPath(MultiNode parent,MultiNode leftMost, MultiNode stackTop){
        if(parent==null||leftMost==null || stackTop==null){
            System.out.println("(isOnRightMostPath)"+parent+";"+leftMost+";"+stackTop);
            return false;
        }
        if(leftMost==stackTop){
            return true;
        }
        while(leftMost.childs[leftMost.getTotal()-1]!=null ){
            if(leftMost.childs[leftMost.getTotal()-1]==stackTop){
                return true;
            }else{
                leftMost=leftMost.childs[leftMost.getTotal()-1];
            }
        }
        return false;
    }
    private MultiNode getNextStackTop(MultiNode leftMost, MultiNode stackTop,MultiNode rightMost){
        if(leftMost==null||stackTop==null||rightMost==null){
            System.out.println("(getNextStackTop)"+leftMost+";"+stackTop+";"+rightMost);
            return null;
        }
        while(leftMost.childs[leftMost.getTotal()-1]!=null){
            if(leftMost.childs[leftMost.getTotal()-1]==stackTop){
                return leftMost;
            }
            if(leftMost.childs[leftMost.getTotal()-1]==rightMost){
                break;
            }
            leftMost=leftMost.childs[leftMost.getTotal()-1];
        }
        return null;
    }
    
    public static boolean allChidrenVisited(MultiNode cursor){
        if(cursor.getVcount()>=cursor.getTotal()){
            return true;
        }
        if(cursor.getVcount()==cursor.getTotal()-1){
            MultiNode tmp=cursor.childs[cursor.getTotal()-1];
            if(tmp==null){
                return true;
            }else{
                List<Integer>indexes=findAllExceptRighMost(tmp);
                for(Integer i:indexes){
                    if(isOnRightMostPath(tmp, tmp.childs[i], cursor)){
                        return true;
                    }
                }
                return false;
            }
        }else{
            return false;
        }
    }
    private static List<Integer>findAllExceptRighMost(MultiNode cursor){
        List<Integer>indexes=new ArrayList<Integer>();
        for(int i=0;i<cursor.getTotal()-1;i++){
            if(cursor.childs[i]!=null){
                indexes.add(i);
            }
        }
        return indexes;
    }
}
