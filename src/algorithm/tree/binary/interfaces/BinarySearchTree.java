package algorithm.tree.binary.interfaces;

public interface BinarySearchTree<E extends Comparable<E>> {
    public BinaryNode<E> getRoot();
    public void insert(E v);
    public void remove(E v);
    public BinaryNode<E> search(E v);
}
