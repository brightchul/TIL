package ch20;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println("test");
        showList(naiveSearch("avadakedavraked", "aked"));
    }
    public static ArrayList<Integer> naiveSearch(String H, String N) {
        ArrayList<Integer> ret = new ArrayList<>();
        // 모든 시작 위치를 다 시도해 본다.
        int nLen = N.length(), hLen = H.length();
        for(int begin = 0; begin + nLen <= hLen; ++begin) {
            boolean matched = true;
            for(int i=0; i<nLen; ++i) {
                if(H.charAt(begin + i) != N.charAt(i)) {
                    matched = false;
                    break;
                }
            }
            if(matched) ret.add(begin);
        }
        return ret;
    }
    public static void showList(List list) {
        System.out.println(list);
    }
}
