package algorithm.tree.binary.interfaces;


public class BSearchNode<E extends Comparable<E>> implements BNode<E>{
    private BNode<E> left=null;
    private BNode<E> right=null;
    private E value=null;
    public BSearchNode(E v){
        value=v;
    }
    @Override
    public BNode<E> getLeft() {
        return left;
    }
    @Override
    public void setLeft(BNode<E> left) {
        this.left = left;
    }
    @Override
    public BNode<E> getRight() {
        return right;
    }
    @Override
    public void setRight(BNode<E> right) {
        this.right = right;
    }
    public E getValue() {
        return value;
    }
    public void setValue(E value) {
        this.value = value;
    }
    public String toString(){
        return value.toString();
    }
}
