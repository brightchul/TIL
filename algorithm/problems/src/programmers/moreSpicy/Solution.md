# 프로그래머스 : 더 맵게

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42626)

매운 것을 좋아하는 Leo는 모든 음식의 스코빌 지수를 K 이상으로 만들고 싶습니다. 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 Leo는 스코빌 지수가 가장 낮은 두 개의 음식을 아래와 같이 특별한 방법으로 섞어 새로운 음식을 만듭니다.

```
섞은 스코빌 지수 = 가장 맵지 않은 스코빌 지수 + (두 번째로 맵지 않은 스코빌 지수 * 2)
```

모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 섞어야 하는 최소 횟수를 return 하도록 solution 함수를 작성해주세요.

```
[제한 사항]
- scoville의 길이는 1 이상 1,000,000 이하입니다.
- K는 0 이상 1,000,000,000 이하입니다.
- scoville의 원소는 각각 0 이상 1,000,000 이하입니다.
- 모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우에는 -1을 return 합니다.

[예시]
[1,2,3,9,10,12] 7 return 2
```





## 문제 풀이

작은 값들 중에 1,2번째 값을 뽑아서 변경해서 넣는 것을 계속 반복하는 문제이다. 다만 여기서 1,2번째 값을 뽑아서 섞은 다음 넣을때 기존의 값들과 비교했을 때 크기가 어느정도 인지, 어느 위치인지 계속 수정해 줘야 한다.

배열을 이용한다면 1,2번째 값들을 뽑으면서 이동이 생기고, 그 이후 섞은 값의 적절한 위치를 찾고, 다시 그 위치에 넣기 위해 그 뒤에 있는 값들을 또 이동해야 한다. 그리고 이 모든 과정은 각각  O(n)이라 할 수 있다. 이것을 모든 값들을 K이상으로 만들어야 하므로 그 횟수가 r이라면 총 O(rn)이라고 볼수 있다. r이 n에 가까울수록 O(n^2)이 된다.

입력이 각각 최대값이 10^6이므로 최악은 10^12이고 이것은 10^8의 10000배 정도이므로 배열을 이용하는 것은 적절하지 못하다고 생각이 들었다.

따라서 힙을 이용했다. 최소 힙을 구현했고 그것을 적용해서 풀었다. 물론 자바의 우선순위 큐를 이용해도 좋다.





## 코드 구현 [[전체코드]](Solution.java)

힙을 이용했기 때문에 그저 힙에 넣었다가 최소값2개를 뺐다가 다시 넣고를 반복하면 된다.

```java
public int solution(int[] scoville, int K) {
    MinHeap heap = new MinHeap(scoville.length);

    for(int one : scoville) heap.add(one);

    int count = 0;
    while(heap.peekMin() < K) {
        if(heap.length() == 1) return -1;
        heap.add(heap.popMin() + heap.popMin() * 2);
        count++;
    }

    return count;
}
```

