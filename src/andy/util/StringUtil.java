package andy.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = str.length() - 1; i >= 0; i--) {
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}

	/**
	 * given "abcdefg" and "bdg", return "a" "c" "ef" ""
	 * 
	 * @param str
	 * @param subStr
	 * @return
	 */
	public static List<String> exclude(String str, String subStr) {
		List<String> res = new ArrayList<String>();
		if (subStr == null || subStr.length() <= 0) {
			res.add(str);
			return res;
		}
		StringBuffer sb = new StringBuffer();
		int indexA = 0;
		int indexB = 0;
		for (indexA = 0; indexA < str.length(); indexA++) {
			if (indexB < subStr.length()
					&& str.charAt(indexA) == subStr.charAt(indexB)) {
				res.add(sb.toString());
				indexB++;
				sb = new StringBuffer();
			} else
				sb.append(str.charAt(indexA));
		}
		res.add(sb.toString());
		return res;
	}

	public static String merge(List<String> a, List<String> b, String inter) {
		if (a == null || b == null || a.size() != b.size()) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.size(); i++) {
			String ai = a.get(i);
			String bi = b.get(i);
			sb.append(merge(ai, bi));
			if (i < inter.length()) {
				sb.append(inter.charAt(i));
			}
		}
		return sb.toString();
	}

	/**
	 * given "fdaw" and "adkfj" return adfdakfj
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String merge(String a, String b) {
		int ia = 0;
		int ib = 0;
		StringBuffer sb = new StringBuffer();
		while (ia < a.length() && ib < b.length()) {
			if (a.charAt(ia) < b.charAt(ib)) {
				sb.append(a.charAt(ia));
				ia++;
			} else {
				sb.append(b.charAt(ib));
				ib++;
			}
		}
		if (ia < a.length()) {
			sb.append(a.substring(ia));
		}
		if (ib < b.length()) {
			sb.append(b.substring(ib));
		}
		return sb.toString();
	}

	public static boolean isAlphaEary(String a, String b) {
		int i;
		for (i = 0; i < a.length() && i < b.length(); i++) {
			if (a.charAt(i) == b.charAt(i)) {
				continue;
			}
			if (a.charAt(i) < b.charAt(i)) {
				return true;
			} else {
				return false;
			}
		}
		if (i < a.length()) {
			return false;
		} else {
			return true;
		}
	}
}
