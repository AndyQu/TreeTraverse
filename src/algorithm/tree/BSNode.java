package algorithm.tree;

public class BSNode<E> implements BinaryNode<E>{
    private BinaryNode<E> left=null;
    private BinaryNode<E> right=null;
    private Comparable<E>value=null;
    public BSNode(Comparable<E>v){
        value=v;
    }
    @Override
    public BinaryNode<E> getLeft() {
        return left;
    }
    @Override
    public void setLeft(BinaryNode<E> left) {
        this.left = left;
    }
    @Override
    public BinaryNode<E> getRight() {
        return right;
    }
    @Override
    public void setRight(BinaryNode<E> right) {
        this.right = right;
    }
    public Comparable<E> getValue() {
        return value;
    }
    public void setValue(Comparable<E> value) {
        this.value = value;
    }
    public String toString(){
        return value.toString();
    }
}
