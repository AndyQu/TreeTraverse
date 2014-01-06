package chapter5.c6;

import java.util.ArrayList;
import java.util.List;

import andy.util.Log;

public class SkyLine {
    private double start;
    private List<ISegment> segments;

    public static SkyLine merge(SkyLine...skys){
        SkyLine res=null;
        for(SkyLine s:skys){
            res=merge(s,res);
        }
        return res;
    }
    /**
     * merge sky1 and sky2
     * @param sky1
     * @param sky2
     * @return
     */
    public static SkyLine merge(SkyLine sky1, SkyLine sky2) {
        if(sky1==null){
            return sky2;
        }
        if(sky2==null){
            return sky1;
        }
        SkyLine merged = mergeIfNotOverlap(sky1, sky2);
        if (merged != null) {
            return merged;
        }
        TripleSlice slices = findPrefixOverlapPostfix(sky1, sky2);
        Line overlap = slices.overlap;
        merged = smerge(sky1.getSubSkyLine(overlap.start, overlap.end), sky2.getSubSkyLine(overlap.start, overlap.end));
        return concatenate(slices.prefix, concatenate(merged, slices.postfix));
    }
    
    private SkyLine() {

    }

    private SkyLine(double start) {
        this.start = start;
    }

    private void add(ISegment seg) {
        if (segments == null) {
            segments = new ArrayList<ISegment>();
        }
        segments.add(seg);
    }

    public SkyLine(double start, List<ISegment>segs){
        this.start=start;
        segments=segs;
    }
    
    public void show(){
        Log.enn("["+this.start);
        for(ISegment s:segments){
            Log.enn(s.toString());
        }
        Log.en("]");
    }

    private double getEndPoint() {
        return segments.get(segments.size() - 1).getEnd();
    }

    private SkyLine getSubSkyLine(double s, double e) {
        if (!(s < e && this.start <= s && e <= getEndPoint())) {
            Log.en("[getSubSkyLine]invalid given positions:" + s + "," + e);
            return null;
        }
        int sIndex = -1;
        int eIndex = -1;
        for (int i = 0; i < segments.size(); i++) {
            ISegment seg = segments.get(i);
            if (seg.getStart() <= s && s < seg.getEnd()) {
                sIndex = i;
            }
            if (seg.getStart() < e && e <= seg.getEnd()) {
                eIndex = i;
                break;
            }
        }
        if (sIndex < 0 || eIndex < 0) {
            Log.en("[getSubSkyLine]can't find sIndex or eIndex. This SkyLine's segments may be not continous.");
            return null;
        }
        SkyLine nsky = new SkyLine(s);
        ISegment startSeg=segments.get(sIndex);
        if(sIndex==eIndex){
            nsky.add(startSeg.subSeg(s, e));
        }else{
            nsky.add(startSeg.subSeg(s, startSeg.getEnd()));
            for (int i = sIndex+1; i < eIndex; i++) {
                nsky.add(segments.get(i));
            }
            if(sIndex<eIndex){
                ISegment endSeg = segments.get(eIndex);
                nsky.add(endSeg.subSeg(endSeg.getStart(), e));
            }
        }
        return nsky;
    }

    public static class Line {
        public double start;
        public double end;

        public Line(double s, double e) {
            start = s;
            end = e;
        }
    }

    public static class TripleSlice {
        public SkyLine prefix;
        public Line overlap;
        public SkyLine postfix;
    }

    /**
     * if SkyLine A and SkyLine B does not overlap, then merge A and B directly. Otherwise return null
     * 
     * @param skyA
     * @param skyB
     * @return
     */
    private static SkyLine mergeIfNotOverlap(SkyLine skyA, SkyLine skyB) {
        double s1 = skyA.start;
        double e1 = skyA.getEndPoint();
        double s2 = skyB.start;
        double e2 = skyB.getEndPoint();
        SkyLine merged = null;
        if (e1 == s2 || e2 == s1) {
            merged = concatenate(skyA, skyB);
        } else {
            merged = new SkyLine();
            merged.segments = new ArrayList<ISegment>();
            if (e1 < s2) {
                merged.start = s1;
                merged.segments.addAll(skyA.segments);
                merged.segments.add(SegTool.tool.createFlat(e1, s2, 0));
                merged.segments.addAll(skyB.segments);
            } else if (e2 < s1) {
                merged.start = s2;
                merged.segments.addAll(skyB.segments);
                merged.segments.add(SegTool.tool.createFlat(e2, s1, 0));
                merged.segments.addAll(skyA.segments);
            }
        }
        if (merged==null||merged.segments==null||merged.segments.size() <= 0) {
            return null;
        } else {
            return merged;
        }
    }

    /**
     * given skyA and skyB, if they are overlapped, then find the three parts(prefix,overlap and postfix).
     * @param skyA
     * @param skyB
     * @return
     */
    private static TripleSlice findPrefixOverlapPostfix(SkyLine skyA, SkyLine skyB) {
        double s1 = skyA.start;
        double e1 = skyA.getEndPoint();
        double s2 = skyB.start;
        double e2 = skyB.getEndPoint();
        SkyLine prefix = null;
        Line overlap = null;
        SkyLine postfix = null;
        if (s2 <= s1 && e1 <= e2) {
            if (s2 == s1) {
                prefix = null;
            } else {
                prefix = skyB.getSubSkyLine(s2, s1);
            }
            if (e1 == e2) {
                postfix = null;
            } else {
                postfix = skyB.getSubSkyLine(e1, e2);
            }
            overlap = new Line(s1, e1);
        } else if (s1 <= s2 && e2 <= e1) {
            if (s1 == s2) {
                prefix = null;
            } else {
                prefix = skyA.getSubSkyLine(s1, s2);
            }
            if (e2 == e1) {
                postfix = null;
            } else {
                postfix = skyA.getSubSkyLine(e2, e1);
            }
            overlap = new Line(s2, e2);
        } else if (s1 < s2 && s2 < e1 && e1 < e2) {
            prefix = skyA.getSubSkyLine(s1, s2);
            postfix = skyB.getSubSkyLine(e1, e2);
            overlap = new Line(s2, e1);
        } else if (s2 < s1 && s1 < e2 && e2 < e1) {
            prefix = skyB.getSubSkyLine(s2, s1);
            postfix = skyA.getSubSkyLine(e2, e1);
            overlap = new Line(s1, e2);
        }
        TripleSlice slices = new TripleSlice();
        slices.prefix = prefix;
        slices.postfix = postfix;
        slices.overlap = overlap;
        return slices;
    }


    /**
     * make sure sky1 and sky2 have the same starting point and ending point
     * 
     * @param sky1
     * @param sky2
     * @return
     */
    private static SkyLine smerge(SkyLine sky1, SkyLine sky2) {
        if (sky1.start != sky2.start || sky1.getEndPoint() != sky2.getEndPoint()) {
            Log.en("[smerge]two skylines don't have the same scope. quit.");
            return null;
        }
        double start=sky1.start;
        List<ISegment>segments=new ArrayList<ISegment>();
        ISegment segA=sky1.segments.get(0);
        int indexA=1;
        ISegment segB=sky2.segments.get(0);
        int indexB=1;
        
        while(segA!=null && segB!=null){
            ISegment.Bean result=SegTool.tool.merge(segA, segB);
            segments.addAll(result.result);
            if(result.segALeft==null){
                if(indexA<sky1.segments.size()){
                    segA=sky1.segments.get(indexA);
                    indexA++;
                }else{
                    segA=null;
                }
            }else{
                segA=result.segALeft;
            }
            if(result.segBLeft==null){
                if(indexB<sky2.segments.size()){
                    segB=sky2.segments.get(indexB);
                    indexB++;
                }else{
                    segB=null;
                }
            }else{
                segB=result.segBLeft;
            }
        }
        return new SkyLine(start, segments);
    }

    /**
     * make sure sky1 and sky2 can be connected, otherwise null will be returned.
     * @param sky1
     * @param sky2
     * @return
     */
    private static SkyLine concatenate(SkyLine sky1, SkyLine sky2) {
        if (sky1 == null) {
            return sky2;
        } else if (sky2 == null) {
            return sky1;
        } else {
            SkyLine linked = null;
            if (sky1.getEndPoint() == sky2.start) {
                linked = new SkyLine(sky1.start);
                linked.segments = new ArrayList<ISegment>();
                ISegment connect=SegTool.tool.concatenate(sky1.getLast(), sky2.getFirst());
                if(connect!=null){
                    linked.segments.addAll(sky1.segments.subList(0, sky1.segments.size()-1));
                    linked.segments.add(connect);
                    linked.segments.addAll(sky2.segments.subList(1, sky2.segments.size()));
                }else{
                    linked.segments.addAll(sky1.segments);
                    linked.segments.addAll(sky2.segments);
                }
            } else if (sky2.getEndPoint() == sky1.start) {
                linked = concatenate(sky2, sky1);
            }else{
                Log.en("can't concatenate 2 skylines.");
            }
            return linked;
        }

    }
    
    private ISegment getLast(){
        if(segments!=null && segments.size()>0){
            return segments.get(segments.size()-1);
        }else{
            return null;
        }
    }
    private ISegment getFirst(){
        if(segments!=null && segments.size()>0){
            return segments.get(0);
        }else{
            return null;
        }
    }
    
    public SkyLine(double start, double height,double end){
        this.start=start;
        segments=new ArrayList<ISegment>();
        segments.add(new FlatSegment(height,start, end));
    }
    
    public SkyLine(double start, double sh, double end, double eh){
    	this.start=start;
    	segments=new ArrayList<ISegment>();
    	segments.add(new SlashSegment(start, sh, end, eh));
    }
}
