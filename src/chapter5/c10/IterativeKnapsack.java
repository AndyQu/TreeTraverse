package chapter5.c10;

import java.util.List;

public class IterativeKnapsack {
    private static boolean[][]valid;
    private static boolean[][]sameTotal;
    private static int[]items;
    private static int total;
    public static List<Integer> doit(int[]objs, int K){
        total=K;
        valid=new boolean[total+1][];
        sameTotal=new boolean[total+1][];
        for(int i=0;i<total+1;i++){
            valid[i]=new boolean[objs.length];
            sameTotal[i]=new boolean[objs.length];
        }
        items=objs;
        return process();
    }
    private static List<Integer> process(){
        for(int i=0;i<total+1;i++){
            if(i==items[0]){
                valid[i][0]=true;
            }
        }
        //TODO
        return null;
    }
}
