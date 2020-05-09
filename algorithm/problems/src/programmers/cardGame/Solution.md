# 프로그래머스 : 카드 게임

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42896)

카드게임이 있다. 게임에 사용하는 각 카드에는 양의 정수 하나가 적혀있고 같은 숫자가 적힌 카드는 여러 장 있을 수 있다.  게임방법은 우선 짝수개의 카드를 무작위로 섞은 뒤 같은 개수의 두 더미로 나누어 하나는 왼쪽에 다른 하나는 오른쪽에 둔다. 각 더미의 제일 위에 있는 카드끼리 서로 비교하며 게임을 한다. 게임 규칙은 다음과 같다. 지금부터 왼쪽 더미의 제일 위 카드를 왼쪽 카드로, 오른쪽 더미의 제일 위 카드를 오른쪽 카드로 부르겠다.

규칙은 다음과 같다.


1. 언제든지 왼쪽 카드만 통에 버릴 수도 있고 왼쪽 카드와 오른쪽 카드를 둘 다 통에 버릴 수도 있다. 이때 얻는 점수는 없다.
2. 오른쪽 카드에 적힌 수가 왼쪽 카드에 적힌 수보다 작은 경우에는 오른쪽 카드만 통에 버릴 수도 있다. 오른쪽 카드만 버리는 경우에는 오른쪽 카드에 적힌 수만큼 점수를 얻는다.
3. (1)과 (2)의 규칙에 따라 게임을 진행하다가 어느 쪽 더미든 남은 카드가 없다면 게임이 끝나며 그때까지 얻은 점수의 합이 최종 점수가 된다.


왼쪽 더미의 카드에 적힌 정수가 담긴 배열 left와 오른쪽 더미의 카드에 적힌 정수가 담긴 배열 right가 매개변수로 주어질 때, 얻을 수 있는 최종 점수의 최대값을 return 하도록 solution 함수를 작성하시오.

```
[제한 사항]
- 한 더미에는 1장 이상 2,000장 이하의 카드가 있다.
- 각 카드에 적힌 정수는 1 이상 2,000 이하이다.

[입출력 예]
left  [3,2,5]
right [2,4,1]
return 7
```



## 문제 풀이

처음에 어떻게 접근해야할지 몰라서 일단 문제에 나오는대로 재귀호출로 완전탐색을 시도해 보았다. 

위에 나오는 규칙 그대로 왼쪽 > 오른쪽 일 대, 왼쪽만 버릴 때, 두 쪽 다 버릴 때를 각 경우로 삼았고 그외에 이미 모든 카드를 소진했을 때를 조건문으로 넣었다. 

스택을 잠깐 생각했지만 완전탐색이므로 스택으로 비웠다가 다시 채우기가 까다로우니 그냥 단순하게 배열과 인덱스로 하는게 더 적절하다고 생각했다. 

```java
// 재귀 호출로 시도 (시간 초과)
public int solution(int[] left, int[] right) {
    int answer = run(left, right, 0, 0);
    return answer;
}

public int run(int[] left, int[] right, int lIdx, int rIdx) {
    if(lIdx == left.length || rIdx == right.length) 
        return 0;
    
    if(left[lIdx] > right[rIdx])
        return right[rIdx] + run(left, right, lIdx, rIdx+1);

    else {
        int leftScore = run(left, right, lIdx+1, rIdx);	// 왼쪽만 버림
        int bothScore = run(left, right, lIdx+1, rIdx+1); // 둘다 버림
        return Math.max(leftScore, bothScore);
    }
}
```

시험삼아 프로그래머스에 돌려보니 47점 정도가 나왔다. 틀린 경우는 전부다 시간 초과 였다. 이제 캐쉬를 통해서 적절히 바꾸면 된다.

인덱스를 찍어보면 해당 length까지 계속해서 (왼쪽만 버림, 둘다 버림) 이 두가지 경우로 계속해서 나뉘면서 가는 것을 볼 수 있다. 이런데 이게 여러 경우에서 중복된 인덱스들의 조합을 확인할 수 있다. 이것을 캐시하면 되는 것이다.

그 이후에는 캐시할 배열을 하나 생성하고 연산하기 전에 캐시에 있으면 바로 결과 반환, 없으면 연산 결과를 캐시에 넣은 다음 결과를 반환하면 된다.  



## 코드 구현

```java
public static int[][] cache;
public int solution(int[] left, int[] right) {
    cache = new int[left.length+1][right.length+1];
    return run(left, right, 0, 0);
}

public int run(int[] left, int[] right, int lIdx, int rIdx) {
    if(lIdx == left.length || rIdx == right.length) 
        return 0;
    
    if(cache[lIdx][rIdx] > 0) 
        return cache[lIdx][rIdx];

    if(left[lIdx] > right[rIdx]) {
        return cache[lIdx][rIdx] = right[rIdx] + run(left, right, lIdx, rIdx+1);
    
    } else {
        int leftScore = run(left, right, lIdx+1, rIdx);	// 왼쪽만 버림
        int bothScore = run(left, right, lIdx+1, rIdx+1);	// 둘다 버림
        return cache[lIdx][rIdx] = Math.max(leftScore, bothScore);
    }
}
```

