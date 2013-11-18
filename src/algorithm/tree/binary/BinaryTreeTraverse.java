package algorithm.tree.binary;

import algorithm.tree.binary.interfaces.BNode;
import andy.util.Log;

public class BinaryTreeTraverse {
    public static void doit(BNode cursor){
        if(cursor==null)return;
        Log.en(cursor.toString());
        doit(cursor.getLeft());
        doit(cursor.getRight());
    }
}
