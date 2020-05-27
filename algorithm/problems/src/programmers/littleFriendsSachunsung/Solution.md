# 프로그래머스 문제 : 리틀 프렌즈 사천성

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/1836)

게임은 2차원 배열에서 진행되는데, 여러 가지 무늬로 구성된 타일이 배치되어 있으며 같은 모양의 타일은 두 개씩 배치되어 있다. 게임의 목적은 배치된 모든 타일을 제거하는 것으로, 같은 모양의 타일을 규칙에 따라 제거하면 된다. 타일을 제거할 수 있는 경우는 다음과 같다.

다음 조건을 만족하는 경로가 있을 때 두 타일을 제거할 수 있다.

- 경로의 양 끝은 제거하려는 두 타일이다.
- 경로는 두 개 이하의 수평/수직 선분으로 구성되어 있고, 이들은 모두 연결되어 있다. (즉, 경로를 한 번 이하로 꺾을 수 있다)
  - 참고: 프렌즈 사천성은 경로가 세 개 이하의 선분으로 구성되어야 한다는 점이 다르다. (즉, 경로를 두 번 이하로 꺾을 수 있다)
- 경로 상에는 다른 타일 또는 장애물이 없어야 한다.



### 입력 형식

입력은 게임판의 크기를 나타내는 `m`과 `n`, 그리고 배치된 타일의 정보를 담은 문자열 배열 `board`로 주어진다. 이 배열의 크기는 `m`이며, 각각의 원소는 `n`글자의 문자열로 구성되어 있다. 입력되는 값의 제한조건은 다음과 같다.

- `1 <= m, n <= 100`

- ```
  board
  ```

  의 원소는 아래 나열된 문자로 구성된 문자열이다. 각 문자의 의미는 다음과 같다.

  - `.`: 빈칸을 나타낸다.
  - `*`: 막힌 칸을 나타낸다.
  - 알파벳 대문자(`A`-`Z`): 타일을 나타낸다. 이 문제에서, 같은 글자로 이루어진 타일은 한 테스트 케이스에 항상 두 개씩만 존재한다.
  - `board`에는 알파벳 대문자가 항상 존재한다. 즉, 타일이 없는 입력은 주어지지 않는다.



### 출력 형식

해가 존재하는 경우 타일을 제거하는 순서대로 한 글자씩 이루어진 문자열을, 그렇지 않은 경우 `IMPOSSIBLE`을 리턴한다. 해가 여러 가지인 경우, 알파벳 순으로 가장 먼저인 문자열을 리턴한다.

```
[예제 입출력]
m	n	board				
3	3	[DBA, C*A, CDB]			return ABCD
2	4	[NRYN, ARYA]			return RYAN
4	4	[.ZI., M.**, MZU., .IU.]	reutrn MUZI
2	2	[AB, BA]			return IMPOSSIBLE
```





## 문제 풀이

알파벳은 26개 밖에 되지 않으며 m,n값도 최대 100 이므로 각 입력값에 대해서 브루트 포스로 접근하기로 했다.

입력되는 board에서 먼저 알파벳 있는 좌표만 따로 정리한 다음에 알파벳있는 좌표만 순회하도록 했으며 순회는 가로->세로, 세로->가로 이렇게 2번 경로를 따라가면서 해당 알파벳 또는 . 이 있는지 아닌지를 체크했다.

처음에 코딩했을 때 좀 복잡하게 해서 해서 통과를 못시켰다가 다음날 다시 싹 지우고 짰더니 통과가 되었다. 



## 코드 구현 [[전체코드]](./Solution.java)

```java
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
```

