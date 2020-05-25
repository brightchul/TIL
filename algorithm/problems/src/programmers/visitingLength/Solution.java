package programmers.visitingLength;

import java.util.HashMap;
import java.util.Map;

class Solution {
    int curCol, curRow;
    Map<Character, int[]> dirMap = new HashMap<>();
    int[][][] countArr;

    Solution() {
        char[] dirChar = {'U', 'D', 'R', 'L'};
        int[][] dirAmountArr = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for(int i=0; i<4; i++)
            dirMap.put(dirChar[i], dirAmountArr[i]);
    }
    public int solution(String dirs) {
        countArr = new int[11][11][4];
        curRow = curCol = 0;

        for(char dir : dirs.toCharArray()) {
            int[] amountArr = dirMap.get(dir);
            if(isOut(curRow + amountArr[0], curCol + amountArr[1]))
                continue;

            addToCountArr(dir, amountArr);
            curRow += amountArr[0];
            curCol += amountArr[1];
        }
        return getCountArr();
    }
    public boolean isOut(int row, int col) {
        if(row < -5 || row > 5) return true;
        if(col < -5 || col > 5) return true;
        return false;
    }
    public void addToCountArr(char dir, int[] amountArr) {
        int curRow = this.curRow + 5;
        int curCol = this.curCol + 5;

        switch(dir) {
            case 'U':   // 0
                countArr[curRow][curCol][0]++;
                countArr[curRow+amountArr[0]][curCol+amountArr[1]][1]++;
                break;
            case 'D':   // 1
                countArr[curRow][curCol][1]++;
                countArr[curRow+amountArr[0]][curCol+amountArr[1]][0]++;
                break;
            case 'R':   // 2
                countArr[curRow][curCol][2]++;
                countArr[curRow+amountArr[0]][curCol+amountArr[1]][3]++;
                break;
            case 'L':   // 3
                countArr[curRow][curCol][3]++;
                countArr[curRow+amountArr[0]][curCol+amountArr[1]][2]++;
                break;
            default : throw new RuntimeException("ERROR");
        }
    }
    public int getCountArr() {
        int count = 0;
        for(int[][] colArr : countArr) {
            for(int[] dirCheckArr : colArr) {
                for(int dirCheck : dirCheckArr) {
                    if(dirCheck > 0) {
                        count++;
                    }
                }
            }
        }
        return count/2;
    }
}
