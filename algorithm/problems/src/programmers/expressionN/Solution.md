# 프로그래머스 문제 : N으로 표현

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42895)

```
아래와 같이 5와 사칙연산만으로 12를 표현할 수 있습니다.

12 = 5 + 5 + (5 / 5) + (5 / 5)
12 = 55 / 5 + 5 / 5
12 = (55 + 5) / 5

5를 사용한 횟수는 각각 6,5,4 입니다. 그리고 이중 가장 작은 경우는 4입니다.
이처럼 숫자 N과 number가 주어질 때, N과 사칙연산만 사용해서 표현 할 수 있는 방법 중 N 사용횟수의 최솟값을 return 하도록 solution 함수를 작성하세요.

[제한사항]
- N은 1 이상 9 이하입니다.
- number는 1 이상 32,000 이하입니다.
- 수식에는 괄호와 사칙연산만 가능하며 나누기 연산에서 나머지는 무시합니다.
- 최솟값이 8보다 크면 -1을 return 합니다.

[입출력 예]
N = 5, number = 12; return 4
N = 2, number = 11; return 3
```



## 문제 풀이

이 문제를 풀 때 3가지를 고려했다.

1. 괄호의 경우의 수
2. 사칙 연산의 경우의 수
3. N, NN, NNN ... 에 대한 경우의 수





#### 괄호를 고려해 보자

```
// 일단은 NN, NNN.. 를 고려하지 않고 N하나만 생각한다.

N 개수 1 일때
N					p1

N 개수 2 일때
(N N)				p2 = p1 + p1

N 개수 3 일때
(N N) N				p3_1 = p2 + p1
N (N N)				p3_2 = p1 + p2

N 개수 4 일때
(N N) (N N)			p4_1 = p2 + p2
((N N) N) N			p4_2 = p3_1 + p1
(N (N N)) N			p4_3 = p3_2 + p1
N ((N N) N)			p4_4 = p1 + p3_1
N (N (N N))			p4_5 = p1 + p3_1

...
```

이렇게 따져보면 앞의 패턴이 누적되어서 그 다음 패턴들을 적용할 수 있다는 것을 알 수 있다. `N N`  에서 N 사이의 공간은 사칙연산이 들어간다.





#### 연산을 고려해 보자

연산은 사실상 사칙 연산만 고려하면 된다. 곱하기와 나누기는 음수값을 고려해야 할지 고민할 수 있는데 어차피 조합시에 +, -가 있기 때문에 굳이 음수 결과값을 고려하지 않아도 반영이 된다.





#### N, NN, NNN... 를 고려해 보자

N은 1, NN는 2, NNN는 3을 의미한다. 따라서 해당 값의 연산을 하면서 Set에 입력할 때 같이 입력 하면 된다. 즉 다른 것들은 연산을 해서 넣지만 이 값들은 연산없이 바로 입력을 하면 되는  것이다.





## 코드 구현 [[전체코드]](./Solution.java)

위의 3가지 경우를 조합해서 코드를 생성하면 된다.

일단 패턴과 사칙 연산을 각각 조합하고, 그 결과값이 하나의 정수 값일테니 그 값을 중복방지를 위해 Set에 담고 이후에 사용할 때에는 순회하면서 적용하면 된다. 그리고 NN, NNN, ... 이 경우에는 위와 별개로 각각 i번째일때 Set에 입력해준다.

```java
public int solution(int N, int number) {
    initCache();
    int[] arrNS = makeArrNS(N);				// N, NN, NNN... 배열
    int idxInArr = indexOf(arrNS, number);

    if(idxInArr > -1) return idxInArr;
    cache[1].add(N);
    
    for(int i=2; i<=8; i++) {
        cache[i].add(arrNS[i]);				// i번째 Set에 N, NN, NNN, ... 추가
        for(int j=1; j<i; j++) {
            for(int o : cache[j]) {
                for(int p : cache[i-j]) {
                    // i번째 조합에 따른 정수 결과값을 생성
                    // 값이 맞으면 더이상 연산할 필요 없으니 끝낸다.
                    if(calculate(o, p, i, number)) return i;
                }
            }
        }
    }
    return -1;
}

public boolean calculate(int o, int p, int idx, int number) {
    int one  = 0;
    if((one = o + p) == number) return true;
    else cache[idx].add(one);
    if((one = o - p) == number) return true;
    else cache[idx].add(one);
    if((one = o * p) == number) return true;
    else cache[idx].add(one);
    if(p != 0 && (one = o / p) == number) return true;
    else cache[idx].add(one);
    return false;
}

```

