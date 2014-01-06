package chapter5.c6;

import java.util.ArrayList;

import andy.util.Log;

public class FlatSegment implements ISegment{
    public static Merger mergor=new Merger();
    static{
        SegTool.tool=mergor;
    }
    private double start;
    private double height;
    private double end;

    public FlatSegment(double h, double s, double e) {
        height = h;
        start=s;
        end = e;
    }
    
    @Override
    public ISegment subSeg(double s, double e) {
        if(start<=s && e<=end){
            return new FlatSegment(height,s,e);
        }else{
            Log.en("[subSeg][invalid parameter]("+start+","+end+")("+s+","+e+")");
            return null;
        }
    }
    
    @Override
    public String toString() {
        return "("+start+","+height+","+end+")";
    }
    
    public static class Merger implements ISegmentTool{
        @Override
        public Bean merge(ISegment sA, ISegment sB) {
            if(sA.getStart()!=sB.getStart()){
                Log.en("[ISegmentTool][merge][invalid parameter]start point not equal:"+sA.getStart()+","+sB.getStart());
                return null;
            }
            ISegment.Bean result=new Bean();
            result.result=new ArrayList<ISegment>();
            if(sA.getEnd()==sB.getEnd()){
                result.result.add(new FlatSegment(Math.max(((FlatSegment)sA).height,((FlatSegment)sB).height), sA.getStart(), sA.getEnd()));
                result.segALeft=null;
                result.segBLeft=null;
            }else if(sA.getEnd()>sB.getEnd()){
                result.result.add(new FlatSegment(Math.max(((FlatSegment)sA).height,((FlatSegment)sB).height), sA.getStart(), sB.getEnd()));
                result.segALeft=sA.subSeg(sB.getEnd(), sA.getEnd());
                result.segBLeft=null;
            }else{
                result.result.add(new FlatSegment(Math.max(((FlatSegment)sA).height,((FlatSegment)sB).height), sA.getStart(), sA.getEnd()));
                result.segALeft=null;
                result.segBLeft=sB.subSeg(sA.getEnd(), sB.getEnd());
            }
            return result;
        }

        @Override
        public ISegment createFlat(double start, double end, double value) {
            return new FlatSegment(value, start, end);
        }

        @Override
        public ISegment concatenate(ISegment sA, ISegment sB) {
            if(sA.getEnd()!=sB.getStart()){
                Log.en("[ISegmentTool][concatenate]sA'end != sB'start. "+sA.getEnd()+","+sB.getStart());
                return null;
            }else{
                FlatSegment ssA=(FlatSegment) sA;
                FlatSegment ssB=(FlatSegment) sB;
                if(ssA.height==ssB.height){
                    return new FlatSegment(ssA.height,ssA.getStart(),ssB.getEnd());
                }else{
                    return null;
                }
            }
        }
    }
    
    @Override
    public double getStart() {
        return start;
    }

    @Override
    public double getEnd() {
        return end;
    }
    
    @Override
    public void setStart(double s) {
        start=s;
    }
    
    @Override
    public void setEnd(double e) {
        end=e;
    }
}
