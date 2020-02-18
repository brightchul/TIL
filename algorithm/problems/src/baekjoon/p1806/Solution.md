# 백준 온라인 저지 : 부분합

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/1806)

10,000 이하의 자연수로 이루어진 길이 N짜리 수열이 주어진다. 이 수열에서 연속된 수들의 부분합 중에 그 합이 S 이상이 되는 것 중, 가장 짧은 것의 길이를 구하는 프로그램을 작성하시오.

```
[입력]
첫째 줄에 N (10 ≤ N < 100,000)과 S (0 < S ≤ 100,000,000)가 주어진다. 
둘째 줄에는 수열이 주어진다. 수열의 각 원소는 공백으로 구분되어져 있으며, 10,000이하의 자연수이다.


[출력]
첫째 줄에 구하고자 하는 최소의 길이를 출력한다. 
만일 그러한 합을 만드는 것이 불가능하다면 0을 출력하면 된다.


[예시]
10 15
5 1 3 5 10 7 4 9 2 8

return 2
```





## 문제풀이

이 문제는 다음과 같이 접근할 수 있다.

1. 부분합을 구한다.
2. 조건이 맞는 부분합일 때 해당 구간을 가져온다.
3. 그 구간중에 최소 길이 구간을 구한다. 



처음에는 각 배열 인덱스로부터 시작해서 캐시 배열을 만들고 누적해서 더하는 식으로 진행을 했다. 하지만 그렇게 할 경우 O(N^2)이 되기 때문에 입력값 10^5일 경우 10^10이 되므로 적절하지 못했고 역시나 테스트에 통과하지 못했다. 그래서 찾아본 결과 투포인트 알고리즘 이 있어 이것으로 해결했다.



[**[투포인터 알고리즘]**](https://www.quora.com/q/kfhwdajorrdsqlrs/The-Two-Pointer-Algorithm) : 배열과 같은 연속된 자료구조에서 2개의 포인터를 활용한 문제 해결 방법이다.

- 2개의 포인터 또는 인덱스를 가지고 이동하는데 각각 startIdx, endIdx라고 하겠다.  

- `endIdx - startIdx`의 값이 구간 값이 되며 endIdx가 증가할때마다 배열 값을 더해서 누적합을 만든다. 
- 만들어진 누적합이 조건값 S 이상인지 확인한다.
- true면 해당 구간 값을 기존 구간 값과 비교해서 적용하고 endIdx+1한다.
- false면 startIdx의 값을 누적합에서 빼주고 startIdx +1 한다.
- 끝까지 순회가 되었으면 종료한다.




## 코드 구현 [[전체 코드]](./Main.java)

처음에 O(N^2)으로 구현한 코드

```java
public static int run2(int[] arr, int limit) {
    int ret = Integer.MAX_VALUE;
    int len = arr.length;
    int[] cache = new int[len];

    for(int i=0; i<len; i++) {
        int one = arr[i];
        for(int idx = 0; idx <= i; idx++) {
            cache[idx] += one;
            if(cache[idx] >= limit) {
                ret = Math.min(ret, i - idx + 1);
            }
        }
    }
    return ret == Integer.MAX_VALUE ? 0 : ret;
}
```



투포인터 알고리즘으로 구현한 코드

```java
public static int run(int[] arr, int limit) {
    int startIdx = 0, endIdx = 0, partialSum = 0, len = arr.length;
    int ret = Integer.MAX_VALUE;

    while(endIdx <= len) {
        if(partialSum < limit) {
            if(endIdx == len) break;
            partialSum += arr[endIdx++];
        } else {
            ret = Math.min(ret, endIdx- startIdx);
            partialSum -= arr[startIdx++];
        }
    }

    return ret == Integer.MAX_VALUE ? 0 : ret;
}
```

