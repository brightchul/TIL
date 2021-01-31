package programmers.triangleSnail;

// https://programmers.co.kr/learn/courses/30/lessons/68645?language=java

// 4 [1,2,9,3,10,8,4,5,6,7]
// 6 [1,2,15,3,16,14,4,17,21,13,5,18,19,20,12,6,7,8,9,10,11]

class Solution {
    final int DIRECTION_DOWN = 0;
    final int DIRECTION_RIGHT = 1;
    final int DIRECTION_UP = 2;

    public int[] solution(int n) {
        int[][] temp = new int[n + 1][n + 1];
        int[] answer = new int[n * (n + 1) / 2];
        int row = 0;
        int col = 0;
        int direction = 0;
        int value = 0;

        for (int i = n; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                switch (direction) {
                    case DIRECTION_DOWN:
                        row++;
                        break;
                    case DIRECTION_RIGHT:
                        col++;
                        break;
                    case DIRECTION_UP:
                        row--;
                        col--;
                        break;
                    default:
                        throw new RuntimeException("잘못된 방향입니다.");
                }
                temp[row][col] = ++value;
            }
            direction = (direction + 1) % 3;
        }

        int idx = -1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                answer[++idx] = temp[i][j];
            }
        }
        return answer;
    }
}