package chapter5.c6;

import andy.util.Algebra;
import andy.util.Algebra.Point;
import andy.util.Log;

import java.util.*;

public class SlashSegment implements ISegment{
    public static Tool mergor=new Tool();
    static{
        SegTool.tool=mergor;
    }
    private double start;
    private double sh;
    private double end;
    private double eh;
    
    @Override
    public String toString(){
        return "(("+start+","+sh+"),("+end+","+eh+"))";
    }

    @Override
    public ISegment subSeg(double s, double e) {
        double angle=getAngle();
        double nsh=Algebra.getPointY(angle,(s-start),sh);
        double neh=Algebra.getPointY(angle,(e-start),sh);
        return new SlashSegment(s,nsh,e,neh);
    }
    
    public SlashSegment(double s,double sh,double e,double eh){
        if(s>=e){
            Log.en("[SlashSegment]start>=end."+s+","+e);
        }
        start=s;
        this.sh=sh;
        end=e;
        this.eh=eh;
    }
    
    public static class Tool implements ISegmentTool{
        @Override
        public ISegment createFlat(double start, double end, double value) {
            return new SlashSegment(start, value, end, value);
        }
        
        @Override
        public Bean merge(ISegment sA, ISegment sB) {
            if(sA.getStart()!=sB.getStart()){
                Log.en("[merge]2 segments don't start at the same point."+sA.getStart()+","+sB.getStart());
                return null;
            }
            SlashSegment ssA=(SlashSegment) sA;
            SlashSegment ssB=(SlashSegment) sB;
            ISegment.Bean res=new Bean();
            double e=Math.min(ssA.getEnd(), ssB.getEnd());
            if(ssA.getEnd()>ssB.getEnd()){
                res.segALeft=ssA.subSeg(ssB.getEnd(), ssA.getEnd());
                res.segBLeft=null;
                ssA=(SlashSegment) ssA.subSeg(ssA.getStart(), e);
            }else if(ssA.getEnd()==ssB.getEnd()){
                res.segALeft=null;
                res.segBLeft=null;
            }else{
                res.segALeft=null;
                res.segBLeft=ssB.subSeg(ssA.getEnd(), ssB.getEnd());
                ssB=(SlashSegment) ssB.subSeg(ssB.getStart(), e);
            }
            res.result=new ArrayList<ISegment>();
            
            Point crossP=Algebra.getCrossPoint(
                            new Point(ssA.getStart(),ssA.sh),
                            new Point(ssA.getEnd(), ssA.eh),
                            new Point(ssB.getStart(),ssB.sh),
                            new Point(ssB.getEnd(),ssB.eh));
            if(crossP==null){
                double base=Math.max(ssA.sh, ssB.sh);
                res.result.add(new SlashSegment(ssA.getStart(), base, 
                                e, Algebra.getPointY(ssA.getAngle(), e-ssA.getStart(), base)));
            }else if(crossP.x<=ssA.getStart() || crossP.x>=ssA.getEnd()){
                //cross point is not in (start, end)
                if(ssA.sh>=ssB.sh){
                    res.result.add(ssA);
                }else{
                    res.result.add(ssB);
                }
            }else{
                if(ssA.sh>ssB.sh){
                    res.result.add(new SlashSegment(ssA.getStart(), ssA.sh, crossP.x, crossP.y));
                    res.result.add(new SlashSegment(crossP.x, crossP.y, ssB.getEnd(), ssB.eh));
                }else{
                    res.result.add(new SlashSegment(ssB.getStart(), ssB.sh, crossP.x, crossP.y));
                    res.result.add(new SlashSegment(crossP.x, crossP.y, ssA.getEnd(), ssA.eh));
                }
            }
            return res;
        }

        @Override
        public ISegment concatenate(ISegment sA, ISegment sB) {
            SlashSegment ssA=(SlashSegment) sA;
            SlashSegment ssB=(SlashSegment) sB;
            if(isEqual(ssA.getAngle(),ssB.getAngle())){
                if(sA.getStart()==sB.getEnd()){
                    return new SlashSegment(ssB.getStart(), ssB.sh, ssA.getEnd(), ssA.eh);
                }else if(sA.getEnd()==sB.getStart()){
                    return new SlashSegment(ssA.getStart(), ssA.sh, ssB.getEnd(), ssB.eh);
                }else{
                    Log.en("[concatenate]2 segments don't connect.");
                    return null;
                }
            }else{
                return null;
            }
            
        }
    }
    
    private static boolean isEqual(double d1,double d2){
        return Math.abs(d1-d2)<=0.000001;
    }
    
    private double getAngle(){
        return (eh-sh)/(end-start);
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
