package chapter5.c6;

import java.util.ArrayList;
import java.util.List;

import andy.util.Log;

public class SkyLine {
    private int start;
    private List<Segment> segments;

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

    private SkyLine(int start) {
        this.start = start;
    }

    private void add(Segment seg) {
        if (segments == null) {
            segments = new ArrayList<SkyLine.Segment>();
        }
        segments.add(seg);
    }

    public SkyLine(int start, int height,int end){
        this.start=start;
        segments=new ArrayList<SkyLine.Segment>();
        segments.add(new Segment(height,end));
    }
    
    public SkyLine(int start, List<Segment>segs){
        this.start=start;
        segments=segs;
    }
    
    public void show(){
        Log.enn("["+this.start);
        for(Segment s:segments){
            Log.enn("("+s.height+","+s.end+")");
        }
        Log.en("]");
    }

    private int getEndPoint() {
        return segments.get(segments.size() - 1).end;
    }

    private Segment getLastSegment() {
        return segments.get(segments.size() - 1);
    }

    private Segment getFirstSegment() {
        return segments.get(0);
    }

    private SkyLine getSubSkyLine(int start, int end) {
        int e = getEndPoint();
        if (start < end && this.start <= start && end <= e) {
            int lstart = this.start;
            int sIndex = -1;
            int eIndex = -1;
            for (int i = 0; i < segments.size(); i++) {
                Segment seg = segments.get(i);
                if (lstart <= start && start < seg.end) {
                    sIndex = i;
                }
                if (lstart < end && end <= seg.end) {
                    eIndex = i;
                    break;
                }
                lstart = seg.end;
            }
            if (sIndex < 0 || eIndex < 0) {
                Log.en("[getSubSkyLine]can't find sIndex or eIndex. This SkyLine's segments may be not continous.");
                return null;
            }
            SkyLine nsky = new SkyLine(start);
            for (int i = sIndex; i < eIndex; i++) {
                nsky.add(segments.get(i));
            }
            Segment endSeg = segments.get(eIndex);
            nsky.add(new Segment(endSeg.height, end));
            return nsky;
        } else {
            Log.en("[getSubSkyLine]invalid given positions:" + start + "," + end);
            return null;
        }
    }

    public static class Segment {
        public int height;
        public int end;

        public Segment(int h, int e) {
            height = h;
            end = e;
        }
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int s, int e) {
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
        int s1 = skyA.start;
        int e1 = skyA.getEndPoint();
        int s2 = skyB.start;
        int e2 = skyB.getEndPoint();
        SkyLine merged = null;
        if (e1 == s2 || e2 == s1) {
            merged = concatenate(skyA, skyB);
        } else {
            merged = new SkyLine();
            merged.segments = new ArrayList<SkyLine.Segment>();
            if (e1 < s2) {
                merged.start = s1;
                merged.segments.addAll(skyA.segments);
                merged.segments.add(new Segment(0, s2));
                merged.segments.addAll(skyB.segments);
            } else if (e2 < s1) {
                merged.start = s2;
                merged.segments.addAll(skyB.segments);
                merged.segments.add(new Segment(0, s1));
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
        int s1 = skyA.start;
        int e1 = skyA.getEndPoint();
        int s2 = skyB.start;
        int e2 = skyB.getEndPoint();
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
        int start=sky1.start;
        List<Segment>segments=new ArrayList<SkyLine.Segment>();
        Segment lastSeg=null;
        Segment segA=sky1.segments.get(0);
        int indexA=1;
        Segment segB=sky2.segments.get(0);
        int indexB=1;
        
        int height;
        int end;
        while(segA!=null && segB!=null){
            height=Math.max(segA.height, segB.height);
            end=Math.min(segA.end, segB.end);
            if(lastSeg==null){
                lastSeg=new Segment(height, end);
            }else{
                if(lastSeg.height==height){
                    lastSeg.end=end;
                }else{
                    segments.add(lastSeg);
                    lastSeg=new Segment(height,end);
                }
            }
            if(end==segA.end){
                if(indexA<sky1.segments.size()){
                    segA=sky1.segments.get(indexA);
                    indexA++;
                }else{
                    segA=null;
                }
            }
            if(end==segB.end){
                if(indexB<sky2.segments.size()){
                    segB=sky2.segments.get(indexB);
                    indexB++;
                }else{
                    segB=null;
                }
            }
        }
        if(lastSeg!=null){
            segments.add(lastSeg);
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
                linked.segments = new ArrayList<SkyLine.Segment>();
                if (sky1.getLastSegment().height == sky2.getFirstSegment().height) {
                    linked.segments.addAll(sky1.segments.subList(0, sky1.segments.size() - 1));
                } else {
                    linked.segments.addAll(sky1.segments);
                }
                linked.segments.addAll(sky2.segments);
            } else if (sky2.getEndPoint() == sky1.start) {
                linked = concatenate(sky2, sky1);
            }
            return linked;
        }

    }
}
