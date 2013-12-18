package chapter4.e27e28;

import java.util.HashMap;
import java.util.Map;

import algorithm.tree.binary.interfaces.BNode;
import algorithm.tree.binary.interfaces.BidirectNode;
import andy.util.Log;

public class TreePartialSum {
    Map<String,Node>map=new HashMap<String,Node>();
    Node root=null;
    public TreePartialSum(){
    }
    public void insert(String name,int v){
        if(name==null){return;}
        if(map.containsKey(name)){
            Log.en("[insert]duplicate name:"+name);
            return;
        }
        Node newn=new Node();
        newn.setValue(v);
        newn.setLeftSum(0);
        map.put(name, newn);
        if(root==null){
            root=newn;
            return;
        }
        Node cur=root;
        while(true){
            if(cur.getValue()>=v){
                //update left sum, when going left
                cur.setLeftSum(cur.getLeftSum()+v);
                if(cur.getLeft()==null){
                    cur.setLeft(newn);
                    newn.setParent(cur);
                    break;
                }else{
                    cur=(Node) cur.getLeft();
                }
            }else{
                if(cur.getRight()==null){
                    cur.setRight(newn);
                    newn.setParent(cur);
                    break;
                }else{
                    cur=(Node) cur.getRight();
                }
            }
        }
    }
    public void delete(String name){
        //TODO
    }
    public void add(String name, int value){
        Node n=map.get(name);
        if(n==null){
            Log.en("[add]does not contain name:"+name);
            return;
        }
        n.setValue(n.getValue()+value);
        Node cur=n;
        while(true){
            cur=cur.p
        }
    }
    public int sum(int upperbound){
        //TODO
        return 0;
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
            this.right=(Node) n;
        }

        @Override
        public void setLeft(BNode<Integer> n) {
            this.left=(Node) n;
        }

        @Override
        public BidirectNode<Integer> getParent() {
            return parent;
        }

        @Override
        public void setParent(BidirectNode<Integer> p) {
            this.parent=(Node) p;
        }

        @Override
        public Integer getValue() {
            return v;
        }

        @Override
        public void setValue(Integer v) {
            this.v=v;
        }
    }
}
