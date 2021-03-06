package algorithm.tree.binary.interfaces;


public interface BSearchTree<E extends Comparable<E>> {
    public BSearchNode<E> getRoot();
    public void insert(E v);
    public void remove(E v);
    public BSearchNode<E> search(E v);
}
