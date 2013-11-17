package algorithm.tree;

public class BinarySearchTree {
    private BSNode root = null;

    public BinarySearchTree() {
    }

    public void insert(Comparable<Object> v) {
        if (root == null) {
            root = new BSNode(v);
        }
        BSNode cursor = root;
        while (cursor != null) {
            int res = cursor.getValue().compareTo(v);
            if (res > 0) {
                if (cursor.getLeft() == null) {
                    cursor.setLeft(new BSNode(v));
                    return;
                }
                cursor = cursor.getLeft();
            } else if (res < 0) {
                if (cursor.getRight() == null) {
                    cursor.setRight(new BSNode(v));
                    return;
                }
                cursor = cursor.getRight();
            } else {
                return;
            }
        }
    }

    public BSNode search(Comparable<Object> v) {
        BSNode cursor = root;
        while (cursor != null) {
            int res = cursor.getValue().compareTo(v);
            if (res == 0) {
                return cursor;
            } else if (res > 0) {
                cursor = cursor.getLeft();
            } else {
                cursor = cursor.getRight();
            }
        }
        return null;
    }

    public void delete(Comparable<Object> v) {
        BSNode cursor = root;
        BSNode parent = null;
        while (cursor != null) {
            int res = cursor.getValue().compareTo(v);
            if (res == 0) {
                if (parent == null) {// root
                    if (cursor.getLeft() == null) {
                        root = cursor.getRight();
                    } else if (cursor.getRight() == null) {
                        root = cursor.getLeft();
                    } else {
                        // find biggest one on the left sub tree
                        Bean result = fetchAndRemoveBiggestNode(cursor.getLeft());
                        result.biggest.setLeft(result.left_tree);
                        result.biggest.setRight(cursor.getRight());
                        root = result.biggest;
                    }
                } else {// not root
                    if (cursor.getLeft() == null) {
                        if (parent.getLeft() == cursor) {
                            parent.setLeft(cursor.getRight());
                        } else {
                            parent.setRight(cursor.getRight());
                        }
                    } else {
                        // find biggest one on the left sub tree
                        Bean result = fetchAndRemoveBiggestNode(cursor.getLeft());
                        result.biggest.setLeft(result.left_tree);
                        result.biggest.setRight(cursor.getRight());
                        if(parent.getLeft()==cursor){
                            parent.setLeft(result.biggest);
                        }else{
                            parent.setRight(result.biggest);
                        }
                    }
                }
                return;
            } else if (res > 0) {
                parent = cursor;
                cursor = cursor.getLeft();
            } else {
                parent = cursor;
                cursor = cursor.getRight();
            }
        }
    }

    public class Bean {
        public BSNode left_tree;
        public BSNode biggest;
    }

    private Bean fetchAndRemoveBiggestNode(BSNode lnode) {
        if (lnode == null) {
            return null;
        }
        Bean bean = new Bean();
        if (lnode.getRight() == null) {
            bean.left_tree = lnode.getLeft();
            bean.biggest = lnode;
        } else {
            bean.left_tree = lnode;
            BSNode parent = lnode;
            lnode = lnode.getRight();
            while (lnode.getRight() != null) {
                parent = lnode;
                lnode = lnode.getRight();
            }
            parent.setRight(lnode.getLeft());
            bean.biggest = lnode;
        }
        bean.biggest.setLeft(null);
        bean.biggest.setRight(null);
        return bean;
    }
}
