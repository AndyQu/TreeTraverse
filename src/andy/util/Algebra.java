package andy.util;

public class Algebra {
	public static double getPointY(double angle, double length, double base){
        return angle*length+base;
    }
	
	public static class Point{
		public double x;
		public double y;
		public Point(double x1,double y1){
			x=x1;
			y=y1;
		}
		public Point(){}
	}
	
	public static Point getCrossPoint(Point a1, Point a2, Point b1, Point b2){
		double c1=getAngle(a1,a2);
		double c2=getAngle(b1,b2);
		if(c1==c2){
			return null;
		}
		double d1=a1.y-c1*a1.x;
		double d2=a2.y-c2*a2.x;
		Point res=new Point();
		res.y=(d2-d1)/(c1-c2);
		res.x=c1*res.y+d1;
		return res;
	}
	
	public static double getAngle(Point a1, Point a2){
		return (a2.y-a1.y)/(a2.x-a1.x);
	}
}
