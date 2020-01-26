# 프로그래머스 문제 : 2 x n 타일링

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/12900)

가로 길이가 2이고 세로의 길이가 1인 직사각형모양의 타일이 있습니다. 이 직사각형 타일을 이용하여 세로의 길이가 2이고 가로의 길이가 n인 바닥을 가득 채우려고 합니다. 타일을 채울 때는 다음과 같이 2가지 방법이 있습니다.

- 타일을 가로로 배치 하는 경우
- 타일을 세로로 배치 하는 경우

직사각형의 가로의 길이 n이 매개변수로 주어질 때, 이 직사각형을 채우는 방법의 수를 return 하는 solution 함수를 완성해주세요.

```
[제한사항]
가로의 길이 n은 60,000이하의 자연수 입니다.
경우의 수가 많아 질 수 있으므로, 경우의 수를 1,000,000,007으로 나눈 나머지를 return해주세요.

[입출력]
4 return 5
```



## 문제 풀이

### 방법 1

문제를 보면 직사각형 타일을 세웠을때와 누웠을때 갯수가 각각 1, 2이다. 따라서 1, 2의 조합으로 접근할 수가 있다.

```
1 => 1
2 => 11, 2
3 => 111, 12, 21
4 => 1111, 112, 121, 211, 22
...
```

이것은 2의 갯수와 조합들의 합으로 볼 수 있다. 2의 개수가 증가하면 1의 개수는 하나 더 감소한다.

```
k = 2의 개수 
결과값 = n C 0 + (n-1) C 1 + ... + (n - k) C k
```
위의 조합의 총합이 곧 답이 된다.

하지만 단순히 `n C 0` ~ `(n-k) C k` 까지의 조합을 다 구해서 더해주려고 하면 내부 연산이 팩토리얼이라 속도가 너무 느리다. 
그래서 `(n-1) C 1` ~ `(n-k) C k`의 값을들 보자면 특징이 있다.

```
7C1 = 7        1
6C2 = 6 5      2 1
5C3 = 5 4 3    3 2 1
4C4 = 4 3 2 1  4 3 2 1
```

하나의 조합을 `a C b`라고 한다면 이전 값에 이전 조합의 a를 나누고, 이전 조합에서 곱했던 값중 가장 작은 값-1, -2인 값을 곱해주고, b를 나눠주면 된다.

```
7C1 = 7
6C2 = 7C1 / 7 * 6 * 5 / 2
5C3 = 6C2 / 6 * 4 * 3 / 3
4C4 = 5C3 / 5 * 2 * 1 / 4
```



### 방법 2

사실 더 쉽게 푸는 방법이 있다. 결과 값의 패턴을 보자.

```
1 => 1
2 => 2
3 => 3
4 => 5
5 => 8
...
```

이것은 바로 피보나치 법칙을 따른다. 따라서 피보나치 수를 구하면 간단히 풀린다.
이것은 n-1과 n-2가 서로 독립적인 경우의 수인데 각각 2와 1을 더하게 되어 둘다 독립적인 경우의 수가 유지되기 때문에 둘을 더해주면 된다고 한다.






## 코드 구현 [[전체코드1]](./Solution2.java)   [[전체코드2]](./Solution3.java) 

### 방법1

제한조건을 보면 60000 이하의 자연수이다. 따라서 상당히 큰값이 들어오고 이것은 자바 long 형보다 값이 크다. 
그래서 BigInteger를 사용했으나 그렇게 해도 효율성 테스트를 통과하지 못했다.

```java
public int solution(int n) {
    int twoCount = n/2;
    int beforeNum = n-1, nextNum = n-2;

    BigInteger temp = BigInteger.valueOf(beforeNum);
    BigInteger result = temp.add(BigInteger.ONE);	// nC0, (n-1)C1 값을 미리 더해준다

    for(int i=2; i<= twoCount; i++) {
        temp = temp.multiply(BigInteger.valueOf(nextNum-- * nextNum--))
                   .divide(BigInteger.valueOf(beforeNum-- * i));
        result = result.add(temp);
    }

    return result.remainder(BigInteger.valueOf(CASE_NUM)).intValue();
}
```



### 방법2

여기서는 long형을 사용할 수 있는데 피보나치 수를 구하되 1,000,000,007보다 넘는다면 그 나머지를 반환하는 것이다. 
곱셈의 분배법칙을 생각해보면 피보나치수를 구한 결과값에 1,000,000,007를 나누는 값과 피보나치 수를 구하기 위한 덧셈로직에 1,000,000,007를 나눈 값이 서로 같다는 것을 알 수 있다. 

```java
public int solution(int n) {
    long result = 1, preNum = 1, temp = 0;
    for(int i=2; i<=n; i++) {
        temp = result;
        result += (preNum % CASE_NUM);
        preNum = temp;
    }
    return (int)(result % CASE_NUM);
}
```