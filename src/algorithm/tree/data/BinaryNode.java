package algorithm.tree.data;

public class BinaryNode {
    private String name;

    private int vcount;
    
    private BinaryNode left;
    private BinaryNode right;
    private boolean marked=false;
    
    public void mark(){
        marked=true;
    } 
    public boolean isMarked(){
        return this.marked;
    }
    
    public BinaryNode(String name){
        this.name=name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BinaryNode getLeft() {
        return left;
    }

    public void setLeft(BinaryNode left) {
        this.left = left;
    }

    public BinaryNode getRight() {
        return right;
    }

    public void setRight(BinaryNode right) {
        this.right = right;
    }

    public int getVcount() {
        return vcount;
    }

    public void setVcount(int vcount) {
        this.vcount = vcount;
    }
}
