package andy.dp.elementry;

import java.util.ArrayList;
import java.util.List;

import andy.util.Log;

public class ZigZag {
	public static void doit(int[] sequence) {
		int N = sequence.length;
		int[] flag = new int[N];
		int[] length = new int[N];
		int[] prevIndex = new int[N];
		flag[0] = 0;
		length[0] = 1;
		prevIndex[0] = -1;
		for (int n = 1; n < N; n++) {
			int maxL = 0;
			int maxIndex = -1;
			for (int j = n - 1; j >= 0; j--) {
				if (flag[j] > 0 && sequence[n] - sequence[j] < 0) {
					if (maxL < length[j] + 1) {
						maxL = length[j] + 1;
						maxIndex = j;
					}
				} else if (flag[j] == 0 && sequence[n] != sequence[j]) {
					if (maxL < length[j] + 1) {
						maxL = length[j] + 1;
						maxIndex = j;
					}
				} else if (flag[j] < 0 && sequence[n] - sequence[j] > 0) {
					if (maxL < length[j] + 1) {
						maxL = length[j] + 1;
						maxIndex = j;
					}
				}
			}
			length[n] = maxL;
			prevIndex[n] = maxIndex;
			flag[n] = sequence[n] - sequence[maxIndex];
		}

		int maxL = 0;
		int index = -1;
		for (int i = 0; i < N; i++) {
			if (maxL < length[i]) {
				maxL = length[i];
				index = i;
			}
		}
		Log.en("======The longest ZigZag sequence, length: " + length[index]);
		while (index >= 0) {
			Log.en("" + sequence[index]);
			index = prevIndex[index];
		}
	}

	public static void doit_more_quickly(int[] sequence) {
		// Initialization
		int N = sequence.length;
		int[] flag = new int[N];
		int[] length = new int[N];
		int[] prevIndex = new int[N];
		flag[0] = 0;
		length[0] = 1;
		prevIndex[0] = -1;

		// ordered list for already found ZigZags
		List<List<Integer>> ordered = new ArrayList<List<Integer>>();
		List<Integer> s1 = new ArrayList<Integer>();
		s1.add(0);
		ordered.add(s1);

		for (int n = 1; n < N; n++) {
			boolean find=false;
			for (int j = ordered.size() - 1; j >= 0; j--) {
				List<Integer> list = ordered.get(j);
				for (Integer index : list) {
					if ((flag[index] > 0 && sequence[n] - sequence[index] < 0)
							|| (flag[index] == 0 && sequence[n] != sequence[index])
							|| (flag[index] < 0 && sequence[n] - sequence[index] > 0)) {
						length[n] = length[index] + 1;
						prevIndex[n] = index;
						flag[n] = sequence[n] - sequence[index];
						find=true;
						break;
					}
				}
				if(find){
					break;
				}
			}
			if(ordered.size()>=length[n]){
				ordered.get(length[n]-1).add(n);
			}else{//append new List
				List<Integer> sB = new ArrayList<Integer>();
				sB.add(n);
				ordered.add(sB);
			}
		}

		int index = ordered.get(ordered.size()-1).get(0); 
		Log.en("======The original, length: " + length[index]);
		for(int i=sequence.length-1;i>=0;i--){
			//Log.e(""+sequence[i]);
		}
		Log.en("======The longest ZigZag sequence is:");
		while (index >= 0) {
			Log.en("" + sequence[index]);
			index = prevIndex[index];
		}
	}

	public static void main(String[] args) {
		doit(new int[] { 1, 7, 4, 9, 2, 5 });
		doit(new int[] { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 });
		doit(new int[] { 44 });
		doit(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		doit(new int[] { 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5,
				5, 5, 1000, 32, 32 });
		doit(new int[] { 374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 600,
				947, 978, 46, 100, 953, 670, 862, 568, 188, 67, 669, 810, 704,
				52, 861, 49, 640, 370, 908, 477, 245, 413, 109, 659, 401, 483,
				308, 609, 120, 249, 22, 176, 279, 23, 22, 617, 462, 459, 244 });
		
		doit_more_quickly(new int[] { 1, 7, 4, 9, 2, 5 });
		doit_more_quickly(new int[] { 1, 17, 5, 10, 13, 15, 10, 5, 16, 8 });
		doit_more_quickly(new int[] { 44 });
		doit_more_quickly(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		doit_more_quickly(new int[] { 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5,
				5, 5, 1000, 32, 32 });
		doit_more_quickly(new int[] { 374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 600,
				947, 978, 46, 100, 953, 670, 862, 568, 188, 67, 669, 810, 704,
				52, 861, 49, 640, 370, 908, 477, 245, 413, 109, 659, 401, 483,
				308, 609, 120, 249, 22, 176, 279, 23, 22, 617, 462, 459, 244 });
	}
}
