package algorithm.tree.binary.interfaces;

public interface BNode<E> {
    public BNode<E> getLeft();
    public BNode<E> getRight();
    public void setRight(BNode<E> n);
    public void setLeft(BNode<E> n);
}
