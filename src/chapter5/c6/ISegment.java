package chapter5.c6;

import java.util.List;

public interface ISegment {
    public double getStart();
    public double getEnd();
    public void setStart(double s);
    public void setEnd(double e);
    public ISegment subSeg(double s, double e);
    public interface ISegmentTool{
        public Bean merge(ISegment sA, ISegment sB);
        public ISegment createFlat(double start,double end, double value);
        public ISegment concatenate(ISegment sA,ISegment sB);
    }
    public class Bean{
        public List<ISegment> result;
        public ISegment segALeft;
        public ISegment segBLeft;
    }
    
}
