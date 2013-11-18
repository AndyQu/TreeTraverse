package algorithm.tree.binary.interfaces;

public interface BidirectNode<E> extends BNode<E> {
    public BidirectNode<E> getParent();
    public void setParent(BidirectNode<E> p);
}
