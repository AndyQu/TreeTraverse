package chapter4.e27e28;

import java.util.HashMap;
import java.util.Map;

import algorithm.tree.binary.GeneralTreeOperation;
import algorithm.tree.binary.GeneralTreeOperation.Bean;
import algorithm.tree.binary.interfaces.BNode;
import algorithm.tree.binary.interfaces.BSearchNode;
import algorithm.tree.binary.interfaces.BidirectNode;
import andy.util.Log;

public class TreePartialSum {
  
    public static void main(String[]args){
        testInsert();
        testAdd();
        testDelete();
        testWithEqualElements();
    }
    private static void testInsert(){
        TreePartialSum treePS=buildTree();
        //20,   25, 30, 35, 40, 45, 50, 80,     85,     90, 100
        //0,    20, 45, 75, 110,150,195,245,    325,    410,500,    600
        Log.en("================Test: insert only============");
        int upper;
        for(upper=20;upper<=106;upper+=5){
            Log.en("sum below "+upper+":"+treePS.sum(upper));
        }
        Log.en("================Test: insert only Done============");
    }
    private static void testDelete(){
        Log.en("================Test: delete only============");
        //20,   25, 30, 35, 40, 45, 50, 80,     85,     90, 100
        //20,   25, 30, 35, 40, 50, 80,     85,     90, 100
        //0,    20, 45, 75, 110,150,200,    280,    365,455,555
        TreePartialSum treePS=buildTree();
        treePS.delete("e");
        int upper;
        for(upper=20;upper<=106;upper+=5){
            Log.en("sum below "+upper+":"+treePS.sum(upper));
        }
        Log.en("================Test: delete only Done============");
    }
    private static void testAdd(){
        /**
         * add, not violate binary property
         */
        Log.en("================Test: add value to a node, not violating binary property============");
        //20,   25, 32, 35, 40, 45, 50, 80,     85,     90, 100
        //0,    20, 45, 77, 112,152,197,247,    327,    412,502,    602
        TreePartialSum treePS=new TreePartialSum();
        treePS.add("i", 2);
        int upper;
        for(upper=20;upper<=106;upper+=5){
            Log.en("sum below "+upper+":"+treePS.sum(upper));
        }
        Log.en("================Test: add value to a node, not violating binary property Done=========");

        /**
         * add, violate binary property
         */
        Log.en("================Test: add value to a node, violating binary property=============");
        //20,   25, 35, 36, 40, 45, 50, 80,     85,     90, 100
        //0,    20, 45, 80, 116,156,201,251,    331,    416,506,    606
        treePS=buildTree();
        treePS.add("i", 6);
        for(upper=20;upper<=106;upper+=5){
            Log.en("sum below "+upper+":"+treePS.sum(upper));
        }
        Log.en("================Test: add value to a node, violating binary property Done========");
    }
    private static TreePartialSum buildTree(){
        TreePartialSum treePS=new TreePartialSum();
        treePS.insert("a", 50);
        treePS.insert("b", 25);
        treePS.insert("d", 20);
        treePS.insert("e", 45);
        treePS.insert("g", 35);
        treePS.insert("i", 30);
        treePS.insert("j", 40);
        
        treePS.insert("c", 100);
        treePS.insert("f", 80);
        treePS.insert("h", 90);
        treePS.insert("k", 85);
        return treePS;
    }
    
    private static void testWithEqualElements(){
        Log.en("================Test: delete a node from a tree which has equal elements=============");
        TreePartialSum tree=buildTree();
        tree.insert("d1", 20);
        tree.insert("d2", 20);
        tree.insert("d3", 20);
        tree.delete("b");
        //20,20,20,20,  30, 35, 40, 45, 50, 80, 85, 90, 100
        //0,            80, 110,145,185,230,280,360,445,535,    635
        int upper;
        for(upper=20;upper<=106;upper+=5){
            Log.en("sum below "+upper+":"+tree.sum(upper));
        }
        Log.en("================Test Done: delete a node from a tree which has equal elements=============");

    }
    
    Map<String, Node> map = new HashMap<String, Node>();
    Node root = null;

    public TreePartialSum() {
    }

    public void insert(String name, int v) {
        if (name == null) {
            return;
        }
        if (map.containsKey(name)) {
            Log.en("[insert]duplicate name:" + name + ". don't insert");
            return;
        }
        Node newn = new Node();
        newn.setValue(v);
        newn.setLeftSum(0);
        map.put(name, newn);
        if (root == null) {
            root = newn;
            return;
        }
        Node cur = root;
        while (true) {
            if (cur.getValue() > v) {
                // update left sum, when going left
                cur.setLeftSum(cur.getLeftSum() + v);
                if (cur.getLeft() == null) {
                    cur.setLeft(newn);
                    newn.setParent(cur);
                    break;
                } else {
                    cur = (Node) cur.getLeft();
                }
            } else if (cur.getValue() < v || cur.getValue() == v) {
                if (cur.getRight() == null) {
                    cur.setRight(newn);
                    newn.setParent(cur);
                    break;
                } else {
                    cur = (Node) cur.getRight();
                }
            }
        }
    }

    public void delete(String name) {
        Node deletedN = map.get(name);
        if (deletedN == null) {
            Log.en("[delete]does not contain name:" + name + ". don't delete");
            return;
        }
        map.remove(name);
        // update all parents' left sum
        updateUpwards(deletedN, 0 - deletedN.getValue());
        Node replaceNode;
        if (deletedN.getLeft() == null) {
            replaceNode = (Node) deletedN.getRight();
        } else if (deletedN.getRight() == null) {
            replaceNode = (Node) deletedN.getLeft();
        } else {
            // find biggest one on the left sub tree
            Bean result = GeneralTreeOperation.fetchAndRemoveLargestNode((BNode<Integer>) deletedN.getLeft());
            result.biggest.setLeft(result.left_tree);
            result.biggest.setRight(deletedN.getRight());
            replaceNode = (Node) result.biggest;
            replaceNode.setLeftSum(deletedN.getLeftSum() - replaceNode.getValue());
        }
        if (deletedN == root) {
            root = replaceNode;
            replaceNode.setParent(null);
        }else {
            Node parent = (Node) deletedN.getParent();
            if (parent.getLeft() == deletedN) {
                parent.setLeft(replaceNode);
            } else {
                parent.setRight(replaceNode);
            }
            if(replaceNode!=null){
                replaceNode.setParent(parent);
            }
        }
    }

    public void add(String name, int value) {
        Node n = map.get(name);
        if (n == null) {
            Log.en("[add]does not contain name:" + name + ". don't add");
            return;
        }
        int newV=n.getValue()+value;
        boolean orderViolated = false;
        if (n.getParent() != null) {
            Node p = (Node) n.getParent();
            if (p.getLeft() == n) {
                orderViolated = p.getValue() < newV;
            } else {
                orderViolated = p.getValue() > newV;
            }
        }
        if (!orderViolated) {
            if (n.getLeft() != null) {
                orderViolated = n.getLeft().getValue() > newV;
            }
        }
        if (!orderViolated) {
            if (n.getRight() != null) {
                orderViolated = n.getRight().getValue() < newV;
            }
        }
        if (orderViolated) {
            delete(name);
            insert(name, newV);
        } else {
            n.setValue(newV);
            updateUpwards(n, value);
        }
    }

    private void updateUpwards(Node n, int value) {
        if(n==null){
            Log.en("[updateUpwards]null node");
            return;
        }
        Node parent;
        while(true){
            parent=(Node) n.getParent();
            if(parent==null){
                break;
            }
            if(parent.getLeft()==n){
                parent.setLeftSum(parent.getLeftSum()+value);
            }
            n=parent;
        }
    }

    public int sum(int upperbound) {
        int sum = 0;
        Node cur = root;
        while (cur != null) {
            if (cur.getValue() < upperbound) {
                sum = sum + cur.getLeftSum() + cur.getValue();
                cur = (Node) cur.getRight();
            } else if (cur.getValue() == upperbound) {
                cur = (Node) cur.getLeft();
            } else {
                cur = (Node) cur.getLeft();
            }
        }
        return sum;
    }

    public class Node implements BidirectNode<Integer>{
        private Node left;
        private Node right;
        private Node parent;
        private int leftSum;
        private int v;

        public int getLeftSum() {
            return leftSum;
        }

        public void setLeftSum(int leftSum) {
            this.leftSum = leftSum;
        }

        @Override
        public BNode<Integer> getLeft() {
            return left;
        }

        @Override
        public BNode<Integer> getRight() {
            return right;
        }

        @Override
        public void setRight(BNode<Integer> n) {
            this.right = (Node) n;
        }

        @Override
        public void setLeft(BNode<Integer> n) {
            this.left = (Node) n;
        }

        @Override
        public BidirectNode<Integer> getParent() {
            return parent;
        }

        @Override
        public void setParent(BidirectNode<Integer> p) {
            this.parent = (Node) p;
        }

        @Override
        public Integer getValue() {
            return v;
        }

        @Override
        public void setValue(Integer v) {
            this.v = v;
        }
    }
}
