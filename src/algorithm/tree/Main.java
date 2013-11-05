package algorithm.tree;

import algorithm.tree.data.BinaryNode;
import algorithm.tree.data.MultiNode;
import algorithm.tree.morris.BinaryMorrisTraverse;
import algorithm.tree.morris.MultiMorrisTraverse;
import algorithm.tree.oberon.MultiOberonTraverse;

public class Main {
    public static void main(String[]argvs){
        //testInOrder(getNode());
        /*BinaryNode root=getNoLoopTree();
        testVisitWithoutEMem(root);
        testPreOrder(root);*/
        
        /*BinaryNode lTree=getLoopTree();
        testVisitWithoutEMem(lTree);
        testPreOrder(lTree);
        testInOrder(lTree);*/
        //testMorrisBinary();
        //testMultiVisit();
        
        testMorrisMultiVisit();
        
        //testOberonMultiVisit();
    }
    public static BinaryNode getBinaryLoopTree(){
        BinaryNode root=new BinaryNode("a");
        root.setLeft(new BinaryNode("b"));
        root.setRight(new BinaryNode("c"));
        
        BinaryNode left=root.getLeft();
        //b
        left.setLeft(new BinaryNode("d"));
        left.setRight(new BinaryNode("e"));
        
        BinaryNode right=root.getRight();
        //c
        right.setLeft(new BinaryNode("f"));
        right.setRight(new BinaryNode("g"));
        
        //e->right=c
        left.getRight().setRight(right);
        //f->left=b
        right.getLeft().setLeft(left);
        return root;
    }
    public static BinaryNode getSimpleTree(){
        BinaryNode a=new BinaryNode("a");
        BinaryNode b=new BinaryNode("b");
        BinaryNode c=new BinaryNode("c");
        a.setLeft(b);
        b.setLeft(c);
        return a;
    }
    public static BinaryNode getBinaryNoLoopTree(){
        BinaryNode root=new BinaryNode("a");
        root.setLeft(new BinaryNode("b"));
        root.setRight(new BinaryNode("c"));
        
        BinaryNode tmp=root.getLeft();
        //b
        tmp.setLeft(new BinaryNode("d"));
        tmp.setRight(new BinaryNode("e"));
        
        BinaryNode r1=tmp.getRight();
        //e
        r1.setLeft(new BinaryNode("h"));
        r1.setRight(new BinaryNode("i"));
        
        r1=r1.getRight();
        //i
        r1.setLeft(new BinaryNode("l"));
        r1.setRight(new BinaryNode("m"));
        
        r1=r1.getLeft();
        //l
        r1.setLeft(new BinaryNode("n"));
        r1.setRight(new BinaryNode("p"));
        
        tmp=tmp.getLeft();
        //d
        tmp.setLeft(new BinaryNode("f"));
        tmp.setRight(new BinaryNode("g"));
        
        tmp=tmp.getLeft();
        //f
        tmp.setLeft(new BinaryNode("j"));
        tmp.setRight(new BinaryNode("k"));
        
        tmp.getLeft().setLeft(new BinaryNode("x"));
        
        return root;
    }
    public static MultiNode getMultiNoLoopTree(){
        MultiNode a=new MultiNode("a", 3);
        MultiNode b=new MultiNode("b", 4);
        MultiNode c=new MultiNode("c", 3);
        a.childs[0]=b;
        a.childs[1]=null;
        a.childs[2]=c;
        
        MultiNode d=new MultiNode("d", 2);
        MultiNode e=new MultiNode("e", 4);
        MultiNode f=new MultiNode("f", 2);
        b.childs[0]=null;
        b.childs[1]=d;
        b.childs[2]=e;
        b.childs[3]=f;
        
        MultiNode j=new MultiNode("j", 2);
        MultiNode k=new MultiNode("k", 2);
        MultiNode l=new MultiNode("l", 2);
        MultiNode m=new MultiNode("m", 2);
        e.childs[0]=j;
        e.childs[1]=k;
        e.childs[2]=l;
        e.childs[3]=m;
        
        MultiNode g=new MultiNode("g", 2);
        MultiNode h=new MultiNode("h", 2);
        c.childs[0]=g;
        c.childs[1]=null;
        c.childs[2]=h;
        
        MultiNode n=new MultiNode("n",2);
        g.childs[0]=n;
        g.childs[1]=null;
        return a;
    } 
    public static MultiNode getMultiLoopTree(){
        MultiNode a=new MultiNode("a", 3);
        MultiNode b=new MultiNode("b", 4);
        MultiNode c=new MultiNode("c", 3);
        a.childs[0]=b;
        a.childs[1]=null;
        a.childs[2]=c;
        
        MultiNode d=new MultiNode("d", 2);
        MultiNode e=new MultiNode("e", 4);
        MultiNode f=new MultiNode("f", 2);
        b.childs[0]=null;
        b.childs[1]=d;
        b.childs[2]=e;
        b.childs[3]=f;
        
        
        MultiNode j=new MultiNode("j", 2);
        MultiNode k=new MultiNode("k", 2);
        MultiNode l=new MultiNode("l", 2);
        MultiNode m=new MultiNode("m", 2);
        e.childs[0]=j;
        e.childs[1]=k;
        e.childs[2]=l;
        e.childs[3]=m;
        
        MultiNode g=new MultiNode("g", 2);
        MultiNode h=new MultiNode("h", 2);
        c.childs[0]=g;
        c.childs[1]=null;
        c.childs[2]=h;
        
        MultiNode n=new MultiNode("n",2);
        g.childs[0]=n;
        g.childs[1]=null;
        
        /*create a loop*/
        f.childs[0]=null;
        f.childs[1]=c;
        g.childs[1]=b;
        return a;
    } 
    public static void testPreOrder(BinaryNode n){
        System.out.println("================Pre Order Visit===============");
        TOperation.preOrderVisit(n);
    }
    public static void testInOrder(BinaryNode n){
        System.out.println("================In  Order Visit===============");
        TOperation.inOrderVisit(n);
    }
    public static void testVisitWithoutEMem(BinaryNode n){
        System.out.println("================Visit Without Mem===============");
        TOperation.visitWithoutExternalMem(n);
    }
    public static void testMorrisBinary(){
        //BinaryNode root=getBinaryNoLoopTree();
        //testPreOrder(root);
        //MorrisTraverse.preOrderVisit(root);
        //MorrisTraverse.postOrderVisit(root);
        BinaryMorrisTraverse.inOrder2Visit(getSimpleTree());
    }
    public static void testMultiVisit(){
        MultiMorrisTraverse.visit(getMultiNoLoopTree());
    }
    public static void testMorrisMultiVisit(){
        MultiMorrisTraverse.morrisVisit(getMultiNoLoopTree());
    }
    public static void testOberonMultiVisit(){
        MultiOberonTraverse.traverse(getMultiLoopTree());
        MultiOberonTraverse.stackTraverse(getMultiLoopTree() );
    }
}
