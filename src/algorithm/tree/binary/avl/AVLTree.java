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
        boolean needBalance=false;
        while(parent!=null){
            if(parent.isBalanced()){
                int oh=parent.getHeight();
                parent.updateHeight();
                if(oh==parent.getHeight()){
                    break;
                }else{
                    downIsLeft=upIsLeft;
                    child=parent;
                    parent=(AvlNode<E>) parent.getParent();
                    upIsLeft=(parent.getLeft()==child);
                }
            }else{
                needBalance=true;
            }
        }
        if(!needBalance){
            
        }
    }

    @Override
    public void remove(E v) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public BSearchNode<E> search(E v) {
        // TODO Auto-generated method stub
        return null;
    }

}
