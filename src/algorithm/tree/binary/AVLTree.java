package algorithm.tree.binary;

import algorithm.tree.binary.interfaces.BSearchNode;
import algorithm.tree.binary.interfaces.BinarySearchTree;

public class AVLTree<E extends Comparable<E>> implements BinarySearchTree<E> {
    private BSearchNode<E> root=null;
    public AVLTree(){
    }
    @Override
    public BSearchNode<E> getRoot() {
        return root;
    }

    @Override
    public void insert(E v) {
        // TODO Auto-generated method stub
        
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
