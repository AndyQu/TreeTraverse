package chapter5.c6;


public class Test {
    public static void main(String[]args){
        SkyLine skyA=new SkyLine(1, 11, 5);
        SkyLine skyB=new SkyLine(2, 6, 7);
        SkyLine skyC=new SkyLine(3,13,9);
        SkyLine skyD=new SkyLine(12,7,16);
        SkyLine skyE=new SkyLine(14,3,25);
        SkyLine skyF=new SkyLine(19,18,22);
        SkyLine skyG=new SkyLine(23,13,29);
        
        SkyLine.merge(skyA,skyB,skyC,skyD,skyE,skyF,skyG).show();
    }
}
