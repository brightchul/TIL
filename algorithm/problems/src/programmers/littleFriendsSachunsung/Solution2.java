package programmers.littleFriendsSachunsung;
import java.util.*;

public class Solution2 {

    StringBuilder result;
    public String solution(int m, int n, String[] board) {
        result = new StringBuilder();
        char[][] charBoard = new char[m][];
        for(int i=0; i<m; i++)
            charBoard[i] = board[i].toCharArray();

        // 있는 char들을 순서대로 set에서 뽑아낸다.
        TreeSet<Character> set = new TreeSet<>();
        for(String row : board) {
            for(char one : row.toCharArray()) {
                if(one == '*' || one == '.') continue;
                set.add(one);
            }
        }
        int totalSize = set.size();

        // 순서별로 좌표를 넣어준다. 반드시 2개일테지만, 그중 하나만 서도 충분하다.
        Map<Character, List<Integer[]>> map = new HashMap<>();
        for(int row = 0; row < m; row++) {
            for(int col = 0; col < n; col++) {
                char one = charBoard[row][col];
                if(one == '*' || one == '.') continue;
                if(!map.containsKey(one)) map.put(one, new ArrayList<>());
                map.get(one).add(new Integer[]{row, col});
            }
        }

        StraightLoop : while(true) {
            Iterator<Character> itr = set.iterator();
            while(itr.hasNext()) {
                char one = itr.next();
                if(oneTargetOperate(one, charBoard, map)) {
                    result.append(one);
                    itr.remove();
                    continue StraightLoop;
                }
            }
            break;
        }

        if(result.length() != totalSize) return "IMPOSSIBLE";
        return result.toString();
    }
    public boolean oneTargetOperate(char target,char[][] charBoard, Map<Character, List<Integer[]>> map) {
        Integer[] point1 = map.get(target).get(0);
        Integer[] point2 = map.get(target).get(1);

        // 직선으로 있는 경우
        if(point1[0] - point2[0] == 0) {
            // row가 같은 선상이다.
            if(Math.abs(point1[1] - point2[1]) > 1) {
                int row = point1[0];
                int smallCol = Math.min(point1[1], point2[1]);
                int bigCol = Math.max(point1[1], point2[1]);
                for(int col = smallCol+1; col < bigCol; col++) {
                    if(charBoard[row][col] != '.') return false;
                }
            }
            // 그 구간 사이가 전부 . 이라면 둘은 연결되어 있다고 보면 된다.
            charBoard[point1[0]][point1[1]] = '.';
            charBoard[point2[0]][point2[1]] = '.';
            return true;
        }
        else if(point1[1] - point2[1] == 0) {
            // col이 같은 선상이다.
            if(Math.abs(point1[0] - point2[0]) > 1) {
                int col = point1[0];
                int smallRow = Math.min(point1[0], point2[0]);
                int bigRow = Math.max(point1[0], point2[0]);
                for(int row = smallRow+1; row < bigRow; row++) {
                    if(charBoard[row][col] != '.') return false;
                }
            }
            charBoard[point1[0]][point1[1]] = '.';
            charBoard[point2[0]][point2[1]] = '.';
            return true;
        }
        // 직각 으로 있는 경우
        else {
            int amountRow = point2[0] - point1[0];
            int amountCol = point2[1] - point1[1];
            int changeRow = amountRow / Math.abs(amountRow);
            int changeCol= amountCol / Math.abs(amountCol);
            // 세로로 먼저 가고 가로로 이동한다.
            {
                boolean isConnect = true;
                int row = point1[0], col = point1[1];
                while(isConnect) {
                    row += changeRow;
                    if(charBoard[row][col] != '.') {
                        isConnect = false;
                    }
                    if(row == point2[0]) break;
                }
                while(isConnect) {
                    if(col+changeCol == point2[1]) break;
                    col += changeCol;
                    if(charBoard[row][col] != '.') {
                        isConnect = false;
                        break;
                    }
                }
                if(isConnect) {
                    charBoard[point1[0]][point1[1]] = '.';
                    charBoard[point2[0]][point2[1]] = '.';
                    return true;
                }
            }
            // 가로로 이동하고 세로로 이동한다.
            {
                boolean isConnect = true;
                int row = point1[0], col = point1[1];
                while(true) {
                    col += changeCol;
                    if(charBoard[row][col] != '.') {
                        isConnect = false;
                        break;
                    }
                    if(col == point2[1]) break;
                }
                while(isConnect) {
                    if(row+changeRow == point2[0]) break;
                    row += changeRow;
                    if(charBoard[row][col] != '.') {
                        isConnect = false;
                        break;
                    }
                }
                if(isConnect) {
                    charBoard[point1[0]][point1[1]] = '.';
                    charBoard[point2[0]][point2[1]] = '.';
                    return true;
                }
            }
            return false;
        }
    }
}

