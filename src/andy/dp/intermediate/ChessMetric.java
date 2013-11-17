package andy.dp.intermediate;

import java.util.HashMap;
import java.util.Map;

import andy.util.Log;

public class ChessMetric {
	public static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object a) {
			if (a instanceof Point) {
				Point tmp = (Point) a;
				return x == tmp.x && y == tmp.y;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return toString().hashCode();
		}
		
		@Override
		public String toString(){
			return x+""+y;
		}
	}

	private static void doit(int size, int[] start, int[] end, int numMoves) {
		int startx = start[0];
		int starty = start[1];
		int endx = end[0];
		int endy = end[1];

		Map<Point, Long> setS = new HashMap<Point, Long>();
		setS.put(new Point(startx, starty), 1L);
		Map<Point, Long> setT = new HashMap<Point, Long>();

		for (int i = 0; i < numMoves; i++) {
			java.util.Iterator<Point> iter = setS.keySet().iterator();
			while (iter.hasNext()) {
				Point p = iter.next();
				long pathC = setS.get(p);

				Point[] nexts = new Point[] { 
						new Point(p.x, p.y + 1),
						new Point(p.x, p.y - 1), 
						
						new Point(p.x - 1, p.y - 1),
						new Point(p.x - 1, p.y), 
						new Point(p.x - 1, p.y + 1),
						
						new Point(p.x + 1, p.y - 1),
						new Point(p.x + 1, p.y),
						new Point(p.x + 1, p.y + 1),
						
						new Point(p.x-2,p.y-1),
						new Point(p.x-2,p.y+1),
						new Point(p.x-1,p.y-2),
						new Point(p.x-1,p.y+2),
						new Point(p.x+2,p.y-1),
						new Point(p.x+2,p.y+1),
						new Point(p.x+1,p.y-2),
						new Point(p.x+1,p.y+2)
						
				};
				for (int k = 0; k < nexts.length; k++) {
					int nx = nexts[k].x;
					int ny = nexts[k].y;
					if (nx < size && nx >= 0 && ny < size && ny >= 0) {
						Long e = null;
						if ((e = setT.get(new Point(nx, ny))) != null) {
							setT.put(new Point(nx, ny), pathC + e);
						} else {
							setT.put(new Point(nx, ny), pathC);
						}
					}
				}
			}
			setS.clear();
			Map<Point, Long> tmp = setS;
			setS = setT;
			setT = tmp;
		}
		Long e=setS.get(new Point(endx,endy));
		Log.en("number of paths:"+e);
	}
	
	public static void main(String[]args){
		doit(3,new int[]{0,0},new int[]{1,0},1);
		doit(3,new int[]{0,0},new int[]{1,2},1);
		doit(3,new int[]{0,0},new int[]{2,2},1);
		doit(3,new int[]{0,0},new int[]{0,0},2);
		doit(100,new int[]{0,0},new int[]{0,99},50);
	}
}
