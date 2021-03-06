# 프로그래머스 : 풍선 터트리기

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/68646)

###### 문제 설명

일렬로 나열된 n개의 풍선이 있습니다. 모든 풍선에는 서로 다른 숫자가 써져 있습니다. 당신은 다음 과정을 반복하면서 풍선들을 단 1개만 남을 때까지 계속 터트리려고 합니다.

1. 임의의 **인접한** 두 풍선을 고른 뒤, 두 풍선 중 하나를 터트립니다.
2. 터진 풍선으로 인해 풍선들 사이에 빈 공간이 생겼다면, 빈 공간이 없도록 풍선들을 중앙으로 밀착시킵니다.

여기서 조건이 있습니다. 인접한 두 풍선 중에서 **번호가 더 작은 풍선**을 터트리는 행위는 최대 1번만 할 수 있습니다. 즉, 어떤 시점에서 인접한 두 풍선 중 번호가 더 작은 풍선을 터트렸다면, 그 이후에는 인접한 두 풍선을 고른 뒤 번호가 더 큰 풍선만을 터트릴 수 있습니다.

당신은 어떤 풍선이 최후까지 남을 수 있는지 알아보고 싶습니다. 위에 서술된 조건대로 풍선을 터트리다 보면, 어떤 풍선은 최후까지 남을 수도 있지만, 어떤 풍선은 무슨 수를 쓰더라도 마지막까지 남기는 것이 **불가능**할 수도 있습니다.

일렬로 나열된 풍선들의 번호가 담긴 배열 a가 주어집니다. 위에 서술된 규칙대로 풍선들을 1개만 남을 때까지 터트렸을 때 최후까지 남기는 것이 가능한 풍선들의 개수를 return 하도록 solution 함수를 완성해주세요.

```
[제한사항]
a의 길이는 1 이상 1,000,000 이하입니다.
a[i]는 i+1 번째 풍선에 써진 숫자를 의미합니다.
a의 모든 수는 -1,000,000,000 이상 1,000,000,000 이하인 정수입니다.
a의 모든 수는 서로 다릅니다.

[입출력 예]
[9,-1,-5]	                             return 3
[-16,27,65,-2,58,-92,-71,-68,-61,-33]    return 6
```




## 문제 풀이

처음에 재귀로 접근했다가 안풀리고 다른 방법으로 접근했는데도 안풀려서 결국 다른분의 글을 참고 했다. [[참고글]](https://inspirit941.tistory.com/entry/Python-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%A8%B8%EC%8A%A4-%ED%92%8D%EC%84%A0-%ED%84%B0%ED%8A%B8%EB%A6%AC%EA%B8%B0-Level-3) 풀이를 정리해 보자.

일단 큰수를 전부 제거를 한다면 반드시 최소값이 남을 것이다.

맨 좌, 우측의 있는 수는 남길수도 있다. 그 이유는 그 좌, 우측 수를 제외한 다른 수들을 전부 1개만 남을때까지 큰수 제거를 실행하면 되기 때문이다.

```
[-16,27,65,-2,58,-92,-71,-68,-61,-33]

-16........-92..........
...........-92.......-33
여기서 1번은 큰수를 없앨수 있으니 -16, -33은 남겨질수 있는 수이다.
```

맨 끝의 수 입장에서는 자신과 그외의 수로 남겨질수 있다.

각 맨긑의 수가 아닌 중간수 입장에서는 자신의 왼쪽계열에서 1개 숫자, 자신의 오른쪽 계열에서 1개 숫자가 남을 것이다. 이때 가운데 숫자가 양옆의 숫자 모두보다 크다면 남겨질수가 없다. 왜냐하면 1번만 작은 수를 없앨수 있기 때문이다. 

그렇다면 자신의 왼쪽 방향에서부터 누적해서 최소 숫자를 하나, 오른쪽에서 수를 누적해 가며 최소 숫자를 구한다음 그 두 숫자보다 큰지 아닌지를 확인하면 남겨질수 있는지 여부를 확인할 수 있다.

​                   

## 코드 구현

```java

public class Solution {
    public int solution(int[] a) {
        int[] leftArr = new int[a.length];
        int[] rightArr = new int[a.length];
        
        int left = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;

        // 왼쪽에서부터 누적해 가며 최소값
        for (int i = 0; i < a.length; i++) {
            if (left > a[i]) left = a[i];
            leftArr[i] = left;
        }
        
		// 오른쪽에서부터 누적해 가며 최소값
        for (int i = a.length - 1; i > -1; i--) {
            if (right > a[i]) right = a[i];
            rightArr[i] = right;
        }

        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if (leftArr[i] >= a[i] || rightArr[i] >= a[i]) count++;
        }
        return count;
    }
}
```

