# 프로그래머스 : 네트워크

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/43162)

네트워크란 컴퓨터 상호 간에 정보를 교환할 수 있도록 연결된 형태를 의미합니다. 예를 들어, 컴퓨터 A와 컴퓨터 B가 직접적으로 연결되어있고, 컴퓨터 B와 컴퓨터 C가 직접적으로 연결되어 있을 때 컴퓨터 A와 컴퓨터 C도 간접적으로 연결되어 정보를 교환할 수 있습니다. 따라서 컴퓨터 A, B, C는 모두 같은 네트워크 상에 있다고 할 수 있습니다.

컴퓨터의 개수 n, 연결에 대한 정보가 담긴 2차원 배열 computers가 매개변수로 주어질 때, 네트워크의 개수를 return 하도록 solution 함수를 작성하시오.

```
[제한사항]
- 컴퓨터의 개수 n은 1 이상 200 이하인 자연수입니다.
- 각 컴퓨터는 0부터 n-1인 정수로 표현합니다.
- i번 컴퓨터와 j번 컴퓨터가 연결되어 있으면 computers[i][j]를 1로 표현합니다.
- computer[i][i]는 항상 1입니다.

[입출력 예]
3	[[1, 1, 0], [1, 1, 0], [0, 0, 1]]	return 2
3	[[1, 1, 0], [1, 1, 1], [0, 1, 1]]	return 1
```



## 문제 풀이

깊이 우선 탐색을 이용하면 된다.



## 코드 구현

computers의 배열을 순회하면서 각 인덱스에 있는 배열의 값들을 확인하고 재귀호출을 이용한다.

단, passedList에 있는 것들은 이전의 재귀호출을 이용한 순회에서 이미 거쳤다는 의미이기 때문에 continue를 하고 paasedList에 존재하지 않는 것들에 대해서만 순회를 한다.

```java
public class Solution {
    public int solution(int n, int[][] computers) {
        ArrayList<Integer> passedList = new ArrayList<>();
        int count = 0;
        for(int i=0; i<n; i++) {
            if(passedList.contains(i)) continue;
            recursive(i, computers, passedList);
            count++;
        }
        System.out.println();
        return count;
    }
    public void recursive(int idx, int[][] computers, ArrayList<Integer> passedList) {
        int[] target = computers[idx];
        for(int i=0; i<target.length; i++) {
            if(target[i] == 0) continue;
            if(passedList.contains(i)) continue;
            passedList.add(i);
            recursive(i, computers, passedList);
        }
    }
}
```

