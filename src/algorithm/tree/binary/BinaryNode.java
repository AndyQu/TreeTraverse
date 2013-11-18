package algorithm.tree.binary;

public interface BinaryNode<E> {
    public BinaryNode<E> getLeft();
    public BinaryNode<E> getRight();
    public void setRight(BinaryNode<E> n);
    public void setLeft(BinaryNode<E> n);
}
