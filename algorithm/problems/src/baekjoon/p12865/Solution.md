# 백준 온라인 저지 : 평범한 배낭

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/12865)

준서가 여행에 필요하다고 생각하는 N개의 물건이 있다. 각 물건은 무게 W와 가치 V를 가지는데, 해당 물건을 배낭에 넣어서 가면 준서가 V만큼 즐길 수 있다. 아직 행군을 해본 적이 없는 준서는 최대 K무게까지의 배낭만 들고 다닐 수 있다. 준서가 최대한 즐거운 여행을 하기 위해 배낭에 넣을 수 있는 물건들의 가치의 최댓값을 알려주자.




```
[입력]
첫 줄에 물품의 수 N(1 ≤ N ≤ 100)과 준서가 버틸 수 있는 무게 K(1 ≤ K ≤ 100,000)가 
주어진다. 두 번째 줄부터 N개의 줄에 거쳐 각 물건의 무게 W(1 ≤ W ≤ 100,000)와 
해당 물건의 가치 V(0 ≤ V ≤ 1,000)가 주어진다.

입력으로 주어지는 모든 수는 정수이다.


[출력]
한 줄에 배낭에 넣을 수 있는 물건들의 가치합의 최댓값을 출력한다.


[예시]
4 7
6 13
4 8
3 6
5 12

return
14


14 100000
61803 5
62863 0
41632 3
12794 2
71324 8
55358 2
34870 8
41590 7
17928 0
24218 3
18426 0
65130 2
16478 2
93173 9

return 17
```





## 문제풀이

> KnapSack 이라는 유명한 문제 타입이라고 한다. 관련 내용은 검색을 하면 내용은 잘 나와있다.

이 문제는 DP를 이용해서 풀면 된다. 문제를 풀려고 시도하면서 몇가지 걸리는 점이 있었다.

1. 자기 자신과 겹쳐질 수 있다.

2. 동일 무게의 서로 다른 값이 있다면 어떻게 하는가?

3. 가방에 누적으로 들어갈 때 남는 공간에 적용되는지에 대한 반영은 어떻가 하는가?

이것들을 해결하기 위해서는 각각 무게, 물건 갯수에 따라서 배열을 만들면 된다. 즉 가방 무게 제한이 100이고 물품이 10개라고 했을 때 `valueArr = [물품개수][무게제한]` 이런식으로 만들면 된다. 이렇게 하면  **2. 동일 무게 문제는 패스**가 된다. 왜냐하면 각각의 아이템에 대해서 전부다 순회를 하기 때문이다.

그리고 각 무게에 대해서 다시 가방의 무게 제한을 1부터 순회를 하면서 각각의 무게 제한일 때 최대 가치인 조합을 누적해 간다.

```
int a = (이전 아이템까지의 누적한 값중 동일한 무게제한일 때 최댓값)
int b = (현재 아이템 가지) + (이전 아이템까지의 누적 값중 현재 아이템 무게를 제외한 가방 무게에서 최댓값)
Math.max(a, b)
```

이전 아이템의 누적 값을 하기 때문에 **1. 자기 자신과 겹쳐지지 않는다.** 또한 가방무게에서 자기 무게를 제외한 무게를 기준으로 찾기 때문에 **3. 남는 공간에 적용 문제는 해결**된다.

위에 장황하게 써놨지만, 결국에는 자신을 담는 것과 담지 않는 것 2가지로 나뉘게 되는데 **자신을 담지 않았을 때 최댓값**과 **자신을 담았을 때의 최댓값** 2가지를 구해서 그 중 큰 것을 반영하는 것이다.

​      

## 코드 구현 [[전체코드]](./Main.java)

배열 크기를 각각 +1을 하는 이유는 편이성 떄문이다. 

0번째부터 시작하게 되면 이전 값에 대해서 IndexOutOfBounds가 생길수 있게 되어서 그것에 대한 체크 로직을 더 넣어줘야 한다. 하지만 1부터 시작하면 0번째 인덱스의 값은 0으로 하고 그냥 로직을 전개해나가면 된다.

```java
public static int solution(int itemLen, int weightLimit, Item[] itemArr) {
    int[][] valueArr = new int[itemLen + 1][weightLimit + 1];

    int idx = 0;
    while (++idx <= itemLen) {
        int weight = itemArr[idx - 1].weight;
        int value = itemArr[idx - 1].value;

        for (int bagWeight = 1; bagWeight <= weightLimit; bagWeight++) {
            if (weight > bagWeight) {
                valueArr[idx][bagWeight] = valueArr[idx - 1][bagWeight];
            } else {
                valueArr[idx][bagWeight] = 
                    Math.max(
                        valueArr[idx - 1][bagWeight], // 물건을 안 넣었을 때
                        value + valueArr[idx - 1][bagWeight - weight]);	// 물건을 넣었을 때
            }
        }
    }
    return valueArr[itemLen][weightLimit];
}
```
