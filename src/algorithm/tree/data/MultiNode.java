package algorithm.tree.data;

public class MultiNode {
    private String name;
    private int vcount=0;
    private int total;
    public MultiNode[] childs;
    public MultiNode(String name, int total){
        this.name=name;
        this.total=total;
        this.childs=new MultiNode[total];
    }
    public int getVcount() {
        return vcount;
    }
    public void setVcount(int vcount) {
        this.vcount = vcount;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}
