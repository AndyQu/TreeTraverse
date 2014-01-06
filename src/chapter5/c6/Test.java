package chapter5.c6;


public class Test {
    public static void main(String[]args){
//        testFlatSegment();
        testSlashSegment();
    }
    private static void testSlashSegment(){
    	SkyLine skyA=new SkyLine(0,0, 10,10);
    	SkyLine skyB=new SkyLine(5,5, 12,12);
    	SkyLine.merge(skyA,skyB).show();
    	
    	SkyLine skyC=new SkyLine(0,0, 10,10);
    	SkyLine skyD=new SkyLine(5,8, 12,12);
    	SkyLine.merge(skyC,skyD).show();
    	
    	SkyLine skyE=new SkyLine(0,0, 	10,10);
    	SkyLine skyF=new SkyLine(0,10, 	10,0);
    	SkyLine.merge(skyE,skyF).show();
    }
    private static void testFlatSegment(){
    	SkyLine skyA=new SkyLine(1, 11, 5);
        SkyLine skyB=new SkyLine(2, 6, 7);
        SkyLine skyC=new SkyLine(3,13,9);
        SkyLine skyD=new SkyLine(12,7,16);
        SkyLine skyE=new SkyLine(14,3,25);
        SkyLine skyF=new SkyLine(19,18,22);
        
        SkyLine skyG=new SkyLine(23,13,29);
        SkyLine skyJ=new SkyLine(29,13,50);
        SkyLine skyH=new SkyLine(29,20,50);
        
        SkyLine.merge(skyA,skyB,skyC,skyD,skyE,skyF,skyG).show();
        
        //covered
        SkyLine.merge(skyE,skyF).show();
        
        //not overlaped
        SkyLine.merge(skyD,skyA).show();
        
        //connected
        SkyLine.merge(skyG,skyJ).show();
        SkyLine.merge(skyG,skyH).show();
    }
}
