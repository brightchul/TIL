package programmers.poppingBalloons;

// https://programmers.co.kr/learn/courses/30/lessons/68646

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

// [9,-1,-5]	3
// [-16,27,65,-2,58,-92,-71,-68,-61,-33] 6
class Solution {
    private final HashSet<Integer> mySet = new HashSet<>();
    private final HashMap<String, Integer> myMap = new HashMap<>();

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.solution(new int[]{9, -1, -5}));  // 3
        System.out.println(s.solution(new int[]{-16,27,65,-2,58,-92,-71,-68,-61,-33})); // 6
    }

    public int solution(int[] a) {
        mySet.clear();
        myMap.clear();
        recursive(a, false);
        System.out.println(mySet.toString());
        System.out.println(myMap.size());
        System.out.println(myMap.toString());
        return mySet.size();
    }

    public void recursive(int[] arr, boolean isUse) {
        String key = Arrays.toString(arr) + isUse;

        myMap.put(key, myMap.containsKey(key) ? myMap.get(key) + 1 : 1);

        if (arr.length == 1) {
            mySet.add(arr[0]);
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int v = Math.max(arr[i], arr[i + 1]);
            int[] newArr = new int[arr.length - 1];

            for (int j = 0, idx = 0; j < arr.length; j++) {
                if (i == j) {
                    newArr[idx++] = v;
                } else if ((i + 1) == j) {
                    continue;
                } else {
                    newArr[idx++] = arr[j];
                }
            }

            recursive(newArr, isUse);

            if (isUse == false) {
                int v1 = Math.min(arr[i], arr[i + 1]);
                int[] newArr1 = new int[arr.length - 1];

                for (int j = 0, idx = 0; j < arr.length; j++) {
                    if (i == j) {
                        newArr1[idx++] = v1;
                    } else if ((i + 1) == j) {
                        continue;
                    } else {
                        newArr1[idx++] = arr[j];
                    }
                }

                recursive(newArr1, true);
            }
        }
    }
}