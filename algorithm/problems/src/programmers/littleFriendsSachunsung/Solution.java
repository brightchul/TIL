package programmers.littleFriendsSachunsung;

import java.util.*;
class Solution {
    char[][] charBoard;
    Map<Character, ArrayList<Element>> map;
    public String solution(int m, int n, String[] board) {
        StringBuilder sb = new StringBuilder();

        // charBoard 초기화
        charBoard = new char[m][];
        for(int i=0; i<m; i++) {
            charBoard[i] = board[i].toCharArray();
        }

        // map : 알파벳 있는 요소만 저장하기 위함
        map = new HashMap<>();
        for(int row=0; row<m; row++) {
            for(int col=0; col<n; col++) {
                char word = charBoard[row][col];
                if(word == '.'|| word == '*') continue;
                if(!map.containsKey(word)) map.put(word, new ArrayList<>());
                map.get(word).add(new Element(row, col));
            }
        }

        // wordSet : 좌표없이 순수 알파벳만 정렬, 체크해서 순서상 먼저있는 알파벳부터 순회하기 위함
        Set<Character> wordSet = new TreeSet<>(map.keySet());
        int count = wordSet.size();

        // 연결이 가능하다면 wordOperate에서 true를 반환한다.
        // 그럴 경우 charBoard에서 '.'으로 교체하고, wordSet에서 지우고, 반환 문자열에 추가한다.
        loop :while(true) {
            Iterator<Character> itr = wordSet.iterator();
            while(itr.hasNext()) {
                char word = itr.next();
                if(wordOperate(word)) {
                    ArrayList<Element> list = map.get(word);
                    charBoard[list.get(0).row][list.get(0).col] = '.';
                    charBoard[list.get(1).row][list.get(1).col] = '.';
                    itr.remove();
                    sb.append(word);
                    continue loop;
                }
            }
            break;
        }

        return sb.length() == count? sb.toString() : "IMPOSSIBLE";
    }
    public boolean wordOperate(char word) {
        Element one = map.get(word).get(0);
        Element two = map.get(word).get(1);

        // 가로 먼저 이동 다음 세로
        int oneRow = one.row;
        int oneCol = one.col;
        int twoRow = two.row;
        int twoCol = two.col;
        boolean flag = true;

        while(flag && oneRow != twoRow) {
            if(oneRow < twoRow) oneRow++;
            else oneRow--;

            if(charBoard[oneRow][oneCol] == word) return true;
            if(charBoard[oneRow][oneCol] != '.') flag = false;
        }
        while(flag && oneCol != twoCol) {
            if(oneCol < twoCol) oneCol++;
            else oneCol--;

            if(charBoard[oneRow][oneCol] == word) return true;
            if(charBoard[oneRow][oneCol] != '.') flag = false;
        }

        // 세로 먼저 이동 다음 가로
        oneRow = one.row;
        oneCol = one.col;
        twoRow = two.row;
        twoCol = two.col;
        flag = true;

        while(flag && oneCol != twoCol) {
            if(oneCol < twoCol) oneCol++;
            else oneCol--;

            if(charBoard[oneRow][oneCol] == word) return true;
            if(charBoard[oneRow][oneCol] != '.') return false;
        }
        while(flag && oneRow != twoRow) {
            if(oneRow < twoRow) oneRow++;
            else oneRow--;

            if(charBoard[oneRow][oneCol] == word) return true;
            if(charBoard[oneRow][oneCol] != '.') return false;
        }
        return true;
    }
}

class Element {
    int row, col;
    Element(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
