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



## 8.2 문제 : 와일드카드

문제내용 : [링크]( https://algospot.com/judge/problem/read/WILDCARD )



## 8.3 풀이 : 와일드카드

### \*를 풀어보자

이 문제에서 \*가 몇 글자에 대응되는지 알 수 없다. 이럴 때 가장 쉬운 방법은 완전 탐색이다. 

먼저 \*에 따라서 패턴들을 나눠보자. 예를 들어 `t*l?*o*r?ng*s`는 `{t*, l?*, o*, r?ng*, s}`의 다섯 조각으로 쪼개진다. 만약 첫 번째 조각에 세 글자가 대응된다고 하면, 나머지 문자열을 나머지 패턴 조각들에 대응되는지를 재귀 호출로 파악한다. 물론 쪼개지 않아도 된다.

```c++
// 와일드카드 패턴 w가 문자열 s에 대응되는지 여부를 반환한다.
bool match(const string& w, const string& s) {
	// w[pos]와 s[pos]를 맞춰나간다.
	int pos = 0;
	while(pos < s.size() && pos < w.size() &&
		  (w[pos] == '?' || w[pos] == s[pos]))
		  ++pos;
	...
}
```



**종료하는 경우의 수**

1. `s[pos]`와 `w[pos]`가 대응되지 않는다. : 대응 실패
2. w 끝에 도달했다. : 패턴에 *이 하나도 없는 경우이다. 이 경우에 패턴과 문자열의 길이가 정확히 같아야만 패턴과 문자열이 대응된다.
3. s끝에 도달했다. : 패턴은 남았지만 문자열이 이미 끝난 경우이다. 당연히 대응 실패라고 생각할 수 있지만, 남은 패턴이 전부 *로 구성되어 있다면 사실 두 문자열은 대응될 수 있다. 이 경우를 제외하고는 답은 항상 거짓이다.
4. `w[pos]`가 *인 경우 : *가 몇글자에 대응될지 모르기 때문에, 0 글자부터 남은 문자열의 길이까지를 순회하며 모든 가능성을 검사한다. 이때 w의 pos+1 이후를 패턴 w\`으로 하고, s의 pos+skip 이후를 문자열 s\`로 해서 match(w\`, s\`)로 재귀 호출했을 때 답이 하나라도 참이면 답은 참이 됩니다.



다음은 위의 아이디어를 구현한다. 3번의 경우 남은 패턴이 모두 \*인 경우의 예외를 따로 처리하지 않는다. 이 경우는 4번을 처리할 때 재귀 호출 되기 때문에 별도로 처리할 필요가 없으며 별도의 기저사례가 없다.

```c++
// 와일드카드 패턴 w가 문자열 s에 대응되는지 여부를 반환한다.
bool match(const string& w, const string& s) {
    // w[pos]와 s[pos]를 맞춰나간다.
    int pos = 0;
    while(pos < s.size() && pos < w.size() && 
          (w[pos] == '?' || w[pos] == s[pos]))
        ++pos;
    
    // 더이상 대응할 수 없으면 왜 while문이 끝났는지 확인한다.
    // 2. 패턴 끝에 도달해서 끝난 경우 문자열도 끝났어야 대응됨
    if(pos == w.size())
        return pos == s.size();
    // 4. *를 만나서 끝난 경우 : *에 몇글자를 대응해야 할지 재귀 호출하며 확인
    if(w[pos] == '*')
        for(int skip=0;pos+skip <= s.size(); ++skip)
            if(match(w.substr(pos+1), s.substr(pos+skip)))
                return true;
    // 이 외의 경우에는 모두 대응되지 않는다.
    return false;
}
```



### 중복되는 부분 문제

완전 탐색은 각 \*에 대응하는 글자 수의 모든 조합을 검사하는데 \*가 많을 수록 경우의 수가 늘어난다. 만약 이 경우의 수 중 답이 없다면 그 답을 차기 전까지 종료하지 않을 것이다.

패턴 `******a`와 원문 `aaaaaaaaaab` 같은 경우가 그렇다. 마지막 글자가 각각 a, b이므로 아무리 체크를 하더라도 대응될 수 없다. 하지만 완전 탐색 알고리즘은 경우의 수를 전부 검사한다. 

코드 실행중에 계산에서 중복이 이루어진다면 캐시를 이용해서 프로그램을 향상시킬수 있다. 입력으로 주어지는 w와 s종류는 제한되어 있다. 재귀 호출 시에 항상 w와 s의 글자를 앞에서부터 떼어내기 때문에 w와 s는 항상 입력에 주어진 패턴 W와 파일명 S의 접미사가 된다. 따라서 입력으로 주어질 수 있는 w와 s는 각각 101개밖에 없다. (입력 최대길이가 100) 이때 match가 101 x 101 = 10201번 이상 호출된다면 비둘기집 원리에 따라 중복되는 부분 문제가 생긴다는 것이다.

메모이제이션을 활용해 보자. w는 항상 전체 패턴 W의 접미사이므로 w의 길이가 결정되면 w 또한 결정된다. 이 점을 이용하면 101x101 크기의 배열에 모든 부분 문제의 답을 저장할 수 있다.

```c++
// -1은 답이 계산 안되었다는 것을 의미한다.
// 1은 해당 입력들이 서로 대응됨을 의미
// 0은 해당 입력들이 서로 대응되지 않음을 의미
int cache[101][101];
// 패턴과 문자열
string W, S;
// 와일드카드 패턴 W[w..]가 문자열 S[s..]에 대응되는지 여부를 반환한다.
bool matchMemoized(int w, int s) {
    // cache
    int& ret = cache[w][s];
    if(ret != -1) return ret;
    // W[w] S[s]를 맞춰나간다.
    while(s < S.size() && w < W.size() && 
          (W[w] == '?' || W[w] == S[s])) {
        ++w;
        ++s;
    }
    // 더이상 대응할 수 없으면 왜 wihle문이 끝났는지 확인한다.
    // 2. 패턴 끝에 도달해서 끝난 경우 : 문자열도 끝났어야 참
    if(w == W.size()) return ret = (s == S.size());
    // 4. *를 만나서 끝난 경우 : *에 몇글자를 대응해야할지 재귀 호출하면서 확인한다.
    if(W[w] == '*')
        for(int skip = 0; skip+s <= S.size(); ++skip)
            if(matchMemoized(w+1, s+skip))
                return ret = 1;
    // 3. 이 외의 경우에는 모두 대응되지 않는다.
    return ret = 0;
}
```

패턴과 문자열의 길이가 모두 n이라고 할 때, 부분 문제의 개수는 n^2이다. matchMemoized는 한 번 호출될 때마다 최대 n번의 재귀 호출을 하기 때문에 전체 시간 복잡도는 O(n^2)이다. 



### 다른 분해 방법

코드가 O(n^3) 이 걸리는 이유는 내부에서 첫 \*를 찾고 \*에 몇 글자가 대응되어야 할지 검사하는 반복문이 존재하기 때문이다. 만약 재귀 함수 자체에 반복문이 하나도 없도록 바꾼다면 부분 문제 개수와 같은 시간만을 사용해 문제를 풀 수 있다.

```c++
// 첫번쩨 *를 찾는 반복문
// W[w]와 S[s]를 맞춰나간다.
while(s < S.size() && w < W.size() && (W[w] == '?|| W[w] == S[s])) {
    ++w; ++s;
}
```

위의 코드에서 w와 s를 1씩 증가시키는 대신에 패턴과 문자열의 첫 한글자씩을 떼고 이들이 서로 대응되는지 재귀호출로 확인할 수 있다.

```c++
if(w < W.size() && s < S.size() && (W[w] == '?|| W[w] == S[s]))
    return ret = matchMemoized(w+1, s+1);
```

다음으로 \*몇 글자가 대응되어야 할지를  확인하는 반복문을 재귀 호출로 변경한다. 매 단계에서 \*에 아무 글자로 대응시키지 않을 것인지, 아니면 한 글자를 더 대응시킬 것인가를 결정하면 된다.

```c++
// 4. *를 만나서 끝난 경우 : *에 몇 글자를 대응해야 할지 재귀 호출하면서 확인
if(W[w] == '*') {
    if(matchMemoized(w+1, s)|| (s < S.size() && matchMemoized(w, s+1))
        return ret = 1;
}
```

이렇게 하면 0 글자 대응되는 경우, 한 글자 대응되는 경우 등을 모두 재귀 호출을 통해 확인하고 그 과정을 메모이제이션을 캐싱하게 된다. 그리고 시간복잡도는 O(n^2)가 된다.



### 자바 버전

```java
// 1. s가 끝까지 갔는지?
// 2. w가 끝까지 갔는지?
// 3. ?인지
// 4. w와 s에 위치하는 문자들이 동일한지?
// *가 없다면 W와 S의 길이는 동일 해야하고, ?를 제외하고는 일치해야 하므로 아래와 같다.
// 5. *인지?

private static int[][] cache = new int[101][101];	

public static int matchMemoized(String W, String S, int w, int s) {
    //cache
    if(cache[w][s] != -1) return cache[w][s];
    int sLen = S.length(), wLen = W.length();

    while(s < sLen && w < wLen && (W.charAt(w) == '?' || W.charAt(w) == S.charAt(s))) {
        ++w; ++s;
    }

    if(w == wLen)
        return cache[w][s] = (s == S.length()) ? 1 : 0;

    // * 때문에 cache가 필요하다.
    if(W.charAt(w) == '*')
        for(int skip = 0; skip + s <= S.length(); ++skip) {
            if (matchMemoized(W, S, w + 1, s + skip) == 1)
                return cache[w][s] = 1;
        }

    return cache[w][s] = 0;
}

public static int matchMemoized2(String W, String S, int w, int s) {
    if(cache[w][s] != -1) return cache[w][s];
    int sLen = S.length(), wLen = W.length();

    // w, s를 증가시키는 대신에 패턴과 문자열을 한 글자씩 떼고
    // 서로 대응되는지 재귀호출로 확인가능하다.
    if(s < sLen && w < wLen && (W.charAt(w) == '?' || W.charAt(w) == S.charAt(s))) {
        return cache[w][s] = matchMemoized2(W, S, w+1, s+1);
    }
    if(w == wLen) {
        return cache[w][s] = (s == sLen ? 1 : 0);
    }

    // *에 몇글자가 대응되어야 할지 확인하는 반복문을 재귀 호출로 바꾼다.
    // 아무 글자로 대응시키지 않을 것인지, 아니면 한 글자를 더 대응시킬 것인가를 결정
    if(W.charAt(w) == '*') {
        if(matchMemoized2(W, S,w+1, s) == 1)
            return cache[w][s] = 1;
        else if(s < sLen && matchMemoized2(W, S, w, s+1) == 1)
            return cache[w][s] = 1;
    }
    return cache[w][s] = 0;
}
```



## 8.4 전통적 최적화 문제들

동적 계획법의 가장 일반적인 사용처는 최적화 문제의 해결이다. 최적화 문제란 여러 개의 가능한 답 중 가장 좋은 답(최적해)를 찾아내는 문제를 말한다.

최적화 문제를 동적 계획법으로 푸는 것 또한 완전 탐색으로 시작하지만, 최적화 문제에 특정 성질이 성립할 경우에는 메모이제이션보다 더 효율적으로 동적 계획법을 구현할 수 있다.



### 예제 : 삼각형 위의 최대 경로 

#### 문제 내용 : [링크](  https://algospot.com/judge/problem/read/TRIANGLEPATH  )



### 완전 탐색으로 시작하기

가장 먼저 완전 탐색을 이용해본다. 

```
pathSum(y, x, sum) : 현재 위치가 (y, x)이고, 지금까지 만난 수의 합이 sum 일 때, 이 경로를 맨 아래줄까지 연장해서 얻을 수 잇는 최대 합을 반환한다.
```

다음과 같은 점화식을 얻을 수 있다.

```
path(y, x, sum) = max( path(y+1, x, sum+tri[y][x]), path(y+1, x+1, sum+tri[y][x]) )
```



#### 자바 구현

전체 코드 : [링크](https://gist.github.com/brightchul/aa4b9784de6edd02cf9d752e88e18976)

```java
public static int run1(int y, int x, int sum) {
    if(y == arr.length) return sum;
    sum += arr[y][x];
    return Math.max(run1(y+1, x, sum), run1(y+1, x+1, sum));
}
```



### 무식하게 메모이제이션 적용하기

이 문제에서 가능한 경로의 개수는 삼각현 가로줄이 하나 늘어날 때마다 두 배씩 늘어나기 때문에 경로의 수는 2^(n-1)이 된다. 만약 n의 최대치가 100이라면 계산할 수 없다.

아래는 메모이제이션을 적용한 코드이지만, 메모리를 너무 많이 쓴다. 또한 같은 합이 없는 입력이 들어오면 완전 탐색처럼 동작한다. 

```c++
// 메모이제이션 코드1
// MAX_NUMBER : 한 칸에 들어갈 숫자의 최대치
int n, triangle[100][100];
int cache[100][100][MAX_NUMBER*100+1];
// (y, x) 위치까지 내려오기 전에 만난 숫자들의 합이 sum일때
// 맨 아래줄까지 내려가면서 얻을 수 있는 최대 경로를 반환한다.
int path1(int y, int x, int sum) {
    // 기저 사례 : 맨 아래 줄까지 도달했을 경우
    if(y==n-1)return sum + triangle[y][x];
    // 메모이제이션
    int& ret = cache[y][x][sum];
    if(ret != -1) return ret;
    sum += triangle[y][x];
    return ret = max(path1(y+1, x+1, sum), path1(y+1, x, sum));
}
```



### 입력 걸러내기

위의 알고리즘을 더 빠르게 하는 힌트

1. y와 x는 재귀 호출이 풀어야 할 부분 문제를 지정하낟. 이 두 입력이 정해지면 앞으로 우리가 만들 수 있는 경로들이 정해진다. 따라서 이들은 앞으로 풀어야 할 조각들에 대한 정보를 주는 입력들이다.
2. sum은 지금까지 어떤 경로로 이 부분 문제에 도달했는지를 나타낸다. sum은 지금까지 풀었더 ㄴ조각들에 대한 정보를 주는 입력이다.

```
[0] [1] ... [y-1]  [y] [y+1] ... [n-2] [n-1]
└이미 해결한 조각들┘ └-아직 해결하지 못한 조각들-┘
```

`(y,x)` 는 그림 오른쪽에 아직 해결하지 못한 조각들을 정의하는 입력이고, sum은 왼쪽에 이미 결정한 조각들에 대한 정보이다. 그런데 과련 sum이 앞으로 남은 조각들을푸는데 필요할까? (y,x)에서 맨 아래줄까지 내려가는 최대 경로는 sum이 얼마건 상관 없이 똑같다. 재귀 함수에 sum을 아ㅖ 입력으로 받지 않도록 하면 이 알고리즘은 훨씬 빨라질 것이다.

단 재귀 함수에서 sum을 입력으로 받지 않으면, 이전까지 어떤 숫자를 만났는지 알 수 없기 때문에 전체 경로의 최대 합을 반환할 수가 없다. 따라서 함수의 반환 값을 전체 경로의 최대치가 아니라 (y,x)에서 시작하는 부분 경로의 최대치로 바꿀 필요가 있다.

결과적으로 다음과 같은 부분 문제를 얻을 수 있다.

```
path2(y,x)=(y,x) 에서 시작해서 맨 아래줄가지 내려가는 부분 경로의 최대 합을 반환한다.
```

전체 경로의 최대 합을 반환하는 것이 아니라 부분 경로의 최대합을 반환한다. 즉 apth2는 앞으로 남은 조각들의 결과만을 반환한다. path2의 동작은 다음의 점화식으로 정의할 수 있다.

```
path2(y,x) = triangle[y][x] + max (path2(y+1, x), path2(y+1, x+1))
```

다음 코드는 부분 문제의 수는 O(n^2)이고 각 부분 문제를 계산하는 데는 상수 시간이 걸리기 때문에 전체 시간 복잡도는 O(n^2)가 된다.

```c++
// (y,x) 위치부터 맨 아래줄까지 내려가면서 얻을 수 있는 최대 경로의 합을 반환한다.
public static int run3(int y, int x) {
    // 기저 사례
    if(y == arr.length-1) return arr[y][x];
    // 메모이제이션
    if(cache2[y][x] != -1) return cache2[y][x];

    return cache2[y][x] = (Math.max(run3(y+1, x), run3(y+1,x+1)) + arr[y][x]);
}
```

[자바 전체 코드](https://gist.github.com/brightchul/fe2b5d7e1038bfeaba2ae4c962c5847a)