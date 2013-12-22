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
    Map<String,Node>map=new HashMap<String,Node>();
    Node root=null;
    public TreePartialSum(){
    }
    public void insert(String name,int v){
        if(name==null){return;}
        if(map.containsKey(name)){
            Log.en("[insert]duplicate name:"+name+". don't insert");
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
            if(cur.getValue()>v){
                //update left sum, when going left
                cur.setLeftSum(cur.getLeftSum()+v);
                if(cur.getLeft()==null){
                    cur.setLeft(newn);
                    newn.setParent(cur);
                    break;
                }else{
                    cur=(Node) cur.getLeft();
                }
            }else if(cur.getValue()<v||cur.getValue()==v){
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
    	Node deletedN=map.get(name);
        if(deletedN==null){
            Log.en("[delete]does not contain name:"+name+". don't delete");
            return;
        }
        map.remove(name);
        //update all parents' left sum
        add(name,0-deletedN.getValue());
        Node replaceNode;
        if(deletedN.getLeft()==null){
        	replaceNode=(Node) deletedN.getRight();
        }else if(deletedN.getRight()==null){
        	replaceNode=(Node) deletedN.getLeft();
        }else{
        	// find biggest one on the left sub tree
            Bean result = GeneralTreeOperation.fetchAndRemoveLargestNode((BSearchNode<Integer>) deletedN.getLeft());
            result.biggest.setLeft(result.left_tree);
            result.biggest.setRight(deletedN.getRight());
            replaceNode=(Node) result.biggest;
            replaceNode.setLeftSum(deletedN.getLeftSum()-replaceNode.getValue());
        }
        if(deletedN==root){
        	root=replaceNode;
        	replaceNode.setParent(null);
        }else{
        	Node parent=(Node) deletedN.getParent();
        	if(parent.getLeft()==deletedN){
        		parent.setLeft(replaceNode);
        	}else{
        		parent.setRight(replaceNode);
        	}
        	replaceNode.setParent(parent);
        }
    }
    public void add(String name, int value){
        Node n=map.get(name);
        if(n==null){
            Log.en("[add]does not contain name:"+name+". don't add");
            return;
        }
        n.setValue(n.getValue()+value);
        boolean orderViolated=false;
        if(n.getParent()!=null){
        	Node p=(Node) n.getParent();
        	if(p.getLeft()==n){
        		orderViolated=p.getValue()<n.getValue();
        	}else{
        		orderViolated=p.getValue()>n.getValue();
        	}
        }
        if(!orderViolated){
        	if(n.getLeft()!=null){
        		orderViolated=n.getLeft().getValue()>n.getValue();
        	}
        }
        if(!orderViolated){
        	if(n.getRight()!=null){
        		orderViolated=n.getRight().getValue()<n.getValue();
        	}
        }
        if(orderViolated){
        	delete(name);
        	insert(name,n.getValue());
        }else{
        	updateUpwards(n,value);
        }
    }
    private void updateUpwards(Node n, int value){
    	//TODO
    }
    public int sum(int upperbound){
        int sum=0;
        Node cur=root;
        while(cur!=null){
        	if(cur.getValue()<upperbound){
        		sum=sum+cur.getLeftSum()+cur.getValue();
        		cur=(Node) cur.getRight();
        	}else if(cur.getValue()==upperbound){
        		cur=(Node) cur.getRight();
        	}else{
        		cur=(Node) cur.getLeft();
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
