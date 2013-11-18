package algorithm.tree.binary;

import algorithm.tree.binary.interfaces.BSearchNode;
import algorithm.tree.binary.interfaces.BNode;
import algorithm.tree.binary.interfaces.BSearchTree;
import andy.util.Log;

public class BSearchTreeNorm<E extends Comparable<E>> implements BSearchTree<E>{
    private BSearchNode<E> root = null;

    public BSearchTreeNorm() {
    }

    @Override
    public void insert(E v) {
        if (root == null) {
            root = new BSearchNode<E>(v);
        }
        BSearchNode<E> cursor = root;
        while (cursor != null) {
            int res = cursor.getValue().compareTo(v);
            if (res > 0) {
                if (cursor.getLeft() == null) {
                    cursor.setLeft(new BSearchNode<E>(v));
                    return;
                }
                cursor = (BSearchNode<E>) cursor.getLeft();
            } else if (res < 0) {
                if (cursor.getRight() == null) {
                    cursor.setRight(new BSearchNode<E>(v));
                    return;
                }
                cursor = (BSearchNode<E>) cursor.getRight();
            } else {
                return;
            }
        }
    }

    @Override
    public BSearchNode<E> search(E v) {
        BSearchNode<E> cursor = root;
        while (cursor != null) {
            int res = cursor.getValue().compareTo(v);
            if (res == 0) {
                return cursor;
            } else if (res > 0) {
                cursor = (BSearchNode<E>) cursor.getLeft();
            } else {
                cursor = (BSearchNode<E>) cursor.getRight();
            }
        }
        return null;
    }

    /**
     * the key point for writing codes nice and clean is the concept: "replace node", instead of "left biggest node"
     * suppose node A is the target node to be deleted.
     * If A has no child, then no replace no.
     * If A only has left child tree, then the replace node should be A's left child, we don't need to find the left biggest node.
     * If A only has right child tree, then the replace node should be A's right child.
     * If A has both left and right child trees, then the replace node should be A's left biggest node.
     * 
     * So the replace node is not always the left biggest node.
     * 
     * use the replace node to replace the deleted node, and use "parent" to link it.
     * @param v
     */
    @Override
    public void remove(E v) {
        BSearchNode<E> cursor = root;
        BSearchNode<E> parent = null;
        while (cursor != null) {
            int res = cursor.getValue().compareTo(v);
            if (res == 0) {
                BNode<E> replace = null;
                if (cursor.getLeft() == null) {
                    replace = cursor.getRight();
                } else if (cursor.getRight() == null) {
                    replace = cursor.getLeft();
                } else {
                    // find biggest one on the left sub tree
                    Bean result = fetchAndRemoveBiggestNode((BSearchNode<E>) cursor.getLeft());
                    result.biggest.setLeft(result.left_tree);
                    result.biggest.setRight(cursor.getRight());
                    replace = result.biggest;
                }
                if (cursor == root) {//parent must be null;
                    root = (BSearchNode<E>) replace;
                } else {
                    if (parent.getLeft() == cursor) {
                        parent.setLeft(replace);
                    } else {
                        parent.setRight(replace);
                    }
                }
                return;
            } else if (res > 0) {
                parent = cursor;
                cursor = (BSearchNode<E>) cursor.getLeft();
            } else {
                parent = cursor;
                cursor = (BSearchNode<E>) cursor.getRight();
            }
        }
    }

    public class Bean {
        public BSearchNode<E> left_tree;
        public BSearchNode<E> biggest;
    }

    private Bean fetchAndRemoveBiggestNode(BSearchNode<E> lnode) {
        if (lnode == null) {
            return null;
        }
        Bean bean = new Bean();
        if (lnode.getRight() == null) {
            bean.left_tree = (BSearchNode<E>) lnode.getLeft();
            bean.biggest = lnode;
        } else {
            bean.left_tree = lnode;
            BSearchNode<E> parent = lnode;
            lnode = (BSearchNode<E>) lnode.getRight();
            while (lnode.getRight() != null) {
                parent = lnode;
                lnode = (BSearchNode<E>) lnode.getRight();
            }
            parent.setRight(lnode.getLeft());
            bean.biggest = lnode;
        }
        bean.biggest.setLeft(null);
        bean.biggest.setRight(null);
        return bean;
    }

    public static void main(String[] args) {
        testDeleteLeaf(generateTree());
        testDeleteNodeWithOnlyLeftTree(generateTree());
        testDeleteNodeWithOnlyRightTree(generateTree());
        testDeleteNodeWithBothChilds(generateTree());
    }
    private static BSearchTreeNorm<Integer>generateTree(){
        BSearchTreeNorm<Integer> tree = new BSearchTreeNorm<Integer>();
        tree.insert(50);
        tree.insert(60);
        tree.insert(70);
        tree.insert(40);
        tree.insert(30);
        tree.insert(42);
        tree.insert(41);
        tree.insert(48);
        tree.insert(46);
        tree.insert(43);
        tree.insert(47);
        tree.insert(65);
        tree.insert(90);
        return tree;
    }
    private static void testDeleteLeaf(BSearchTreeNorm<Integer>tree){
        Log.en("=======testDeleteLeaf");
        BinaryTreeTraverse.doit(tree.root);
        Integer target = 43;
        Log.en("=======remove " + target);
        tree.remove(target);
        BinaryTreeTraverse.doit(tree.root);
    }
    private static void testDeleteNodeWithOnlyLeftTree(BSearchTreeNorm<Integer>tree){
        Log.en("=======testDeleteNodeWithOnlyLeftTree");
        BinaryTreeTraverse.doit(tree.root);
        Integer target = 48;
        Log.en("=======remove " + target);
        tree.remove(target);
        BinaryTreeTraverse.doit(tree.root);
    }
    private static void testDeleteNodeWithOnlyRightTree(BSearchTreeNorm<Integer>tree){
        Log.en("=======testDeleteNodeWithOnlyRightTree");
        BinaryTreeTraverse.doit(tree.root);
        Integer target = 60;
        Log.en("=======remove " + target);
        tree.remove(target);
        BinaryTreeTraverse.doit(tree.root);
    }
    private static void testDeleteNodeWithBothChilds(BSearchTreeNorm<Integer>tree){
        Log.en("=======testDeleteNodeWithBothChilds");
        BinaryTreeTraverse.doit(tree.root);
        Integer target = 42;
        Log.en("=======remove " + target);
        tree.remove(target);
        BinaryTreeTraverse.doit(tree.root);
    }

    @Override
    public BSearchNode<E> getRoot() {
        return this.root;
    }

}
