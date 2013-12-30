package chapter5.c10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import andy.util.Log;

public class RecursiveKnapsack {
    public static void main(String[]args){
        doit(new int[]{2,3,5,6},10);
    }
    
    private static int[]items=null;
    private static Map<Bean,List<Integer>>results=new HashMap<Bean, List<Integer>>();
    public static List<Integer> doit(int[]objs, int K){
        items=objs;
        results.clear();
        return process(objs.length,K);
    }
    private static List<Integer> process(int N, int K){
        Bean key=new Bean(N,K);
        Log.en("begin to calculate for "+key);
        if(N<=0||K<=0){
            Log.en("K is "+K+",skip");
            return null;
        }
        List<Integer> lst=results.get(key);
        if(lst!=null){
            Log.enn("got result from result map:");
            print(lst);
            Log.en("");
            return lst;
        }
        if(N==1){
            if(items[0]==K){
                lst=new ArrayList<Integer>();
                lst.add(1);
                put(key, lst);
                return lst;
            }else{
                Log.en("can't get result for key:"+key);
                return null;
            }
        }
        lst=process(N-1,K-items[N-1]);
        if(lst!=null){
            //must create a new list
            List<Integer> nlst=new ArrayList<Integer>();
            nlst.addAll(lst);
            nlst.add(N);
            put(key, nlst);
            return nlst;
        }
        lst=process(N-1,K);
        if(lst!=null){
            put(key, lst);
        }else{
            Log.en("can't get result for key:"+key);
        }
        return lst;
    }
    
    private static void put(Bean key,List<Integer>lst){
        results.put(key, lst);
        Log.enn("get a new result:"+key);
        print(lst);
        Log.en("");
    }
    
    private static void print(List<Integer>lst){
        for(Integer i:lst){
            Log.enn(","+items[i-1]);
        }
    }
    
    public static class Bean{
        public int N;
        public int K;
        public Bean(int Na,int Ka){
            N=Na;
            K=Ka;
        }
        @Override
        public boolean equals(Object obj){
            if(obj!=null && obj instanceof Bean){
                Bean b=(Bean)obj;
                return N==b.N&&K==b.K;
            }else{
                return false;
            }
        }
        @Override
        public int hashCode(){
            return N*17+K;
        }
        @Override
        public String toString(){
            return "(N:"+N+",K:"+K+")";
        }
    }
}
