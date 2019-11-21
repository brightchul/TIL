# 08 Dynamic Programming

## 8.1 도입

동적 계획법은 프로그래밍 대회 문제에 가장 자주 출현하는 디자인 패러다임 중 하나이다. 동적 계획법은 큰 의미에서 분할 정복과 같은 접근 방식을 의미한다. 하지만 문제를 나누는 방식에서 차이가 난다. 동적 계획법에서는 어떤 부분 문제의 답을 한번만 계산하고 계산결과를 재활용해서 속도를 향상시킬수 있다.

이미 계산한 값을 저장해두는 메모리 장소를 **캐시**(cache)라고 하며 두 번 이상 계산되는 부분문제를 **중복되는 부분문제**(overlapping subproblems)라고 부른다.

가상의 문제를 푼다고 하자. 이때 문제를 분할했을 때 나눠진 각 문제들이 같은 부분 문제에 의존을 하게 되면 계산의 중복 횟수가 분할의 깊이가 깊어질수록 지수적으로 증가하게 된다. 이러한 현상을 흔히 **조합폭발**(combinatorial explosion) 이라고 부른다.

동적 계획법 알고리즘의 가장 유명한 예 중 하나는 이항 계수(binomial coefficient) 계산이다. `C(n, r)`은 n개의 서로 다른 원소 중에서 r개의 원소를 순서없이 골라내는 방법의 수를 나타내며 다음의 점화식이 성립한다.

```
C(n, r) = C(n-1, r-1) + C(n-1, r)
```

```
// 반복문이 없기 때문에 재귀 호출이 몇번 이루어지는지로 수행시간을 파악할 수 있다.
public int bino(int n, int r) {
	// 기저 사례 : n==r(모든 원소를 다 고르는 경우) r=0(없는경우)
	if(r== 0 || n==r) return return 1;
	return bino(n-1, r-1) + bino(n-1, r);
}
```

`bino(4,2)`를 호출하면 `bino(2,0), bino(1,0), bino(1,1)` 이 각각 2번씩 호출된다. `bino(8,4)`를 호출해보면 더 많은 중복 호출을 확인할 수 있다. 함수의 중복 호출은 n가 r이 커짐에 따라 기하급수적으로 증가한다.

```
bino(7,3) : 1
bino(6,2) : 1
bino(5,1) : 1
bino(4,0) : 1
bino(4,1) : 4
bino(3,0) : 4
bino(3,1) : 10
bino(2,0) : 10
bino(2,1) : 20
bino(1,0) : 20
bino(1,1) : 20
bino(5,2) : 3
bino(4,2) : 6
bino(3,2) : 10
bino(2,2) : 10
bino(6,3) : 2
bino(5,3) : 3
bino(4,3) : 4
bino(3,3) : 4
bino(7,4) : 1
bino(6,4) : 1
bino(5,4) : 1
bino(4,4) : 1
총 호출 수 : 139
```

이제 이런 계산 낭비를 피하기 위해 캐시 배열을 만들어서 각 입력에 대한 반환 값을 저장한다. 이렇게 함수의 결과를 저장하는 장소를 만들고 한 번 계산한 값을 저장해 뒀다 재활용하는 최적화 기법을 **메모이제이션(memoization)** 이라고 부른다.

```java
import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static int[][] cache = new int[30][30];

    public static void main(String[] args) {
        for(int[] arr: cache) Arrays.fill(arr, -1);
        bino2(8,4);
    }
    
    public static int bino2(int n, int r) {
        // 기저 사례
        if(r==0 || n==r) return 1;
        // -1이 아니면 저장된 값 반환
        if(cache[n][r] != -1) return cache[n][r];
        // 직접 계산한 뒤 배열에 저장
        return cache[n][r] = bino2(n-1, r-1) + bino2(n-1, r);
    }
}

```

```
bino2(8,4) : 1
bino2(1,0) : 1
bino2(2,0) : 1
bino2(1,1) : 1
bino2(3,0) : 1
bino2(2,1) : 2
bino2(4,0) : 1
bino2(3,1) : 2
bino2(2,2) : 1
bino2(4,1) : 2
bino2(3,2) : 2
bino2(5,1) : 1
bino2(4,2) : 2
bino2(3,3) : 1
bino2(5,2) : 2
bino2(4,3) : 2
bino2(6,2) : 1
bino2(5,3) : 2
bino2(4,4) : 1
bino2(6,3) : 2
bino2(5,4) : 1
bino2(7,3) : 1
bino2(6,4) : 1
bino2(7,4) : 1
총 호출 수 : 33
```

메모이제이션을 사용해서 함수 호출 횟수가 많이 감소한 것을 볼수 있다. 이와 같이 두 번 이상 반복 계산되는 부분 문제들의 답을 미리 저장해서 속도의 향상을 꾀하는 알고리즘 설계 기법을 동적 계획법이라고 한다.



### 메모이제이션을 적용할 수 있는 경우

**참조적 투명성**(referential transparency)는 함수의 반환 값이 그 입력 값만으로 결정되는지에 대한 여부를 뜻한다. 입력이 고정되어 잇을 때 그 결과가 항상 같은 함수들을 **참조적 투명함수**(referential transparent funtion) 라고 부른다. 메모이제이션은 참조적 투명 함수의 경우에만 적용할 수 있다. 즉 입력이 같다면 출력이 항상 같은 함수에 대해서 캐싱이 가능한 것이다.



### 메모이제이션 구현 패턴

동적 계획법은 가장 흔한 문제 유형중 하나라서 메모이제이션은 자주 구현하게 된다. 그렇기 때문에 한가지 패턴을 정해두고 같은 형태로 구현하면 작성, 버그 찾기도 쉬워진다. 

```java
// 예시 함수
// a와 b는 각각 (0, 2500) 구간 안에 있는 정수
// 반환 값은 항상 int형 안에 들어가는 음이 아닌 정수
public int someObscureFunction9int a, int b);
```

1. 항상 기저 사례를 제일 먼저 처리한다. 입력이 범위를 벗어난 경우 등을 기저 사례로 처리하면 유용하다. 

2. 함수의 반환 값이 항상 0이상이라는 점을 이용해 cache[ ]를 모두 -1로 초기화한다. cache[ ]의 해당 위치에 적혀있는 값이 -1이라면 이 값은 계산된 반환값이 아니다.

3. ret가 `cache[a][b]`에 대한 참조형이다. 참조형 ret의 값을 바꾸면 `cache[a][b]` 값도 변하기 때문에 답을 저장할 때 `cache[a][b]` 라고 쓸 필요가 없다. 특히 다차원 배열일 때 유용하다. 귀찮은 것도 해소되지만, 인덱스 순서를 바꿔 쓴다거나 하는 실수 가능성도 없애준다. (C++코드에서는 참조형을 써서 그렇고 자바에서는 그럴수 없다.)

4. memset()를 이용해 `cache[]`를 초기화하는 부분이다. 메모이제이션용 배열을 초기화하는 것은 자주 하는일이기 대문에 다중 for문보다 쉽게 초기화할 수 있는 방법을 알아두면 간편하다. 단 memset()으로 배열을 초기화하는 방법은 굉장히 제한적인 경우에만 쓸 수 있다. (C++만 가능)

```c++
// 메모이제이션 예시 : C++
// 전부 -1로 초기화해 둔다.
int cache[2500][2500];
// a와 b는 각각 (-,2500) 구간 안의 정수
// 반환 값은 항상 int 형 안에 들어가는 음이 아닌 경우
int someObscureFunction(int a, int b) {
    // 1. 기저 사례를 처음에 처리한다.
    if(...) return ...;
    // 3. (a,b)에 대한 답을 구한 적이 있으면 곧장 반환
    int& ret = cache[a][b];
    if(ret != -1) return ret;
    // 여기에서 답을 계산한다.
    ...
    return ret;
}
int main() {
    // 2,4. memset을 이용해서 cache배열을 초기화한다.
	memset(cache, -1, sizeof(cache));
}
```

```java
// 메모이제이션 예시 : 자바
class Main {
    public static int someObscureFunction(int a, int b) {
        // 1. 기저 사례를 처리한다.
        if(...) return;
        // 3. java는 참조형 변수가 없으나 
        // n차원 배열일때 n-1차원 배열 변수로 변경 가능성을 제한할 수 있을 것이다.
        // 하지만 c++의 참조형 변수에 비해서는 덜 직관적이다.
        int[] cacheA = cache[a];
        if(cacheA[b] != -1) return cacheA[b];
        
        ...
        return cacheA[b];
    }
    public static void main(String[] args) {
        // 2. -1로 초기화
        // 4. 자바에는 memset이 없어서 그럴수가 없다..
        int [][] cache = new int[2500][2500];
        for(int[] arr : cache) Arryas.fill(arr, -1);
    }
}
```



### 메모이제이션의 시간 복잡도 분석

메모이제이션 시간복잡도를 계산은 다음식을 이용한다.

```
(존재하는 부분 문제의 수) x (한 부분 문제를 풀 때 필요한 반복문의 수행 횟수)
```

위 식을 bino2에 적용해 본다. 먼저 r의 최대치는 n이니 bino2(n,2)를 계산할 때 만날 수 있는 부분 문제의 수는 최대 O(n^2)이다. 각 부분 문제를 계산할 때 걸리는 시간은 반복문이 없으니 O(1)이다. 따라서 총 시간 복잡도는 `O(n^2) x O(1) = O(n^2)` 이다. 물론 이 식은 수행 시간의 상한을 간단히 계산할 수 있는 방법일 뿐이며, 항상 정확하지는 않다. (자세한 내용은 책 p.214를 참고한다.)



### 예제 : 외발 뛰기 

문제 내용 : [링크]( https://algospot.com/judge/problem/read/JUMPGAME )

문제 풀이 전체코드 : [링크](https://gist.github.com/brightchul/28908030c140bab0b071a6d23895eba1)



#### 재귀 호출에서 시작하기

동적 계획법 알고리즘을 만드는 첫 단계는 해당 문제를 재귀적으로 해결하는 완전 탐색 알고리즘을 만드는 것이다. 맨 왼쪽 윗 칸에서 시작하는 모든 경로를 하나씩 만들어 보면서 마지막 칸에 도달할 수 있는지 검사한다.

```
jump(y,x) : (y,x)에서부터 맨 마지막 칸까지 도달할 수 있는지 여부를 반환한다.
```

```java
private static int[][] arr = new int[100][100];

public static boolean run(int y, int x) {
    // 기저 사례 : 게임판 밖을 벗어난 경우
    if(!isBorder(y, x)) return false;
    // 마지막 칸에 도착한 경우
    if(y == rowLen-1 && x == rowLen-1) return true;

    int one = arr[y][x];
    
    // 오른쪽으로 점프할지, 아래쪽으로 점프할지를 정한다.
    return run(y+one, x) || run(y, x+one);
}
```



#### 메모이제이션 적용하기

재귀호출을 이용해서 하는 완전 탐색이 만드는 경로의 수는 많지만 입력의 갯는 최대 100x100=10000개뿐이다. 비둘기집의 원리에 의해 중복으로 해결되는 부분 문제들이 항상 존재한다는 것을 알 수 있다. 위의 재귀함수는 참조적 투명 함수라서 메모이제이션을 적용해서 중복된 연산을 없앨 수 있다.

아래는 메모이제이션을 적용한 코드이다. 반환값이 boolean -> int로 변경된 점을 참고하자.

```java
public static int run2(int y, int x) {
    // 기저사례
    if(!isBorder(y, x)) return 0;
    if(y == rowLen-1 && x == rowLen-1) return 1;
	// 캐시 사용
    if(cache[y][x] != -1) return cache[y][x];

    int one = arr[y][x];

    // 자바에서는 논리적 연산자 || 는 오로지 boolean값만 받고 boolean값만 반환한다.
    // 따라서 다르게 구현해야 한다. c++에서는 그렇지 않기 때문에 아래처럼 한다.
    // int& ret = cache[y][x];
    // ret = (run2(y+one,x) || run2(y, x+one));
    
    int way1 = run2(y+one, x);
    if(way1 == 1) {
        return (cache[y][x] = 1);
    } else {
        return (cache[y][x] = run2(y, x+one));
    }
}
```



재귀호출을 이용한 완전 탐색과 메모이제이션을 했을 때를 비교해보면 다음과 같다.

```
true
2 5 1 6 1 4 1
6 1 1 2 2 9 3
7 2 3 2 1 3 1
1 1 3 1 7 1 2
4 1 2 3 4 1 2
3 3 1 2 3 4 1
1 5 2 9 4 7 0
run  -> count : 29
run2 -> count : 27

false : 최악입력
1 1 1 1 1 1 1
1 1 1 1 1 1 1
1 1 1 1 1 1 1
1 1 1 1 1 1 1
1 1 1 1 1 1 1
1 1 1 1 1 1 2
1 1 1 1 1 2 0
run  -> count : 5015
run2 -> count : 97
```



#### 다른 해법

그래프로 모델링해보면 아주 간단한 도달 가능성 문제가 된다.



#### 동적 계획법 레시피

1. 주어진 문제를 완전 탐색을 이용해 해결한다.
2. 중복된 부분 문제를 한 번만 계산하도록 메모이제이션을 적용한다.



#### 다른 구현 방법에 관하여

재귀호출을 이용하지 않고도 동적 계획법 알고리즘을 구현할 수 있는데 이것을 **반복적 동적 계획법**이라고 한다. 9.21절에서 반복적 동적 계획법을 구현하는 요령과 장단점을 소개한다.