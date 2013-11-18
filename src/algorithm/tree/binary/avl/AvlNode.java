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
        if(this.getLeft()==null){
            this.height=((AvlNode<E>)getRight()).getHeight()+1;
        }else if(this.getRight()==null){
            this.height=((AvlNode<E>)getLeft()).getHeight()+1;
        }else{
            this.height=Math.max(
                            ((AvlNode<E>)getRight()).getHeight(), 
                            ((AvlNode<E>)getLeft()).getHeight()
                                )+1;
        }
    }
    
    public boolean isBalanced(){
        return Math.abs(((AvlNode<E>)getRight()).getHeight()- 
                        ((AvlNode<E>)getLeft()).getHeight())>1;
    }

    public boolean isLeftLonger(){
    	if(this.getLeft()==null){
    		return false;
    	}else if(this.getRight()==null){
    		return true;
    	}else{
    		return ((AvlNode<E>)getLeft()).getHeight()- 
                    ((AvlNode<E>)getRight()).getHeight()>=0;
    	}
    }
}
