package programmers.phoneNumberList;

import java.util.Arrays;
import java.util.Comparator;

/*
코딩테스트 연습 > 해시 > 전화번호 목록

https://programmers.co.kr/learn/courses/30/lessons/42577
[119, 97674223, 1195524421]	false
[123,456,789]	true
[12,123,1235,567,88]	false
 */

public class Main {
    public static Comparator<String> StringComparator = new Comparator<String>() {
        @Override
        public int compare(String s, String t1) {
            if(s.length() != t1.length()) return s.length() - t1.length();
            else return s.compareTo(t1);
        }
    };
    public static void main(String[] args) {
        String[] case1 = new String[]{"119", "97674223", "1195524421"}; // false
        String[] case2 = new String[]{"123", "456", "789"}; // true
        String[] case3 = new String[]{"12", "123", "1235", "567", "88"}; // false

        System.out.println(check(new String[]{"1"}));
        System.out.println(check(case1));
        System.out.println(check(case2));
        System.out.println(check(case3));

    }
    public static boolean check(String[] arr) {
        Arrays.sort(arr, StringComparator);
        int last = arr.length-2;
        for(int i=0; i<= last; i++) {
            for(int j=i+1; j<arr.length; j++) {
                if(arr[j].indexOf(arr[i]) == 0) return false;
            }
        }
        return true;
    }
}


