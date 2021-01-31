package programmers.integerTriangle;

// https://programmers.co.kr/learn/courses/30/lessons/43105?language=java
// [[7], [3, 8], [8, 1, 0], [2, 7, 4, 4], [4, 5, 2, 6, 5]] return 30

class Solution {
    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] inputArr = {{7}, {3, 8}, {8, 1, 0}, {2, 7, 4, 4}, {4, 5, 2, 6, 5}};
        System.out.println(s.solution(inputArr));
    }

    public int solution(int[][] triangle) {
        int rowLen = triangle.length;
        int colLen = triangle[rowLen - 1].length;
        int[][] cache = new int[rowLen][colLen];
        cache[0][0] = triangle[0][0];

        int row = -1;
        int max = -1;
        for (int[] arr : triangle) {
            if ((++row) == 0) continue;
            for (int col = 0; col < arr.length; col++) {
                cache[row][col] = Math.max(getValue(row - 1, col - 1, cache), getValue(row - 1, col, cache)) + arr[col];
                max = Math.max(max, cache[row][col]);
            }
        }
        return max;
    }

    public int getValue(int row, int col, int[][] arr) {
        if (row < 0 || col < 0) return 0;
        if (col >= arr[row].length) return 0;
        return arr[row][col];
    }
}