package chapter5.c6;

import andy.util.Log;

public class Segment implements ISegment{
    public static Merger mergor=new Merger();
    static{
        SegTool.tool=mergor;
    }
    private int start;
    private int height;
    private int end;

    public Segment(int h, int s, int e) {
        height = h;
        start=s;
        end = e;
    }
    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }
    @Override
    public String toString() {
        return "("+start+","+height+","+end+")";
    }
    @Override
    public void setStart(int s) {
        start=s;
    }
    @Override
    public void setEnd(int e) {
        end=e;
    }
    
    public static class Merger implements ISegmentTool{
        @Override
        public Bean merge(ISegment sA, ISegment sB) {
            if(sA.getStart()!=sB.getStart()){
                Log.en("[ISegmentTool][merge][invalid parameter]start point not equal:"+sA.getStart()+","+sB.getStart());
                return null;
            }
            ISegment.Bean result=new Bean();
            if(sA.getEnd()==sB.getEnd()){
                result.result= new Segment(Math.max(((Segment)sA).height,((Segment)sB).height), sA.getStart(), sA.getEnd());
                result.segALeft=null;
                result.segBLeft=null;
            }else if(sA.getEnd()>sB.getEnd()){
                result.result= new Segment(Math.max(((Segment)sA).height,((Segment)sB).height), sA.getStart(), sB.getEnd());
                result.segALeft=sA.subSeg(sB.getEnd(), sA.getEnd());
                result.segBLeft=null;
            }else{
                result.result= new Segment(Math.max(((Segment)sA).height,((Segment)sB).height), sA.getStart(), sA.getEnd());
                result.segALeft=null;
                result.segBLeft=sB.subSeg(sA.getEnd(), sB.getEnd());
            }
            return result;
        }

        @Override
        public ISegment createFlat(int start, int end, int value) {
            return new Segment(value, start, end);
        }

        @Override
        public ISegment concatenate(ISegment sA, ISegment sB) {
            if(sA.getEnd()!=sB.getStart()){
                Log.en("[ISegmentTool][concatenate]sA'end != sB'start. "+sA.getEnd()+","+sB.getStart());
                return null;
            }else{
                Segment ssA=(Segment) sA;
                Segment ssB=(Segment) sB;
                if(ssA.height==ssB.height){
                    return new Segment(ssA.height,ssA.getStart(),ssB.getEnd());
                }else{
                    return null;
                }
            }
        }
    }

    @Override
    public ISegment subSeg(int s, int e) {
        if(start<=s && e<=end){
            return new Segment(height,s,e);
        }else{
            Log.en("[subSeg][invalid parameter]("+start+","+end+")("+s+","+e+")");
            return null;
        }
    }
}
