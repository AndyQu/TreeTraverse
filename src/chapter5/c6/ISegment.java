package chapter5.c6;

public interface ISegment {
    public int getStart();
    public int getEnd();
    public void setStart(int s);
    public void setEnd(int e);
    public ISegment subSeg(int s, int e);
    public interface ISegmentTool{
        public Bean merge(ISegment sA, ISegment sB);
        public ISegment createFlat(int start,int end, int value);
        public ISegment concatenate(ISegment sA,ISegment sB);
    }
    public class Bean{
        public ISegment result;
        public ISegment segALeft;
        public ISegment segBLeft;
    }
    
}
