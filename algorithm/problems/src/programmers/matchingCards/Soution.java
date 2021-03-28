package programmers.matchingCards;

class Solution2 {
    private static final int LEN = 4;
    private static Pair[][] pairsArr;
    private static boolean[] visited;
    private static int lastNum;

    public static void main(String[] args) {
        Solution2 s = new Solution2();
        int r = 1, c = 0;

//
        int[][] board1 = {{1, 0, 0, 3}, {2, 0, 0, 0}, {0, 0, 0, 2}, {3, 0, 1, 0}};
        r = 1;
        c = 0;
        System.out.println(s.solution(board1, r, c));   // 14

        int[][] board2 = {{3, 0, 0, 2}, {0, 0, 1, 0}, {0, 1, 0, 0}, {2, 0, 0, 3}};
        r = 0;
        c = 1;
        System.out.println(s.solution(board2, r, c));   // 16

        int[][] board3 = {
                {0, 0, 0, 1},
                {0, 0, 0, 0},
                {0, 2, 0, 2},
                {0, 0, 0, 1}
        };
        r = 0;
        c = 3;
        System.out.println(s.solution(board3, r, c));   // 8

    }

    public void init(int[][] board) {
        lastNum = -1;
        // Pair[][]를 만든다.
        // boolean[] visited를 만든다.
        pairsArr = new Pair[9][2];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                int value = board[row][col];
                lastNum = Math.max(lastNum, value);

                if (value > 0) {
                    Pair onePair = new Pair(row, col, value);
                    int pairsArrSubIdx = pairsArr[value][0] == null ? 0 : 1;
                    pairsArr[value][pairsArrSubIdx] = onePair;
                }
            }
        }
        visited = new boolean[lastNum + 1];
    }

    // 1. 일단 전체 순회하는 것을 만든다.
    public int solution(int[][] board, int r, int c) {
        Pair start = new Pair(r, c, 0);

        init(board);

        int result = run(start);
        return result;
    }

    public int run(Pair start) {
        int tempCost = Integer.MAX_VALUE;
        String path = start.toString();
        for (int i = 1; i <= lastNum; i++) {
            tempCost = getMin(
                    tempCost,
                    run(start, pairsArr[i][0], "", 0),
                    run(start, pairsArr[i][1], path, 0)
            );
        }
        return tempCost + lastNum * 2;
    }

    public int run(Pair start, Pair end, String path, int accumCost) {
        int curNum = end.num;

        Pair newStart = pairsArr[curNum][1].equals(end) ? pairsArr[curNum][0] : pairsArr[curNum][1];
        int localCost = start.getCost(end) + end.getCost(newStart);

        path += "\n" + end.toString() + " - " + newStart.toString();
        accumCost += localCost;

        visited[curNum] = true;
        int tempCost = Integer.MAX_VALUE;

        for (int i = 1; i <= lastNum; i++) {
            if (visited[i]) continue;
            tempCost = getMin(
                    tempCost,
                    run(newStart, pairsArr[i][0], path, accumCost),
                    run(newStart, pairsArr[i][1], path, accumCost)
            );
        }
        visited[curNum] = false;

        if (tempCost == Integer.MAX_VALUE) {
//            System.out.println(path);
//            System.out.println(accumCost);
            return localCost;
        }
        return localCost + tempCost;
    }

    public int getMin(int... args) {
        int result = args[0];
        for (int arg : args) {
            result = Math.min(result, arg);
        }
        return result;
    }
}

class Pair {
    int row;
    int col;
    int num;

    Pair(int row, int col, int num) {
        this.num = num;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "row=" + row +
                ", col=" + col +
                ", num=" + num +
                '}';
    }

    public boolean equalNum(Pair other) {
        return this.num == other.num;
    }

    public boolean equals(Pair other) {
        return this.num == other.num && this.row == other.row && this.col == other.col;
    }

    public int getCost(Pair other) {
        int rowCost = Math.min(Math.abs(this.row - other.row), 1);
        int colCost = Math.min(Math.abs(this.col - other.col), 1);

        return rowCost + colCost;
    }
}