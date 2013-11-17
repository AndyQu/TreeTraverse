package andy.dp.intermediate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import andy.util.Log;


public class AvoidRoads {
	public static class Path{
		int ax;
		int ay;
		int bx;
		int by;
		public Path(int ax,int ay,int bx,int by){
			this.ax=ax;
			this.ay=ay;
			this.bx=bx;
			this.by=by;
		}
	}
	private static long doit(int width,int height,Path[]badStreets){
		if(badStreets==null||badStreets.length==0){
			//return combination(width+width, Math.min(width, height)); 
		}
		long[][]pathNum=new long[width+1][height+1];
		for(int i=0;i<width+1;i++){
			for(int j=0;j<height+1;j++){
				pathNum[i][j]=0;
			}
		}
		pathNum[0][0]=1;
		for(int w=0;w<width+1;w++){
			for(int h=0;h<height+1;h++){
				if(w==0&&h==0){
					continue;
				}
				boolean upOk=!isIn(w,h,w-1,h,badStreets);
				boolean leftOk=!isIn(w,h,w,h-1,badStreets);
				if(h==0){
					if(upOk){
						pathNum[w][h]=pathNum[w-1][h];
					}
				}else if(w==0){
					if(leftOk){
						pathNum[w][h]=pathNum[w][h-1];
					}
				}else{
					if(upOk){
						pathNum[w][h]=pathNum[w-1][h];
					}
					if(leftOk){
						pathNum[w][h]+=pathNum[w][h-1];
					}
				}
			}
		}
		return pathNum[width][height];
	}
	
	public static void main(String[]args){
		long result=0;
		
		Path[]bads=new Path[]{
				new Path(0,0,0,1),
				new Path(6,6,5,6)
		};
		result=doit(6,6,bads);
		Log.en("number of paths:"+result);
		
		bads=new Path[]{};
		result=doit(1,1,bads);
		Log.en("number of paths:"+result);
		
		bads=new Path[]{};
		result=doit(35,31,bads);
		Log.en("number of paths:"+result);
		
		bads=new Path[]{
				new Path(0,0,1,0),
				new Path(1,2,2,2),
				new Path(1,1,2,1)
		};
		result=doit(2,2,bads);
		Log.en("number of paths:"+result);
	}
	
	private static boolean isIn(int ax,int ay,int bx,int by,Path[]bads){
		for(int i=0;i<bads.length;i++){
			Path p=bads[i];
			if(ax==p.ax && ay==p.ay && bx==p.bx && by==p.by){
				return true;
			}
			if(ax==p.bx && ay==p.by && bx==p.ax && by==p.ay){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * can't use this function to compute combination, because result may overflow
	 * @param nx
	 * @param mx
	 * @return
	 */
	private static long combination(int nx,int mx){
		long n=nx;
		long m=mx;
		long res=1;
		Set<Integer>set=new HashSet<Integer>();
		for(int i=2;i<=m;i++){
			set.add(i);
		}
		for(int i=0;i<m;i++){
			res=res*(n-i);
			Iterator<Integer>iter=set.iterator();
			while(iter.hasNext()){
				int x=iter.next();
				if(res%x==0){
					res=res/x;
					iter.remove();
				}
			}
		}
		return res;
	}
}
