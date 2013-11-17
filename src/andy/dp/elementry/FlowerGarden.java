package andy.dp.elementry;

import andy.util.Log;

public class FlowerGarden {
	public static void doit(int[] height, int bloom[], int wilt[]) {
		int N = height.length;
		// quick sort, decreasing order of height
		quick_sort(height, bloom, wilt, 0, N - 1);

		//
		for (int i = 1; i < N; i++) {
			int heightA = height[i];
			int bloomA = bloom[i];
			int wiltA = wilt[i];
			int k = 0;
			for (k = 0; k < i; k++) {
				if (overlap(bloom[k], wilt[k], bloomA, wiltA)) {
					// switch;
					for (int j = i - 1; j >= k; j--) {
						height[j + 1] = height[j];
						bloom[j + 1] = bloom[j];
						wilt[j + 1] = wilt[j];
					}
					break;
				}
			}
			if (k < i) {
				height[k] = heightA;
				bloom[k] = bloomA;
				wilt[k] = wiltA;
			}
		}

		Log.en("planting order:");
		for (int i = 0; i < N; i++) {
			Log.en("" + height[i]);
		}
	}

	private static boolean overlap(int startA, int endA, int startB, int endB) {
		if (startB > endA || endB < startA) {
			return false;
		} else {
			return true;
		}
	}

	private static void quick_sort(int[] height, int[] bloom, int[] wilt,
			int start, int end) {
		if (start >= end) {
			return;
		}
		if (start + 1 == end) {
			if (height[start] < height[end]) {
				switchLoc(height, start, end);
				switchLoc(bloom, start, end);
				switchLoc(wilt, start, end);
			}
			return;
		}
		int middle = (start + end) / 2;
		int h = height[middle];
		int i = 0;
		int k = 0;
		int equalLoc = start - 1;
		for (i = start, k = end; i <= k;) {
			if (height[i] < h) {
				while (height[k] < h) {
					k--;
				}
				if (k <= i) {
					break;
				}
				switchLoc(height, i, k);
				switchLoc(bloom, i, k);
				switchLoc(wilt, i, k);
				k--;
			} else if (height[i] > h) {
				i++;
			} else if (height[i] == h) {
				equalLoc++;
				switchLoc(height, i, equalLoc);
				switchLoc(bloom, i, equalLoc);
				switchLoc(wilt, i, equalLoc);
			}
		}
		for (int J = start; J <= equalLoc; J++) {
			switchLoc(height, J, i - 1 - (J - start));
			switchLoc(bloom, J, i - 1 - (J - start));
			switchLoc(wilt, J, i - 1 - (J - start));
		}
		quick_sort(height, bloom, wilt, start, start+(i-1-equalLoc));
		quick_sort(height, bloom, wilt, i, end);
	}

	private static void switchLoc(int[] array, int a, int b) {
		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}

	public static void main(String[] args) {
		// doit( new int[]{5,4,3,2,1},
		// new int[]{1,1,1,1,1},
		// new int[]{365,365,365,365,365});
		//
		// doit( new int[]{5,4,3,2,1},
		// new int[]{1,5,10,15,20},
		// new int[]{4,9,14,19,24});
		//
		// doit( new int[]{5,4,3,2,1},
		// new int[]{1,5,10,15,20},
		// new int[]{5,10,15,20,25});
		//
		// doit( new int[]{5,4,3,2,1},
		// new int[]{1,5,10,15,20},
		// new int[]{5,10,14,20,25});
		//
		// doit( new int[]{1,2,3,4,5,6},
		// new int[]{1,3,1,3,1,3},
		// new int[]{2,4,2,4,2,4});

		doit(new int[] { 3, 2, 5, 4 }, new int[] { 1, 2, 11, 10 }, new int[] {
				4, 3, 12, 13 });
	}
}
