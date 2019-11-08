# 06 Brute-force

## 6.1 도입

사람들이 가장 많이하는 실수는 쉬운 문제를 어렵게 푸는 것이다. 이런 실수를 피하기 위해서 가장 먼저 무식하게 풀 수 있는지 생각해 봐야 한다.

무식하게 풀기(brute-force)는 전산학에서 컴퓨터의 머신 파워를 이용해서 경우의 수를 일일이 나열하면서 답을 찾는 방법을 의미한다. 이렇게 가능한 방법을 전부 만들어보는 알고리즘을 가리켜 완전탐색(exhaustive search)라고 한다. 

프로그래밍 대회에서 프로그램을 빠르고 정확하게 구현하는 능력을 검증하기 위해 입력의 크기를 작게 제한한 문제들이 흔히 출제되며 완전 탐색은 더 빠른 알고리즈의 기반이 되기도 하니 잘 익혀두도록 한다.



## 6.2 재귀 호출과 완전 탐색

### 재귀 호출

재귀 함수(recursive function)란 자신이 수행할 작업을 유사한 형태의 여러 조각을 쪼갠 뒤 그 중 한 조각을 수행하고, 나머지를 자기 자신을 호출해 실행하는 함수를 가리킨다. 

모든 재귀 함수는 이와 같이 **더이상 쪼개지지 않는** 최소한의 작업에 도달했을 때 답을 곧장 반환하는 조건문을 포함해야 한다. 이 떄 쪼개지지않는 가장 작은 작업들을 가리켜 재귀 호출의 기저 사례(base case) 라고 한다.

```java
public class Main {
    // 필수 조건 : n >= 1, 결과 : 1~n까지의 합을 반환한다.
    public static int loopSum(int num) {
        int ret = 0;
        for(int i=1; i<=num; i++) ret += i;
        return ret;
    }
    public static int recursiveSum(int num) {
        if(num == 1) return 1; // base case
        return num + recursiveSum(num-1);
    }
    public static int tailSum(int num, int result) {
        if(num==1) return result+1; // base case
        return tailSum(num-1, result + num);
    }
}
```



### 예제 : 중첩 반복문 대체하기

0번부터 차례대로 번호 매겨진 n개의 원소중 4개를 고르는 모든 경우를 출력하는 코드를 작성해보자. 예를 들어 n=5이라면 (0,1,2,3), (0,1,2,4), ..., (2,3,4,5)의 모든 경우를 출력하는 것이다. 일단은 4중 for문을 사용하면 된다. 하지만 입력에 따른 n개를 고르는 경우라면 사용할 수 없을 것이다. 

```java
public class Main {
    public static void main(String[] args) {
        
    }
    public static void ex1(num) {
        for(int i=0; i<num; i++) {
            for(int j=i+1; j<num; j++) {
                for(int k=j+1; k<num; k++) {
                    for(int l=k+1; l<num; l++) {
                        System.out.println(String.format("%d %d %d %d", i, j, k, l));
                    }
                }
            }
        }
    }
}
```

재귀호출은 이런 경우에 유연한 코드를 작성할 수 있도록 해준다. 위의 코드가 하는 작업을 몇가지 조각으로 나눌수 있다. (하나의 원소를 고른다. 남은 원소들을 고르는 작업을 자기 자신을 호출해 위임한다.)

남은 원소들을 고르는 작업은 다음과 같은 입력들의 집합으로 정의할 수 있다.

- 원소들의 총개수
- 더 골라야 할 원소들의 개수
- 지금까지 고른 원소들의 번호

```java
// n : 전체 원소의 수
// picked : 지금까지 고른 원소들의 번호
// toPick : 더 고를 원소의 수
// 앞으로 toPick개의 원소를 고르는 모든 방법을 출력한다.
public static void pick(int n, Stack<Integer> picked, int toPick) {
    // 기저 사례 : 더 고를 우너소가 없을 때 고른 원소들을 출력한다.
    if(toPick == 0) {
        System.out.println(picked);
        return;
    }
    
    // 고를수 있는 가장 작은 번호를 계산한다.
    int smallest = picked.size() == 0 ? 0 : picked.peek()+1;
    
    // 하나의 원소를 고른다.
    for(int next = smallest; next < n; ++next) {
        picked.push(next);
        pick(n, picked, toPick-1);
        picked.pop();
    }
}

// 실행 호출
pick(6, new Stack<Integer>(), 3);
```

재귀호출을 이용하면 특정 조건을 만족하는 조합을 모두 생성하는 코드를 쉽게 작성할수 있다. 그래서 완전 탐색을 구현할 때 유용한 도구이다.

```java
// 버전2
public static void ex2(int target, int maxIdx) {
    recursive(1,0, maxIdx, target, "");
}
public static void recursive(int curr, int idx, int maxIdx, int max, String str) {
    if(idx == maxIdx)
        System.out.println(str);
    else
        for(int i=curr; i<=max; i++) recursive(i+1, idx+1, maxIdx, max, str + i + " ");
}
```



### 예제 : 보글 게임 

**문제 내용 [링크]( https://algospot.com/judge/problem/read/BOGGLE )**

hasWord(y, x, word) = 보글 겡미판의 (y, x)에서 시작하는 단어 word의 존재 여부를 반환한다.

이 문제의 까다로운 점은 다음 글자가 될 수 있는 칸이 여러 개 있을 때 이 중 어느 글자를 선택해야 할지 미리 알고 없다는 점이다. 간단한 방법은 완전탐색을 이용하는 것이다.



#### 문제의 분할

hasWord()가 하는 일을 가장 자연스럽게 조각내는 방법은 각 글자를 하나의 조각으로 만드는 것이다. 첫글자가 다르다면 바로 종료를 하고 그다음 칸들을 찾으면 되고, 맞다면 마찬가지로 그 다음 글자를 인접한 칸에서 찾으면 된다.



#### 기저 사례의 선택

1. 위치 (y, x)에 있는 글자가 원하는 단어의 첫 글자가 아닌 경우 항상 실패
2. (1번에 해당하지 않을 때) 원하는 단어가 한 글자인 경우 항상 성공

>  입력이 잘못되거나 범위에서 벗어난 경우에도 기저 사례로 택해서 맨 처음에 처리하면 반복적인 코드를 제거하고 간결한 코드를 만드는데 도움이 된다. 



#### 구현

>  **주의**: 알고리즘 문제 해결 전략 6장을 읽고 이 문제를 푸시려는 분들은 주의하세요. 6장의 예제 코드는 이 문제를 풀기에는 너무 느립니다. 6장의 뒷부분과 8장을 참조하세요. 

```c++
// 다음칸의 상대좌표 목록을 함수 내에 직접 코딩해 넣은 것이 아니라 별로의 변수로 분리
const int dx[8] = {-1,-1,-1, 1, 1, 1, 0, 0};
const int dy[8] = {-1, 0, 1,-1, 0, 1,-1, 1};

// 5x5 보글 게임판의 해당 위치에서 주어진 단어가 시작하는지를 반환
bool hasWord(int y, int x, const string& word) {
    // 기저 사례1 : 시작 위치가 범위 밖이면 무조건 실패
    if(!inRange(y, x)) return false;
    
    // 기저 사례2 : 첫 글자가 일치하지 않으면 실패
    if(board[y][x] != word[0]) return false;
    
    // 기저 사례3 : 단어 길이가 1이면 성공
    if(word.size() == 1) return true;
    
    // 인접한 여덟 칸을 검사한다.
    for(int direction = 0; direction < 8; ++direction) {
        int nextY = y + dy[direction], nextX = x + dx[direction];
        // 다음 칸이 범위 안에 있는지, 첫 글자는 일치하는지 확인할 필요가 없다.
        if(hasWord(nextY, nextX, word.substr(!)))
            return true;
    }
    return false;
}
```





#### 시간 복잡도 분석

완전 탐색 알고리즘의 시간 복잡도는 가능한 후보의 최대 수를 계산하면 된다. 여기서는 각 칸에는 최대 8개의 이웃이 있고, 탐색은 단어의 길이 N에 대해 N-1 단계 진행된다. 따라서 최대 8^(N-1) 즉 O(8^N) 이 된다.

시간 복잡도가 단어의 길이에 따라 지수적으로 증가하기 때문에 짧은 경우에만 완전 탐색으로 해결할 수 있다. 긴 경우에는 3부의 설계 패러다임들을 사용해야 한다.



#### 완전 탐색 레시피 (해결 과정)

1. 완전 탐색은 존재하는 모든 답을 하나씩 검사하므로, 걸리는 시간은 가능한 답의 수에 정확히 비례한다. 최대 크기의 입력을 가정했을 때 답의 개수를 계산하고 이들을 모두 제한 시간 안에 생성할 수 있을지를 가늠한다. 만약 시간 안에 계산할 수 없다면 3부의 다른 장에서 소개하는 설계 패러다임을 적용해야 한다. 
2. 가능한 모든 답의 후보를 만드는 과정을 여러 개의 선택으로 나눈다. 각 선택은 답의 후보를 만드는 과정의 한 조각이 된다.
3. 그 중 하나의 조각을 선택해 답의 일부를 만들고, 나머지 답을 재귀 호출을 통해 완성한다.

4. 조각이 하나밖에 남지 않은 경우, 혹은 하나도 남지 않은 경우에는 답을 생성했으므로, 이것을 기저 사례로 선택해 처리한다.



#### 이론적 배경 : 재귀 호출과 부분 문제

보글 게임 문제에서 문제는 **게임판에서의 현재 위치 (y,x) 그리고 단어 word가 주어질 때 해당 단어를 이 칸에서부터 시작해서 찾을 수 있는가?** 로 정의된다. 그리고 해당 단어를 이 위치에서 찾을 수 있는지 알기 위해 최대 아홉가지 정보를 알아야 한다.

1. 현재 위치에 단어의 첫글자가 있는가?
2. 윗칸에서 단어의 나머지 글자를 찾을수 있는가?
3. 그 윗칸에서 나머지 글자들을 찾을수 있는가?
4. ..... (반복)

이중 2번 이후의 항목은 원래 문제에서 한 조각을 떼어냈을 뿐, 형식이 같은 또 다른 문제를 푼 결과이다. 문제를 구성하는 조각들 중 하나를 뺏기 때문에, 이 문제들은 원래 문제의 일부라고 말할 수 있다. 이런 문제들을 원래 문제의 **부분 문제**라고 한다.



## 6.3 문제 : 소풍

https://algospot.com/judge/problem/read/PICNIC



## 6.4 풀이 : 소풍

### 완전 탐색

가장 간단한 방법은 가능한 조합을 모두 만들어보는 것이다. 재귀호출을 이용해본다. 

재귀 호출을 이용해 문제를 해결하려면 각 답을 만드는 과정을 여러 개의 조각으로 나누어야 한다. 전체 문제를 n/2개의 조각으로 나눠서 해보자. 문제의 형태는 **아직 짝을 찾지 못한 학생들의 명단이 주어질 때 친구끼리 둘씩 짝짓는 경우의 수를 계산하라** 이다. 서로 친구인 두사람을 짝지어주고 나면 남은 학생들을 짝지어주는 것을 반복하기 때문이다.



### 중복으로 세는 문제

```c++
// 잘못된 재귀 코드

int n;
boo areFriends[10][10];

// taken[i] = i번째 학생이 짝을 이미 찾았으면 true 아니면 false
int countPairings(bool taken[10]) {
    // 기저 사례 : 모든 학생이 짝을 찾았으면 한 가지 방법을 찾았으니 종료
    bool finished = true;
    for(int i=0; i<n; i++)
        if(!taken[i]) finished = false;
    
    if(finished) return 1;
    int ret = 0;
    // 서로 친구인 두 학생을 찾아 짝을 지어준다.
    for(int i=0; i<n; i++) {
        for(int j=0; j<n; j++) {
            if(!taken[i] && !taken[j] && areFriends[i][j]) {
                taken[i] = taken[j] = true;
                ret += countPairings(taken);
                taken[i] = taken[j] = false;
            }
        }
    }
    return ret;
}
```

#### 문제점

- 같은 학생 쌍을 두 번 짝지어 준다. 얘를 들어 (0,1)과 (1,0)을 따로 세고 있다.
- 다른 순서로 학생들을 짝지어 주는 것을 서로 다른 경우로 세고 있다. 예를 들어 (0,1) 후에 (2,3)을 짝지어 주는 것과 (2,3) 후에 (0,1)을 짝지어 주는 것은 완전히 같은 방법인데 다른 경우로 세고 있다.

실질적으로 같은 답을 중복으로 세는 상황은 경우의 수를 다룰 때 흔하게 마주친다. 이것을 해결하기 위해 선택할 수 있는 좋은 방법은 항상 특정 형태를 갖는 답만을 세는 것이다. 보통 사전순으로 가장 먼저 오는 답 하나만을 세는 것이 있다.  (2,3), (0,1) 이나 (1,0), (2,3)은 세지 않지만 (0,1), (2,3)은 세는 것이다. 이 속성을 강제하기 위해서는 각  단계에서 남아있는 학생들중 가장 번호가 빠른 학생의 짝을 찾아주도록 하면된다. 이렇게 하면 앞의 두 가지 문제를 모두 해결할 수 있다.

```c++
// 중복 문제를 해결한 재귀 호출 코드
int n;
bool areFriends[10][10];
// taken[i] = i번째 학생이 짝을 이미 찾았으면 true, 아니면 false
int countParings(bool taken[10]) {
    // 남은 학생들 중 가장 번호가 빠른 학생을 찾는다.
    int firstFree = -1;
    for(int i=0; i<n; i++) {
        if(!taken[i]) {
            firstFree = i;
            break;
        }
    }
    // 기저 사례 : 모든 학생이 짝을 찾았으면 한 가지 방법을 찾았으니 종료한다.
    if(firstFree == -1) return 1;
    int ret = 0;
    // 이 학생과 짝지을 학생을 결정한다.
	for(int pairWith = firstFree+1; pairWith < n; ++pairWith) {
        if(!taken[pairWith] && areFriends[firstFree][pairWith]) {
            taken[firstFree] = taken[pairWith] = true;
            ret += countPairings(taken);
            taken[firstFree] = taken[pairWith] = false;
        }
    }
    return ret;
}

```



### 답의 수의 상한

모든 답을 생성해가며 수를 세는 재귀호출 알고리즈믄 답의 수에 정비례하는 시간이 걸리므로, 프로그램을 짜기 전에 답의 수가 얼마나 될지 예측해 보고 시간이 얼마나 걸릴지 확인해야 한다. 이 문제의 경우 가장 많은 답을 가질수 있는 입력은 열명의 학생이 모두 서로 친구인 경우로, 9 * 7 * 5 * 3 * 1 = 945가 된다.



## 6.5 문제 : 게임판 덮기

https://algospot.com/judge/problem/read/BOARDCOVER



## 6.6 풀이 : 게임판 덮기

게임판을 덮는 모든 경우를 완전 탐색을 이용해 해결할 수 있다. 블럭의 칸이 3칸이므로, 3의 배수가 아닐 경우 무조건 답이 없으니 이를 따로 처리한다. 그 외에는 3으로 나눠 내려놓을 블록의 수 N을 얻은 뒤, 문제의 답을 생성하는 과정을 N조각으로 나눠 한 조각에서 한 블록을 내려놓도록 한다. 그 과정의 반복을 재귀호출을 이용한다.



### 중복으로 세는 문제

블록 놓는 순서는 상관없으나 중복으로 셀수 있기에 특정한 순서대로 셀수 있도록 강제할 필요가 있다. 가장 간편한 방법은 각단계마다 아직 빈칸 중에서 가장 윗줄, 가장 왼쪽에 있는 칸을 덮도록 하는 것이다. ㅇ렇게 하면 한 답을 한 가지 방법으로밖에 생성할 수 없다.

항상 빈캄중 가장 위 & 가장 왼쪽에 있는 칸을 처음 채운다고 가정하기 때문에 그 지점의 왼, 윗에 있는 칸은 항상 채워져 잇다고 가정할 수 있다. 따라서 `┌ ┐└ ┘` 이렇게 4가지 방법으로 채울 수 있다.



### 답의 수의 상한

입력에서 흰 칸의 수는 50을 넘지 않는다고 했으므로, 50/3 = 16개의 블록을 놓을수 있다. 즉 답의 상한은 4^16 = 2^32 개가 된다. 하지만 실제로는 두 가지 방법으로 밖에 배치할 수 없다. 따라서 실제 답의 수는 상한보다 훨씬 작다고 예측할 수 있다.



### 구현

```C++
// 주어진 칸을 덮을 수 있는 네 가지 방법
// 블록을 구성하는 세 칸의 상대적 위치 (dy, dx)의 목록
const int coverType[4][3][2] = {
    {{0,0}, {1,0}, {0,1}},
    {{0,0}, {0,1}, {1,1}},
    {{0,0}, {1,0}, {1,1}},
    {{0,0}, {1,0}, {1,-1}}
};

// board의 (y,x)를 type번 방법으로 덮거나 덮었던 블록을 없앤다.
// delta = 1이면 덮고, -1이면 덮었던 블록을 없앤다.
// 만약 블록이 제대로 덮이지 않은 경우 (게임판 밖으로 나가거나, 겹치거나 검은 칸을 덮을때)
// false를 반환한다.
boo set(vector<vector<int>>&board, int y, int x, int type, int delta) {
    bool ok = true;
    for(int i=0; i<3; ++i) {
        const int ny = y + coverType[type][i][0];
        const int nx = x + coverType[type][i][0];
        if(ny<0 || ny >= board.size() || nx < 0 || nx >= board[0].size())
            ok = false;
        else if((board[ny][nx] += delta) > 1)
            ok = false;
    }
    return ok;
}

// board의 모든 빈 칸을 덮을 수 있는 방법의 수를 반환한다.
// board[i][j] = 1 이미 덮인 칸 혹은 검은 칸
// board[i][j] = 0 아직 덮이지 않은 칸
int cover(vector<vector><int>>& board) {
    // 아직 채우지 못한 칸 중 가장 윗줄 왼쪽에 있는 칸을 찾는다.
    int y= -1, x = -1;
    for(int i=0; i<board.size(); ++i) {
        for(int j=0; j<board[i].size(); ++j)
            if(board[i][j] == 0) {
                y = i;
                x = j;
                break;
            }
        if(y != -1) break;
    }
    // 기저 사례 : 모든 칸을 채웠으면 1을 반환한다.
    if(y==-1) return 1;
    int ret = 0;
    for(int type = 0; type < 4; ++type) {
        // 만약 board[y][x]를 type 형태로 덮을 수 있으면 재귀 호출한다.
        if(set(board, y, x, type, 1))
            ret += cover(board);
        // 덮었던 블록을 치운다.
        set(board, y, x, type, -1);
    }
    return ret;
}
```





## 6.7 최적화 문제

문제의 답이 하나가 아니라 여러개이고 그 중에서 가장 좋은 답을 찾아내는 문제들을 통칭해 최적화 문제(Optimization problem)라고 한다. n개의 사과 중에서 r개를 골라서 무게의 합을 최대화 하는 문제, 아니면 가장 무거운 사과와 가장 가벼운 사과의 무게차이를 최소화하는 문제등이 있다.

최적화 문제를 해결하는 방법은 여러가지가 있는데 그 중 가장 기초적이고 직관적인 방법이 완전 탐색이다. (동적 계획법 8장, 조합 탐색 11장, 결정 문제로 해결 12장 등)



### 예제 : 여행하는 외판원 문제

https://algospot.com/judge/problem/read/TSP1

#### 무식하게 풀 수 있을까?

완전 탐색으로 문제 푸는 첫 단계는 시간 안에 답을 구할 수 있을지 확인하는 것이다. 시작한 도시로 되돌아 오기 때문에 0번 도시에서 출발한다고 가정해도 경로의 길이는 다르지 않다. 남은 n-1개의 도시를 나열하는 방법은 (n-1)! 가지가 있다. 도시가 10개라면 9! = 362,880 개가 되며 컴퓨터로는 1초안에 처리되니 완전탐색을 통해 문제를 해결할 수 있다.



#### 재귀호출을 통한 답안 생성

재귀호출을 이용해 n개의 도시로 구성된 경로를 n개의 조각으로 나눠 앞에서부터 도시를 하나씩 추가해 경로를 만들어 간다. shortestPath(path)=path 가 지금까지 만든 경로일 때, 나머지 도시들을 모두 방문하는 경로들 중 가장 짧은 것의 길이를 반환한다. 

```c++
int n; // 도시의 수
double dist[MAX][MAX]; // 두 도시 간의 거리를 저장하는 배열
// path : 지금까지 만든 경로
// visited : 각 도시의 방문 여부
// currentLength : 지금까지 만든 경로의 길이
// 나머지 도시들을 모두 방문하는 경로들 중 가장 짧은 것ㅇ의 길이를 반환한다.
double shortestPath(vector<int>& path, vector<bool>& visited, double currentLength) {
    // 기저 사례 : 모든 도시를 다 방문했을 때는 시작 도시로 돌아가고 종료한다.
    if(path.size() == n)
        return currentLength + dist[path[0]][path.back()];
    double ret = INF; // 매우 큰 값으로 초기화
    // 다음 방문할 도시를 전부 시도해 본다.
    for(int next = 0; next < n; ++next) {
        if(visited[next]) continue;
        int here = path.back();
        path.push_back(next);
        visited[next] = true;
        // 나머지 경로를 재귀 호출을 통해 완성하고 가장 짧은 경로의 길이를 얻는다.
        double card = shortestPath(path, visited, currentLength + dist[here][next]);
        ret = min(ret, cand);
        visited[next] = false;
        path.pop_back();
    }
    return ret;
}
```



## 6.8 문제 : 시계 맞추기 

https://algospot.com/judge/problem/read/CLOCKSYNC



## 6.9 풀이 : 시계 맞추기

### 문제 변형하기

이 문제는 스위치를 누르는 순서가 전혀 중요하지 않다는 것이다. 그저 각 스위치를 몇번이나 누를 것인지가 계산해야 할 것이다. 하지만 바로 완전 탐색을 적용할 수는 없는데, 완전 탐색 알고리즘을 사용하려면 스위치를 누르는 횟수의 모든 조합을 하나 하나 열거할 수 있어야 하는데, 각 스위치를 몇 번 누르는지는 상관없고 따라서 조합의 수가 무한하기 때문이다.

시계는 12시간이 지나면 제 자리로 돌아온다는 점을 이용하면 무한한 조합의 수를 유한하게 바꿀 수 있다. 4번 누르면 하나도 누르지 않은 것과 같으므로 세 번을 초과해서 누를 일이 없는 것이다. 따라서 각 스위치를 누르는 횟수는 0~3사이 정수이다. 열개의 스위치가 있으니 경우의 수는 4^10 = 1,048,576 이고 1억미만이므로 무리 없는 크기이다.



### 완전 탐색 구현하기

문제를 열 조각으로 나눈 후 각 조각에서 한 스위치를 누를 횟수를 정하는 식으로 구현했다.

- 답을 구할 수 없다면 반환 값을 매우 큰 값으로 만들어서 가장 작은 출력 값을 찾아야 할 때 답이 없는 경우를 따로 확인하지 않아도 된다.
- 스위치와 시계 연결을 2차원 배열을 통해 저장했다. 이런 형태의 표현은 스위치마다 연결되어 있는 시계의 개수가 다르다는 점에 신경 쓸 필요가 없다는 장점은 있지만 눈으로 확인하기는 까다롭다.

```c++
const int INF = 999, SWITCHES = 10, CLOCKS = 16;
// linked[i][j] = 'x' : i번 스위치와 j번 시계가 연결되어 있다.
// linked[i][j] = '.' : i번 스위치와 j번 시계가 연결되어 있지 않다.

const char linked[SWITCHES][CLOCKS+1] = {
    // 0123456789012345
    "xxx.............",
    "...x...x.x.x....",
    "....x.....x...xx",
    "x...xxxx........",
    "......xxx.x.x...",
    "x.x...........xx",
    "...x..........xx",
    "....xx.x......xx",
    ".xxxxx..........",
    "...xxx...x...x.."}

// 모든 시계가 12시를 가리키고 있는지 확인한다.
bool areAligned(const vector<int>& clocks);
// swtch번 스위치를 누른다.
void push(vector<int>& clocks, int swtch) {
    for(int clock = 0; clock < CLOCKS; ++clock)
        if(linked[swtch][clock] == 'x') {
            clocks[clock] += 3;
            if(clocks[clock] == 15) clocks[clock] = 3;
        }
}

// clocks : 현재 시계들의 상태
// swtch : 이번에 누를 스위치의 번호
// 가 주어질 때, 남은 스위치들을 눌러서 clocks를 12시로 맞출 수 있는 최소 횟수를 반환한다.
// 만약 불가능하다면 INF 이상의 큰 수를 반환한다.
int solve(vector<int>& clocks, int swtch) {
    if(swtch == SWITCHES) return areAligned(clocks) ? 0 : INF;
    // 이 스위치를 0번 누르는 경우부터 세번 누르는 경우까지를 모두 시도한다.
    int ret = INF;
    for(int cnt = 0; cnt < 4; ++cnt) {
        ret = min(ret, cnt + solve(clocks, swtch + 1));
        push(clocks, swtch);
    }
    // push(clocks, swtch)가 4번 호출되었으니 clocks는 원래와 같은 상태가 된다.
    return ret;
}
```





## 6.10 많이 등장하는 완전 탐색 유형

입력의 크기에 따라 답의 개수가 어떻게 변하는지 알고 구현하는 방법을 연습해두면 좋다.



### 모든 순열 만들기

서로 다른 N개의 원소를 일렬로 줄 세운 것을 순열(permutation)이라고 부른다. 주어진 원소의 모든 순열을 생성해서 풀 수 있는 문제는 자주 만날수 있고, 부분문제로도 나타나니 모든 순열을 생성하는 코드를 한번 신경써서 작성해 보면 좋다. 단 순열의 수는 N!이 되는데, N이 10을 넘어간다면 시간 안에 모든 순열을 생성하기 어려우니 완전 탐색이 아닌 다른 방법을 생각해야 한다. c++사용자들은 STL에 포함된 next_permutation() 함수에서 모든 순열을 순서대로 생성하는 작업을 대신해 준다.



### 모든 조합 만들기

서로 다른 N개의 원소 중에서 R개를 순서 없이 골라낸 것을 조합(combination)이라고 부른다. 피자에 올릴 수있는 다섯종류의 토핑인 소시지, 쇠고기, 올리브, 피망, 양파 중 세가지를 고르는 것이 대표적인 예이다. 이때 경우의 수는 이항 계수 `nCr = n! / ( r! (n-r)! )` 로 정의 된다.



### 2^n 가지 경우의 수 만들기

n개의 질문에 대한 답이 예/아니오 중의 하나라고 할 때 존재할 수 있는 답의 모든 조합 수는 2^n 가지이다. 이 모든 조합을 n비트 정수로 표현한다고 하면 1차원 for문으로도 시도할 수 있다. (16장 참고)