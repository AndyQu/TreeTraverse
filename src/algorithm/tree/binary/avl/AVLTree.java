package algorithm.tree.binary.avl;

import algorithm.tree.binary.interfaces.BSearchNode;
import algorithm.tree.binary.interfaces.BSearchTree;

public class AVLTree<E extends Comparable<E>> implements BSearchTree<E> {
    private AvlNode<E> root=null;
    public AVLTree(){
    }
    @Override
    public BSearchNode<E> getRoot() {
        return root;
    }

    @Override
    public void insert(E v) {
        if(root==null){
            root=new AvlNode<E>(v);
            return;
        }
        //Insert
        AvlNode<E>cursor=root;
        AvlNode<E>newAdded=null;
        while(cursor!=null){
            int r=cursor.getValue().compareTo(v);
            if(r==0){
                return;
            }else if(r>0){
                if(cursor.getLeft()==null){
                    newAdded=new AvlNode<E>(v);
                    cursor.setLeft(newAdded);
                    newAdded.setParent(cursor);
                    break;
                }else{
                    cursor=(AvlNode<E>) cursor.getLeft();
                }
            }else{
                if(cursor.getRight()==null){
                    newAdded=new AvlNode<E>(v);
                    cursor.setRight(newAdded);
                    newAdded.setParent(cursor);
                    break;
                }else{
                    cursor=(AvlNode<E>) cursor.getRight();
                }
            }
        }
        //update height and check whether need to balance
        newAdded.setHeight(1);
        AvlNode<E> child=(AvlNode<E>) newAdded.getParent();
        child.updateHeight();
        AvlNode<E> parent=(AvlNode<E>) child.getParent();
        boolean downIsLeft=(child.getLeft()==newAdded);
        boolean upIsLeft=(parent.getLeft()==child);
        while(parent!=null){
            if(parent.isBalanced()){
                int oh=parent.getHeight();
                parent.updateHeight();
                if(oh==parent.getHeight()){
                	//stop updating height, because parent'height is not changed
                    return;
                }else{
                    downIsLeft=upIsLeft;
                    child=parent;
                    parent=(AvlNode<E>) parent.getParent();
                    upIsLeft=(parent.getLeft()==child);
                }
            }else{
                break;
            }
        }
        if(parent==null){
            return;
        }
        
        //balance the tree
        //TODO
        //Don't forget to link the rotated sub tree with its parent
        AvlNode<E> father=(AvlNode<E>) parent.getParent();
        AvlNode<E>	replaceForParent=null;
        boolean isLeft=true;
        if(father!=null){
        	isLeft=(father.getLeft()==parent);
        }
        if(upIsLeft && downIsLeft){//left,left
        	AvlNode<E> middle=(AvlNode<E>) child.getRight();
        	
        	child.setRight(parent);
        	child.setParent(null);
        	
        	parent.setLeft(middle);
        	parent.setParent(child);
        	
        	child.updateHeight();
        	parent.updateHeight();
        	
        	replaceForParent=child;
        }else if(!upIsLeft && !downIsLeft){//right,right
        	AvlNode<E>middle=(AvlNode<E>) child.getLeft();
        	
        	child.setLeft(parent);
        	child.setParent(null);
        	
        	parent.setRight(middle);
        	parent.setParent(child);
        	
        	child.updateHeight();
        	parent.updateHeight();
        	
        	replaceForParent=child;
        }else if(upIsLeft && !downIsLeft){//left,right
        	AvlNode<E>targetRoot=(AvlNode<E>) child.getRight();
        	AvlNode<E>middle_left=(AvlNode<E>) targetRoot.getLeft();
        	AvlNode<E>middle_right=(AvlNode<E>) targetRoot.getRight();
        	
        	child.setRight(middle_left);
        	child.setParent(targetRoot);
        	
        	parent.setLeft(middle_right);
        	parent.setParent(targetRoot);
        	
        	targetRoot.setLeft(child);
        	targetRoot.setRight(parent);
        	
        	child.updateHeight();
        	targetRoot.updateHeight();
        	parent.updateHeight();
        	
        	replaceForParent=targetRoot;
        }else{//right,left
        	AvlNode<E>targetRoot=(AvlNode<E>) child.getLeft();
        	AvlNode<E>middle_left=(AvlNode<E>) targetRoot.getLeft();
        	AvlNode<E>middle_right=(AvlNode<E>) targetRoot.getRight();
        	
        	child.setLeft(middle_right);
        	child.setParent(targetRoot);
        	
        	parent.setRight(middle_left);
        	parent.setParent(targetRoot);
        	
        	targetRoot.setLeft(parent);
        	targetRoot.setRight(child);
        	
        	child.updateHeight();
        	targetRoot.updateHeight();
        	parent.updateHeight();
        	
        	replaceForParent=targetRoot;
        }
        
        //link the rotated tree with father
        if(father==null){
        	root=replaceForParent;
        	replaceForParent.setParent(null);
        }else if(isLeft){
        	father.setLeft(replaceForParent);
        	replaceForParent.setParent(father);
        }else{
        	father.setRight(replaceForParent);
        	replaceForParent.setParent(father);
        }
    }

    @Override
    public void remove(E v) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public BSearchNode<E> search(E v) {
        BSearchNode<E>cursor=root;
        while(cursor!=null){
        	int r=cursor.getValue().compareTo(v);
        	if(r==0){
        		break;
        	}else if(r>0){
        		cursor=(BSearchNode<E>) cursor.getLeft();
        	}else{
        		cursor=(BSearchNode<E>) cursor.getRight();
        	}
        }
        return cursor;
    }

}
