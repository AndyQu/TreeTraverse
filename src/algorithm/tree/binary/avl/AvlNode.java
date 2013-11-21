package algorithm.tree.binary.avl;

import algorithm.tree.binary.interfaces.BSearchNode;
import algorithm.tree.binary.interfaces.BidirectNode;

public class AvlNode<E extends Comparable<E>> extends BSearchNode<E> implements BidirectNode<E> {
    public AvlNode(E v) {
        super(v);
        height=1;
    }

    private int height;
    private AvlNode<E> parent;
    @Override
    public BidirectNode<E> getParent() {
        return parent;
    }

    @Override
    public void setParent(BidirectNode<E> p) {
        this.parent=(AvlNode<E>) p;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    public void updateHeight(){
            this.height=Math.max(getLeftHeight(),getRightHeight())+1;
    }
    
    public boolean isBalanced(){
        return Math.abs(getLeftHeight()-getRightHeight())<=1;
    }

    public boolean isLeftLonger(){
    		return (getLeftHeight()-getRightHeight())>=0;
    }
    
    public int lengthCompare(){
        return getLeftHeight()-getRightHeight();
    }
    
    private int getLeftHeight(){
        if(getLeft()==null){
            return 0;
        }else{
            return ((AvlNode<E>)getLeft()).getHeight();
        }
    }
    private int getRightHeight(){
        if(getRight()==null){
            return 0;
        }else{
            return ((AvlNode<E>)getRight()).getHeight();
        }
    }
}
