# 프로그래머스 : 숫자 게임

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/12987)

xx 회사의 2xN명의 사원들은 N명씩 두 팀으로 나눠 숫자 게임을 하려고 합니다. 두 개의 팀을 각각 A팀과 B팀이라고 하겠습니다. 숫자 게임의 규칙은 다음과 같습니다.

- 먼저 모든 사원이 무작위로 자연수를 하나씩 부여받습니다.
- 각 사원은 딱 한 번씩 경기를 합니다.
- 각 경기당 A팀에서 한 사원이, B팀에서 한 사원이 나와 서로의 수를 공개합니다. 그때 숫자가 큰 쪽이 승리하게 되고, 승리한 사원이 속한 팀은 승점을 1점 얻게 됩니다.
- 만약 숫자가 같다면 누구도 승점을 얻지 않습니다.

전체 사원들은 우선 무작위로 자연수를 하나씩 부여받았습니다. 그다음 A팀은 빠르게 출전순서를 정했고 자신들의 출전 순서를 B팀에게 공개해버렸습니다. B팀은 그것을 보고 자신들의 최종 승점을 가장 높이는 방법으로 팀원들의 출전 순서를 정했습니다. 이때의 B팀이 얻는 승점을 구해주세요.

```
[제한사항]
- A와 B의 길이는 같습니다.
- A와 B의 길이는 1 이상 100,000 이하입니다.
- A와 B의 각 원소는 1 이상 1,000,000,000 이하의 자연수입니다.

[예시]
A	B	result
A=[5,1,3,7]    B=[2,2,6,8]    result=3
A=[2,2,2,2]    B=[1,1,1,1]    result=0
```



​    

## 문제 풀이

순서에 따라서 B팀이 얻는 승점이 달라질 수 있다. 여기서 순서를 출력하는게 아니라 결과값인 승점만 출력하면 되므로 순서보다는 A팀의 개별 값보다 최소로 큰 값을 찾기만 하면 될 것이다.

A팀,B팀의 배열 값들을 정렬한 다음 가장 작은 값부터 서로 비교를 하면서 올라가면 된다. 이것을 위한 2가지 방법이 떠올랐는데, 하나는 **배열을 정렬**한 다음 각각의 인덱스를 하나씩 증가시키는 것이었고 또 하나는 **힙을 이용**하는 것이었다. 둘다 복잡도는 O(nlgn) 이겠지만 **배열을 정렬**한 방법이 테스트 결과는 더 빨랐다. 



## 코드 구현 [[전체코드]](Solution.java)

#### 배열을 정렬하여 이용하는 방법

```java
public int solution(int[] A, int[] B) {
    Arrays.sort(A);
    Arrays.sort(B);

    int idxA = 0, idxB = 0;
    while(idxB < B.length){
        if(A[idxA] < B[idxB++]) idxA++;
    }
    return idxA;
}
```

​    

#### 힙을 이용한 방법

자바에서는 배열을 이용한 힙으로 PriorityQueue가 있으므로 이것을 이용했다.

```java
public int solution2(int[] A, int[] B) {
    PriorityQueue<Integer> pqA = new PriorityQueue<>();
    PriorityQueue<Integer> pqB = new PriorityQueue<>();

    for(int i=0; i<A.length; i++) {
        pqA.add(A[i]);
        pqB.add(B[i]);
    }
    while(pqB.size() > 0) {
        if(pqA.peek() < pqB.peek())
            pqA.poll();
        pqB.poll();
    }
    return A.length - pqA.size();
}
```







