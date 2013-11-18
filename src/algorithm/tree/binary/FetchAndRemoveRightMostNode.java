package algorithm.tree.binary;

import algorithm.tree.binary.interfaces.BNode;

public class FetchAndRemoveRightMostNode {
	public static class Bean {
        public BNode left_tree;
        public BNode biggest;
        public BNode biggest_origin_father;
    }
	public static Bean doit(BNode lnode) {
        if (lnode == null) {
            return null;
        }
        Bean bean = new Bean();
        if (lnode.getRight() == null) {
            bean.left_tree = lnode.getLeft();
            bean.biggest = lnode;
            bean.biggest_origin_father=null;
        } else {
            bean.left_tree = lnode;
            BNode parent = lnode;
            lnode = lnode.getRight();
            while (lnode.getRight() != null) {
                parent = lnode;
                lnode = lnode.getRight();
            }
            parent.setRight(lnode.getLeft());
            bean.biggest = lnode;
            bean.biggest_origin_father=parent;
        }
        bean.biggest.setLeft(null);
        bean.biggest.setRight(null);
        return bean;
    }
}
