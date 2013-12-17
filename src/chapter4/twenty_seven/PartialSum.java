package chapter4.twenty_seven;

import andy.util.Log;

public class PartialSum {
    private int[]   data;
    private int[]   psum;
    private boolean[] flag;
    public PartialSum(int[] data){
        if(data==null){
            throw new RuntimeException();
        }
        this.data=data;
        psum=new int[data.length];
        flag=new boolean[data.length];
        for(int i=0;i<psum.length;i++){
            psum[i]=0;
            flag[i]=false;
        }
        init();
    }
    private void init(){
        for(int i=0;i<data.length;i++){
            if(flag[i]){
                continue;
            }
            updateUpwards(i,data[i]);
        }
        Log.en("After Initialization:");
        for(int i=0;i<data.length;i++){
            Log.enn(psum[i]+",");
        }
        Log.en("");
    }
    private void updateUpwards(int index, int value){
        psum[index]+=value;
        flag[index]=true;
        while(true){
            index=getNextIndex(index);
            if(index>=data.length){
                break;
            }
            if(flag[index]==false){
                value+=data[index];
                flag[index]=true;
            }
            psum[index]+=value;
        }
    }
    /**
     * find the next number, which is the biggest in the current segment(2,4,8,16,......)
     * @param index
     * @return
     */
    private int getNextIndex(int index){
        index++;
        int added=(((index-1)^index)+1)>>1;
        return (index+added-1);
    }
    public void add(int index, int value){
        updateUpwards(index,value);
    }
    public int sum(int index){
        int sum=0;
        while(index>=0){
            sum+=psum[index];
            index++;
            index=(index-1)&index;
            index--;
        }
        return sum;
    }
    public void printSum(){
        Log.en("Partial Sum:");
        for(int i=0;i<data.length;i++){
            Log.enn(sum(i)+",");
        }
        Log.en("");
    }
    public static void main(String[]args){
        int[]data=new int[]{1,2,3,4,5,6,7,8,9,10};
        PartialSum ps=new PartialSum(data);
        ps.printSum();
        for(int i=0;i<data.length;i++){
            ps.add(i, 10);
            ps.printSum();
        }
    }
}
