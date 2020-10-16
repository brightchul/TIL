# 백준 온라인 저지 : 가장 긴 증가하는 부분 수열

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/11053)

수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우에 가장 긴 증가하는 부분 수열은 A = {**10**, **20**, 10, **30**, 20, **50**} 이고, 길이는 4이다.




```
[입력]
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000)이 주어진다.
둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000)

[출력]
첫째 줄에 수열 A의 가장 긴 증가하는 부분 수열의 길이를 출력한다.

[예시]
6
10 20 10 30 20 50

return 
4
```





## 문제풀이

LIS(Longest Increasing Subsequence 최장 증가 수열) 문제이다. 자세한 내용은 [[여기]](https://shoark7.github.io/programming/algorithm/3-LIS-algorithms) 를 참고하면 잘 나와 있다. 참고한 사이트에서 DP를 이용해서 풀었다. 

예를 들어서 [1,2,3] 까지를 lis한다고 생각해보자. 총 개수를 세어보면 호출이 총 15가 일어난다. 0, 1, 2, 3에 대해서 각각 1, 2, 4, 8로 존재하고 이것은 등비수열의 합인 2^n -1로 나타낼 수 있다. 다시 이것을 O(2^n)으로 나타낼 수 있다.

```
lis(index)

lis(0) - lis(1) - lis(2) - lis(3)
                - lis(3)
       - lis(2) - lis(3)
       - lis(3)
       
lis(1) - lis(2) - lis(3)
       - lis(3)
                
lis(2) - lis(3)

lis(3)
```

전체 탐색으로는 도저히 답이 나올수 없고, 위에서 보다시피 중복된 호출이 많기 때문에 캐시를 이용해는 DP를 활용할 수 있다.



## 코드 구현 [[전체코드]](./Main.java)

```java
private static int lis(int[] arr) {
    arr[0] = Integer.MIN_VALUE;
    N = arr.length;
    cache = new int[N];
    Arrays.fill(cache, -1);

    return find(0, arr);
}

private static int find(int start, int[] arr) {
    if (cache[start] != -1) return cache[start];
    int ret = 0;
    for (int i = start + 1; i < N; i++) {
        if (arr[start] < arr[i]) {
            ret = Math.max(ret, find(i, arr) + 1);
        }
    }
    return cache[start] = ret;
}
```

