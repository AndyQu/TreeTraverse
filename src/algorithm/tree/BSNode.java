package algorithm.tree;

public class BSNode {
    private BSNode left=null;
    private BSNode right=null;
    private Comparable<Object>value=null;
    public BSNode(Comparable<Object>v){
        value=v;
    }
    public BSNode getLeft() {
        return left;
    }
    public void setLeft(BSNode left) {
        this.left = left;
    }
    public BSNode getRight() {
        return right;
    }
    public void setRight(BSNode right) {
        this.right = right;
    }
    public Comparable<Object> getValue() {
        return value;
    }
    public void setValue(Comparable<Object> value) {
        this.value = value;
    }
}
