package chapter5.c6;

import andy.util.Log;

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
        double nsh=get(angle,(s-start),sh);
        double neh=get(angle,(e-start),sh);
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
            }else if(ssA.getEnd()==ssB.getEnd()){
                res.segALeft=null;
                res.segBLeft=null;
            }else{
                res.segALeft=null;
                res.segBLeft=ssB.subSeg(ssA.getEnd(), ssB.getEnd());
            }
            if(isEqual(ssA.getAngle(),ssB.getAngle())){
                res.result=new SlashSegment(ssA.getStart(), Math.max(ssA.sh, ssB.sh), 
                                e, get(ssA.getAngle(), e-ssA.getStart(), Math.max(ssA.sh, ssB.sh)));
            }else{
                //calculate the cross point
            }
            return res;
        }

        @Override
        public ISegment concatenate(ISegment sA, ISegment sB) {
            SlashSegment ssA=(SlashSegment) sA;
            SlashSegment ssB=(SlashSegment) sB;
            if(isEqual(ssA.getAngle(),ssB.getAngle())){
                if(sA.getStart()==sB.getEnd()){
                    return new SlashSegment(ssA.getStart(), ssA.sh, ssB.getEnd(), ssB.eh);
                }else if(sA.getEnd()==sB.getStart()){
                    return new SlashSegment(ssB.getStart(), ssB.sh, ssA.getEnd(), ssA.eh);
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
    private static double get(double angle, double length, double base){
        return angle*length+base;
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
