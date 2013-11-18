package algorithm.tree.binary;

import andy.util.Log;

public class BinaryTreeTraverse {
    public static void doit(BinaryNode cursor){
        if(cursor==null)return;
        Log.en(cursor.toString());
        doit(cursor.getLeft());
        doit(cursor.getRight());
    }
}
