# 백준 온라인 저지 : 1로 만들기

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/1463)

정수 X에 사용할 수 있는 연산은 다음과 같이 세 가지 이다.

1. X가 3으로 나누어 떨어지면, 3으로 나눈다.
2. X가 2로 나누어 떨어지면, 2로 나눈다.
3. 1을 뺀다.

정수 N이 주어졌을 때, 위와 같은 연산 세 개를 적절히 사용해서 1을 만들려고 한다. 연산을 사용하는 횟수의 최솟값을 출력하시오.



```
[입력]
첫째 줄에 1보다 크거나 같고, 10^6보다 작거나 같은 정수 N이 주어진다.

[출력]
첫째 줄에 연산을 하는 횟수의 최솟값을 출력한다.

[예시]
2		return 1
10 		return 3
```





## 문제풀이

동적계획법으로 푸는 문제이다. 1부터 차근차근 풀어보면 어떻게 접근해야 하는지 알 수 있다.

```
1	-> 0
2	-> 1
3	-> 1
4	-> 2
5	-> 3
6	-> 2
7	-> 3
8	-> 3
9	-> 2
10	-> 3
```

4부터는 (n-1, n/2, n/3) 한 이전 값중에서 최소값 + 1을 하면 되는 것을 알 수 있다. 

따라서 4부터 올라가면서 캐시 배열의 접근 가능한 값들중 최소값에 +1을 해주면서 채워나가면 해당 입력 숫자까지 쉽게 구할 수 있다.



## 코드 구현 [[전체코드]](./Main.java)

```java
public static int solution(int input) {
    if(arr[input] > -1) return arr[input];
    else {
        int three=INF, two=INF, one=INF;

        if(input % 3 == 0)
            three = arr[input/3] > -1 ? arr[input/3] : solution(input/3);

        if(input % 2 == 0)
            two = arr[input/2] > -1 ? arr[input/2] : solution(input/2);

        one = arr[input-1] > -1 ? arr[input-1] : solution(input-1);

        int value = Math.min(Math.min(three, two), one)+1;
        arr[input] = value;
        return value;
    }
}
```



