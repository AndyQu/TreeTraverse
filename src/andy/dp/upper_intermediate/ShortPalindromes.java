package andy.dp.upper_intermediate;

import java.util.List;

import andy.array.LCS;
import andy.util.Log;
import andy.util.StringUtil;

public class ShortPalindromes {
	private static String doitCorrect(String base){
		int N=base.length();
		if(N==1){
			return base;
		}
		String[][] shortest=new String[N][N];
		for(int i=0;i<N;i++){
			shortest[i][i]=base.substring(i, i+1);
		}
		for(int size=2;size<=N;size++){
			for(int start=0;start+size-1<N;start++){
				int end=start+size-1;
				String s1=base.substring(start, start+1)+shortest[start+1][end]+base.substring(start, start+1);
				String s2=base.substring(end, end+1)+shortest[start][end-1]+base.substring(end, end+1);
				String s3=null;
				if(base.charAt(start)==base.charAt(end)){
					if(size==2){
						shortest[start][end]=base.substring(start, end+1);
						continue;
					}else{
						s3=base.substring(start, start+1)+shortest[start+1][end-1]+base.substring(end, end+1);
					}
				}
				int min=Math.min(s1.length(), s3==null?s2.length():Math.min(s2.length(), s3.length()));
				String shor=null;
				if(s1.length()==min){
					shor=s1;
				}
				if(s2.length()==min){
					if(shor==null || StringUtil.isAlphaEary(s2, shor)){
						shor=s2;
					}
				}
				if(s3!=null && s3.length()==min){
					if(shor==null || StringUtil.isAlphaEary(s3, shor)){
						shor=s3;
					}
				}
				shortest[start][end]=shor;
			}
		}
		Log.en("Shortest earliest Palindrome:"+shortest[0][N-1]);
		return shortest[0][N-1];
	}
	
	private static String doit(String base){
		int N=base.length();
		int middle=N/2;
		String earlyStr=null;

		if(base.length()==1){
			return base;
		}
		
		//middle
		String	left=base.substring(0,middle);
		String	right=StringUtil.reverse(base.substring(middle+1));
		String	com=LCS.doit(left, right);
		//Log.en("Common String of "+left+" and "+right+" is "+com);
		List<String> left_slices=StringUtil.exclude(left, com);
		//Log.enn("left slice:");
		for(String str:left_slices){
			//Log.enn(str+",");
		}
		//Log.en("");
		List<String> right_slices=StringUtil.exclude(right, com);
		//Log.enn("right slice:");
		for(String str:right_slices){
			//Log.enn(str+",");
		}
		//Log.en("");
		String tmp=StringUtil.merge(left_slices, right_slices,com);
		earlyStr=tmp+base.substring(middle, middle+1)+StringUtil.reverse(tmp);
		//Log.en("merged:"+earlyStr);
		
		for(int step=1;step<=N/2;step++){
			int leftIndex=middle-step;
			//if(N-leftIndex+2<earlyStr.length()){
			//	break;
			//}
			//left not as pivot
			left=base.substring(0, leftIndex+1);
			right=StringUtil.reverse(base.substring(leftIndex+1,N));
			com=LCS.doit(left, right);
			//Log.en("Common String of "+left+" and "+right+" is "+com);
			left_slices=StringUtil.exclude(left, com);
			right_slices=StringUtil.exclude(right, com);
			tmp=StringUtil.merge(left_slices, right_slices,com);
			String ear=tmp+StringUtil.reverse(tmp);
			//Log.en("merged:"+ear);
			if(ear.length()<earlyStr.length()){
				//Log.en("update to "+ear);
				earlyStr=ear;
			}else if(earlyStr.length()==ear.length()){
				if(StringUtil.isAlphaEary(ear, earlyStr)){
					earlyStr=ear;
					//Log.en("update to "+ear);
				}
			}
			//left as pivot
			left=base.substring(0, leftIndex);
			right=StringUtil.reverse(base.substring(leftIndex+1,N));
			com=LCS.doit(left, right);
			//Log.en("Common String of "+left+" and "+right+" is "+com);
			left_slices=StringUtil.exclude(left, com);
			right_slices=StringUtil.exclude(right, com);
			tmp=StringUtil.merge(left_slices, right_slices,com);
			ear=tmp+base.substring(leftIndex, leftIndex+1)+StringUtil.reverse(tmp);
			//Log.en("merged:"+ear);
			if(ear.length()<earlyStr.length()){
				//Log.en("update to "+ear);
				earlyStr=ear;
			}else if(earlyStr.length()==ear.length()){
				if(StringUtil.isAlphaEary(ear, earlyStr)){
					earlyStr=ear;
					//Log.en("update to "+ear);
				}
			}
			
			if(N%2==0){
				continue;
			}
			int rightIndex=middle+step;
			//right not as pivot
			left=base.substring(0, rightIndex);
			right=StringUtil.reverse(base.substring(rightIndex,N));
			com=LCS.doit(left, right);
			//Log.en("Common String of "+left+" and "+right+" is "+com);
			left_slices=StringUtil.exclude(left, com);
			right_slices=StringUtil.exclude(right, com);
			tmp=StringUtil.merge(left_slices, right_slices,com);
			ear=tmp+StringUtil.reverse(tmp);
			//Log.en("merged:"+ear);
			if(ear.length()<earlyStr.length()){
				//Log.en("update to "+ear);
				earlyStr=ear;
			}else if(earlyStr.length()==ear.length()){
				if(StringUtil.isAlphaEary(ear, earlyStr)){
					earlyStr=ear;
					//Log.en("update to "+ear);
				}
			}
			
			//right as pivot
			left=base.substring(0, rightIndex);
			right=StringUtil.reverse(base.substring(rightIndex+1,N));
			com=LCS.doit(left, right);
			//Log.en("Common String of "+left+" and "+right+" is "+com);
			left_slices=StringUtil.exclude(left, com);
			right_slices=StringUtil.exclude(right, com);
			tmp=StringUtil.merge(left_slices, right_slices,com);
			ear=tmp+base.substring(rightIndex, rightIndex+1)+StringUtil.reverse(tmp);
			//Log.en("merged:"+ear);
			if(ear.length()<earlyStr.length()){
				//Log.en("update to "+ear);
				earlyStr=ear;
			}else if(earlyStr.length()==ear.length()){
				if(StringUtil.isAlphaEary(ear, earlyStr)){
					earlyStr=ear;
					//Log.en("update to "+ear);
				}
			}
		}
		Log.en("Shortest earliest Palindrome:"+earlyStr);
		return earlyStr;
	}
	public static void main(String[]args){
//		doit("RACE");
//		doit("TOPCODER");
//		doit("Q");
//		doit("MADAMIMADAM");
		doit("ALRCAGOEUAOEURGCOEUOOIGFA");
		//AFLRCAGIOEOUAOEURGC O CGRUEOAUOEOIGACRLFA
		//AFLRCAGIOEOUAEOCEGR U RGECOEAUOEOIGACRLFA
		//Common String of ALRCAGOEUAOE and AFGIOOUEOCGR is AGOEO
		//merged:AFLRCAGIOOUEUAOCEGRURGECOAUEUOOIGACRLFA
		
		doitCorrect("RACE");
		doitCorrect("TOPCODER");
		doitCorrect("Q");
		doitCorrect("MADAMIMADAM");
		doitCorrect("ALRCAGOEUAOEURGCOEUOOIGFA");
		//correct:  AFLRCAGIOEOUAEOCEGRURGECOEAUOEOIGACRLFA
		//my result:AFLRCAGIOEOUAEOCEGRURGECOEAUOEOIGACRLFA
	}
}
