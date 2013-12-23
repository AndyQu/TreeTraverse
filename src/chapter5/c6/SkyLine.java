package chapter5.c6;

import java.util.ArrayList;
import java.util.List;

import andy.util.Log;

public class SkyLine {
    private int start;
    private List<Segment>segments;
    
    public SkyLine(int start,Segment[]segs){
        if(segs==null||segs.length<=0){
            Log.en("segments empty. stop.");
            return;
        }
        this.start=start;
        segments=new ArrayList<Segment>();
        for(Segment s:segs){
            segments.add(s);
        }
    }
    
    public int getEndPoint(){
        return segments.get(segments.size()-1).end;
    }
    
    public class Segment{
        public int height;
        public int end;
        public Segment(int h,int e){
            height=h;
            end=e;
        }
    }

    public class Line{
        public int start;
        public int end;
        public Line(int s,int e){
            start=s;
            end=e;
        }
    }
    public class TripleSlice{
        public Line prefix;
        public Line overlap;
        public Line suffix;
    }
    public static TripleSlice findPrefixOverlapSuffix(Line lineA,Line lineB){
        //TODO
        return null;
    }
    
    public static SkyLine merge(SkyLine sky1, SkyLine sky2){
        int s1=sky1.start;
        int e1=sky1.getEndPoint();
        int s2=sky2.start;
        int e2=sky2.getEndPoint();
        return null;
    }
}
