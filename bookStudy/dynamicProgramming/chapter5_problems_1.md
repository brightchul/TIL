# 5장 실전 문제

## 5.1 최소 교정 비용 문제

두 단어 str1, str2가 주어지고 str1에서 수행할 수 있는 연산은 삽입, 삭제, 치환이 있다. 두 단어 간의 교정 비용은 한 단어에서 다른 단어로 바꾸는데 필요한 글자 연산 횟수로 정의한다. str1 -> str2로 바꾸는데 필요한 연산의 최소 개수를 구하는 프로그램을 작성한다.

```
C A T
    |
C A R
    치환
    
=>최소 교정 비용 : 1

S      U N D A Y
  |  |   |
S A  T U R D A Y
 삽입 삽입 치환
 
=> 최소 교정 비용  : 3
```

​     

### 재귀 호출을 사용하는 풀이와 설명

재귀로 풀기 위해서 같은 유형을 가진 작은 문제를 사용해 큰 문제를 정의할 수 있어야 한다.

str1, str2의 각 첫번째 글자가 시작지점이다.

1. 두 글자가 같다면 양쪽 단어의 첫 번째 글자에 대해서는 아무것도 하지 않아도 되며, 첫 글자를 제외한 두 단어 간의 최소 교정비용을 찾아야 한다. 즉 양쪽 단어에서 첫 번째 글자는 무시한다는 의미이다.
2. 두 글자가 다르다면 사용 가능한 세 개의 연산을 그려해봐야 한다.
   1. 삭제 연산: str1에서 첫 번째 글자를 삭제하고 난 후의 첫 번째 글자가 삭제된 str1과 str2사이의 최소교정 비용을 구한다.
   2. 치환 연산 : str1의 첫 번째 글자를 str2의 첫번째 글자로 치환한 다음 양쪽의 첫 번째 글자를 제외한 단어 간의 최소 교정 비용을 구한다.
   3. 삽입 연산 : str2의 첫 번째 글자를 str1의 제일 앞에 삽입한 다음 양쪽 단어의 첫 번째 글자를 제외한 나머지 단어 간의 최소 교정 비용을 구한다. str1의 길이는 한 글자 증가한다.

연산 이후의 최소 교정 비용은 재귀 호출에 맡긴다. 삭제, 치환, 삽입의 어떤 연산을 수행하건 하나의 연산을 수행하므로 각 재귀 호출의 결과 중 제일 최소값에 1을 더해서 반환한다. 이 코드는 O(3^n)의 시간 복잡도를 갖는다. 

```typescript
// 책 원문은 C++ 포인터로 연산하지만 js에서는 그러지 않으니 인덱스값을 받아서 한다.
// string.prototype.slice를 써도 되겠지만 그러면 문자열 복사가 계속해서 일어나서 효율적인 메모리 사용이 아니다.

export function editDistance(str1: string, str2: string) {
  function calculate(idx1: number, idx2: number): number {
    // str1이 빈 문자열이면 str2의 모든 글자를 삽입하면 된다.
    if (str1.length <= idx1) return str2.length - idx2;

    // str2이 빈 문자열이면 str1의 모든 글자를 제거하면 된다.
    if (str2.length <= idx2) return str1.length - idx2;

    // 첫번째 글자가 같을 때는 첫 번째 글자를 무시하고 나머지 단어 간의 최소 교정 비용ㅇ르 구한다.
    if (str1[idx1] === str2[idx2]) return calculate(idx1 + 1, idx2 + 1);

    // 삭제 연산
    const remove: number = calculate(idx1 + 1, idx2);

    // 치환 연산
    const update: number = calculate(idx1 + 1, idx2 + 1);

    // 삽입 연산
    const insert: number = calculate(idx1, idx2 + 1);

    // 세 연산 이후 최소 교정 비용 간의 최소값에 1을 더해서 반환한다.
    return getMinimum(remove, update, insert) + 1;
  }
  
  return calculate(0, 0);
}

function getMinimum(...args: number[]): number {
  return args.reduce((p, a) => Math.min(p, a));
}
```

​             

### 다이나믹 프로그래밍을 사용하는 풀이와 설명

다이나믹 프로그래밍 접근 방법은 두 단어의 교정 비용을 구하는 과정에 필요한 모든 가능한 조합에 대해서 교정 비용을 상향식으로 구하는 것이다. str1, str2 두 단어의 길이가 각각 n과 m이라면 빈 문자열의 경우를 포함한 모든 가능한 조합은` (m+1) x (n + 1)` 크기의 행렬을 사용해야 한다.

|      |      |  S   |  A   |  T   |  U   |  R   |  D   |  A   |  Y   |
| :--: | ---- | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
|      |      |      |      |      |      |      |      |      |      |
|  S   |      |      |      |      |      |      |      |      |      |
|  U   |      |      |      |      |      |      |      |      |      |
|  N   |      |      |      |      |      |      |      |      |      |
|  D   |      |      |      |      |      |      |      |      |      |
|  A   |      |      |      |      |      |      |      |      |      |
|  Y   |      |      |      |      |      |      |      |      |      |

이 행렬의 \(i, j\) 번째 셀의 값은 str1의 i개의 글자와 str2의 j개의 글자 사이의 최소 교정 비용의 값이다. 이 행렬의 값을 모두 채우고 난 후의 가장 우하단의 값 C\(7,8\) 이 두 문자간의 최소 교정 비용이 된다.

위 행렬에서 첫 번째 행은 첫 번째 단어가 빈 문자열일 때의 최소 교정 비용이며, 첫 번째 열은 두 번째 단어가 빈 문자열일 때의 최소 교정 비용이다.

첫 번째 열과 첫 번째 행은 쉽게 채울 수 잇다. 첫 번째 단어가 빈 문자열일 때 두 단어를 똑같이 만들려면 두 번째의 단어의 모든 글자를 첫 번째 단어에 삽입하거나 두 번째 단어의 모든 글자를 삭제하면 된다. 어떤 쪽이건 필요한 연산의 수는 두 번째 단어의 글자수와 같다. 비슷한 방식으로 첫 번째 열도 첫 번째 단어의 글자의 수로 채울 수 있다.


|      |      |  S   |  A   |  T   |  U   |  R   |  D   |  A   |  Y   |
| :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
|      |  0   |  1   |  2   |  3   |  4   |  5   |  6   |  7   |  8   |
|  S   |  1   |      |      |      |      |      |      |      |      |
|  U   |  2   |      |      |      |      |      |      |      |      |
|  N   |  3   |      |      |      |      |      |      |      |      |
|  D   |  4   |      |      |      |      |      |      |      |      |
|  A   |  5   |      |      |      |      |      |      |      |      |
|  Y   |  6   |      |      |      |      |      |      |      |      |

나머지 셀은 다음 로직에서 채울 수 있다. 여기서 EditD가 앞에서 본 표에 해당하는 배열, 즉 최소 교정 비용을 저장하는 배열이다.

```
if(str[i-1] === str[j-1])
	EditD[i][j] = EditD[i-1][j-1]
else
	EditD[i][j] = 1 + MINIMUM(EditD[i-1][j-1], EditD[i-1][j], EditD[i][j-1])
```

1. 두 글자가 같으면 교정 비용의 차이가 없으므로 대각선 방향 왼쪽 위 셀의 값을 가지고 온다. 

2. 두 글자가 다르면 위쪽 셀, 왼쪽 셀, 왼쪽 위 셀의 값의 최솟값을 가져와 1을 더한다. 각 셀에 해당하는 문자열에 치환, 삽입, 삭제 연산을 통해서 도달한 결과이다. 

   1. str1에 삭제 연산을 수행하면 sun, satur의 최소 교정 비용과 같은 값이 된다.
   2. str1에 삽입 연산을 수행하면 sundr, satur이 되어 sund , satu의 최소 교정 비용과 같은 값이 된다.
   3. 양쪽 단어에 최환 연산을 수행하면 sun, satu의 최소 교정 비용과 같은 값이 된다.

```typescript
function editDistance(str1: string, str2: string, m: number, n: number) {
  const EditD = Array(m + 1)
    .fill(0)
    .map((o: number) => Array(n + 1).fill(0));

  for (let j = 0; j <= n; j++) EditD[0][j] = j;

  for (let i = 0; i <= m; i++) EditD[i][0] = i;

  for (let i = 1; i <= m; i++) {
    for (let j = 1; j <= n; j++) {
      // 두 글자가 같다면
      if (str1[i - 1] == str2[j - 1]) EditD[i][j] = EditD[i - 1][j - 1];
      // 두 글자가 다르다면
      else
        EditD[i][j] =
          getMinimum(EditD[i - 1][j - 1], EditD[i][j - 1], EditD[i - 1][j]) + 1;
    }
  }
  return EditD[m][n];
}
```

이 로직을 따라 다음과 같이 행렬이 채워진다.

|      |      |  S   |  A   |  T   |  U   |  R   |  D   |  A   |  Y   |
| :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
|      |  0   |  1   |  2   |  3   |  4   |  5   |  6   |  7   |  8   |
|  S   |  1   |  0   |  1   |  2   |  3   |  4   |  5   |  6   |  7   |
|  U   |  2   |  1   |  1   |  2   |  2   |  3   |  4   |  5   |  6   |
|  N   |  3   |  2   |  2   |  2   |  3   |  3   |  4   |  5   |  6   |
|  D   |  4   |  3   |  3   |  3   |  3   |  4   |  3   |  4   |  5   |
|  A   |  5   |  4   |  3   |  4   |  4   |  4   |  4   |  3   |  4   |
|  Y   |  6   |  5   |  4   |  4   |  5   |  5   |  5   |  4   |  3   |

이 코드의 시간 복잡도는 O(n^2)이며 O(n^2)만큼의 추가메모리가 필요하다.






​          

## 5.2 직사각형에서 총 경로 수 구하기

M x N 개의 방으로 구성된 직사각형이 있을 때 좌상단 방에서 우하단 방까지 이동하는 모든 경로의 수를 구해본다. 단, 방과 방 사이의 이동은 오른쪽 방향과 아래쪽 방향으로만 가능하다. 3 X 4 구조의 직사각형일 때 시작 인덱스의 값을 0으로 하여 좌상단 방 (0,0)에서 우하단 방 (2,3)으로 이동하는 가능한 경로는 모두 10개이다. 

​      

### 재귀 호출을 사용하는 풀이와 설명

이 문제는 4.1절 최소 이동 비용 구하기 예제와 비슷하다. 접근 방식도 유사하다. 방 (m, n)은 다른 두방에서 접근 가능하다.

1. 바로 위쪽 방 : (m-1, n)
2. 바로 왼쪽 방 : (m, n-1)

`방 (m, n)까지의 경로` 는 `방(m-1, n)까지의 경로 + 방(m, n-1)까지의 경로` 이다. 이것을 보고 재귀 호출 로직을 정의한다.

종료조건은 위쪽 경계나 왼쪽 경계에 도달했을 때이다. 첫번 째 행의 어떤 방이건 오른쪽으로 이동하는 한 가지 뿐이며, 첫 번째 열의 어떤 방이건 그 방까지의 경로는 아래쪽으로 쭉 이동하는 한 가지 뿐이다. 방 (0,0)에 도달하는 방법은 도착점이므로 0이다. 

```typescript
// 이 코드의 시간 복잡도는 지수 시간 O(2^n)이다. 
// 이 코드에서는 M x N를 입력받는 경우를 가정하고 만들었다.

function numOfPathsRecursive(m: number, n: number): number {

  // 종료 조건
  if (m === 1 && n === 1) return 0;
  if (m === 1 || n === 1) return 1;
  
  // 재귀 호출
  return numOfPathsRecursive(m - 1, n) + numOfPathsRecursive(m, n - 1);
}
```

​              

### 다이나믹 프로그래밍을 사용하는 풀이와 설명

```typescript
// 이 코드의 시작 복잡도는 O(n^2)이다

function numOfPathsDP(m: number, n: number): number {
  // 2차원 캐시 배열을 생성한다.
  const cache = makeArray(0, m, n);

  // 첫 행과 첫 열을 채운다.
  for (let row = 1; row < m; row++) cache[row][0] = 1;
  for (let col = 1; col < n; col++) cache[0][col] = 1;

  // 나머지 영역들을 채운다.
  for (let row = 1; row < m; row++) {
    for (let col = 1; col < n; col++) {
      cache[row][col] = cache[row - 1][col] + cache[row][col - 1];
    }
  }
  return cache[m - 1][n - 1];
}
```

​       

## 5.3 문자열 인터리빙 확인 문제

두 문자열 A와 B가 있다. 이 문자열 내의 모든 글자의 상대적인 순서가 유지된 채 섞여서 새로운 문자열 C가 만들어지면 이 때 문자열 C를 문자열 A와 문자열 B의 인터리빙(interleaving)이라고 한다.

```
A = 'xyz'
B = 'abcd'
C = 'xabyczd'

C = interleaving(A, B)
```

​        

### 재귀 호출을 사용하는 풀이와 설명

먼저 세 문자열의 길이를 확인하면, 확인해야 하는 경우의 수를 ㄹ줄일 수 있다. C의 길이가 A,B의 길이의 합과 같지 않다면 인터리빙이 아니다. 

```
A = 'xyz' , B = 'abcd' , C = 'xabyczd'
```

C의 첫 글자 x는 B의 첫 번째 글자가 아니므로 A에서 가져왔다. 그러면 그 이후에는 아래의 경우를 파악하면 된다.

```
A = 'yz' , B = 'abcd' , C = 'abyczd'
```

계속 동일한 유형이므로 재귀 호출을 사용해서 풀수 있다. 하지만 A, B에 같은 문자가 있다면 어떻게 해야할까?

```
A = 'bcc' , B = 'bbca' , C = 'bbcbcac'
```

이러한 경우에는 C의 첫 글자 b는 A, B 어디서 왔는지 바로 알수가 없다. 이 때는 다음처럼 양쪽 모두 확인해봐야 한다.

```
A = 'bcc' , B = 'bbca' , C = 'bbcbcac'

// C의 첫 글자가 A에서 삽입된 경우
A = 'cc' , B = 'bbca' , C = 'bbcbcac'

// C의 첫 글자가 B에서 삽입된 경우
A = 'bcc' , B = 'bca' , C = 'bbcbcac'
```

각각의 경우에서도 같은유형의 하위 문제이다. 즉 최적의 하위 구조를 가지고 있는 문제인 것이다. 아래를 보면 중복되는 하위 문제 계산 횟수가 늘어난다. 따라서 이 문제는 DP를 이용해서 푸는게 좋다.

```
A = 'bcc' , B = 'bbca' , C = 'bbcbcac'

// C의 첫 글자가 A에서 삽입된 경우
A = 'cc' , B = 'bbca' , C = 'bcbcac' 

  // C의 첫 글자는 B에서만 삽입 가능 
  A = 'cc' , B = 'bca' , C = 'cbcac'

// C의 첫 글자가 B에서 삽입된 경우
A = 'bcc' , B = 'bca' , C = 'bcbcac'

  // C의 첫 글자가 A에서 삽입된 경우    (중복)
  A = 'cc' , B = 'bca' , C = 'cbcac'
	
  // C의 첫 글자가 B에서 삽입된 경우
  A = 'bcc' , B = 'ca' , C = 'cbcac'
```

​     

아래 코드는 시간 복잡도가 O(2^n)이다. 

```typescript
function isInterleavingRecursive(strA: string, strB: string, strC: string) {
  // 만약 모든 문자열이 빈 문자열인 경우
  if (!strA && !strB && !strC) return true;
  // strA, strB 문자열의 길이의 합이 C 문자열의 길이와 다를때)
  if (strA.length + strB.length !== strC.length) return false;

  function calculate(idxA: number, idxB: number, idxC: number) {
    // 만약 모든 문자열이 빈 문자열인 경우
    if (!strA[idxA] && !strB[idxB] && !strC[idxC]) return true;

    let caseA = false;
    let caseB = false;

    // strA첫글자와 strC의 첫 글자가 같은 경우
    if (strA[idxA] === strC[idxC]) caseA = calculate(idxA + 1, idxB, idxC + 1);

    // strA첫글자와 strC의 첫 글자가 같은 경우
    if (strB[idxB] === strC[idxC]) caseB = calculate(idxA, idxB + 1, idxC + 1);

    // 둘 중 하나라도 참이면 인터리빙
    return caseA || caseB;
  }
}
```

​       

### 다이나믹 프로그래밍을 사용하는 풀이와 설명

이번에는 상향식으로 풀어본다. 각 단계마다 C의 부분 문자열이 A의 부분 문자열과 B의 부분 문자열의 인터리빙인지를 확인한다. 문자열 A의 길이를 m, 문자열 B의 길이를 n이라고 할때 i<=m인 i에 대해서 문자열 A의 첫 i글자로 이루어진 문자열 A'와 j<=n인 j에 대해서 문자열 B의 첫 j글자로 이루어진 문자열 B' 의 인터리빙으로 C의 첫(i + j) 글자로 이루엊니 문자열 C'를 만들 수 있는지를 검사한다. 

i와 j 두개의 인수가 있으므로 하위 문제의 결과를 저장하는데는 2차원 자료구조가 필요하다. (A의 각글자는 행, B의 각 글자는 열에 대응). 이 행렬 역시 인덱스이 시작 값은 0이다.

예를 들어 ` A = 'bcc' , B = 'bbca' , C = 'bbcbcac' ` 로 보자.

|      |        |  b   |  b   |  c   |   a    |
| :--: | :----: | :--: | :--: | :--: | :----: |
|      | (0, 0) |      |      |      |        |
|  b   |        |      |  ☆   |      |        |
|  c   |        |      |      |      |   ★    |
|  c   |        |      |      |      | (3, 4) |

이 행렬의 셀 (i, j)의 값은   C'가 A'와 B'의 인터리빙이면 참이 된다. ☆로 표시한 셀 (1, 2)는 b, bb를 인터리빙하여 bbc를 만들 수 없으므로 False이다. 반대로  ★로 표시한 셀 (2, 4)는 bc, bbca를 인터리빙하여 bbcbca를 만들 수 잇으므로 True로 채운다.

첫 번째 셀 (0, 0)은 T이다. 빈 무자열은 두 개를 인터리빙해도 빈 문자열이기대문이다.

첫 번째 행은 문자열A가 빈 문자열인 경우이다. 이 때는 B의 부분 문자열이 C의 부분 문자열과 같으면 참이다. 로직은 다음과 같다.

```
if (B[i-1] != C[i-1])
	ilMatrix[0][i] = false
else
	ilMatrix[0][i] = ilMatrix[0][i-1]
	
if (A[j-1] != C[j-1])
	ilMatrix[j][0] = false
else
	ilMatrix[j][0] = ilMatrix[j-1][0]
```


이렇 첫 번째 행과 첫 번째 열을 채운 결과는 다음과 같다.

|      |      |  b   |  b   |  c   |  a   |
| :--: | :--: | :--: | :--: | :--: | :--: |
|      |  T   |  T   |  T   |  T   |  F   |
|  b   |  T   |      |      |      |      |
|  c   |  F   |      |      |      |      |
|  c   |  F   |      |      |      |      |



이제 나머지 셀은 좌상단부터 행렬을 채워간다. 각 셀 (i, j)에 대응하는 문자열 A, B, C의 현재 글자는 각각 A[i-1], B[j-1], C[i+j-1] 이다. 각 셀에 대해서 다음의 네 가지 경우가 가능하다. 

1. C의 현재 글자가 A의 현재 글자와 B의 현재 글자 어느쪽과도 다른 경우, 이 때 셀의 값은 F이다.
2. C의 현재 글자가 A의 현재 글자와 같지만 B의 현재 글자와 다른 경우, 이 때 셀의 값은 바로 위 셀의 값과 같다.
3. C의 현재 글자가 B의 현재 글자와 같지만 A의 현재 글자와 다른 경우 이 때 셀의 값은 바로 왼쪽 셀의 값과 같다.
4. A, B, C 현재 글자가 모두 같은 경우, 이 때 셀의 값은 위쪽 셀의 값이나 오른쪽 셀의 값 둘 중 하나가 T이면 T이다. 그렇지 않다면 F이다.

이와 같은 로직으로 다음과 같이 행렬을 완성할 수 있다.

|      |      |  b   |  b   |  c   |  a   |
| :--: | :--: | :--: | :--: | :--: | :--: |
|      |  T   |  T   |  T   |  T   |  F   |
|  b   |  T   |  T   |  F   |  T   |  F   |
|  c   |  F   |  T   |  T   |  T   |  T   |
|  c   |  F   |  F   |  T   |  F   |  T   |



이 로직을 반영한 코드 (시간 복잡도는 O(n^2))

```typescript

function isInterleavingDP(strA: string, strB: string, strC: string) {
  const M = strA.length;
  const N = strB.length;
  const lengthC = strC.length;

  //A와 B 문자열의 길이의 합이 C문자열의 길이와 다를 때
  if (lengthC !== M + N) return false;

  // 인터리빙 여부를 저장하는 2차원 배열
  const ilMatrix = makeArray<boolean | undefined>(undefined, M + 1, N + 1);
  ilMatrix[0][0] = true;

  // 첫번째 열을 채운다.
  for (let i = 1; i <= M; i++) {
    if (strA[i - 1] !== strC[i - 1]) {
      ilMatrix[i][0] = false;
    } else {
      ilMatrix[i][0] = ilMatrix[i - 1][0];
    }
  }

  // 첫번째 행을 채운다.
  for (let j = 1; j <= N; j++) {
    if (strB[j - 1] !== strC[j - 1]) {
      ilMatrix[0][j] = false;
    } else {
      ilMatrix[0][j] = ilMatrix[0][j - 1];
    }
  }

  // 나머지 셀을 채운다.
  for (let i = 1; i <= M; i++) {
    for (let j = 1; j <= N; j++) {
      // 현재 셀의 A, B, C의 글자
      let currentA = strA[i - 1];
      let currentB = strB[j - 1];
      let currentC = strC[i + j - 1];

      // C의 글자가 A의 글자와 같고 B의 글자와 다를때
      if (currentA === currentC && currentB !== currentC) {
        ilMatrix[i][j] = ilMatrix[i - 1][j];
      }

      // C의 글자가 B의 글자와 같고 A의 글자와 다를때
      else if (currentA !== currentC && currentB === currentC) {
        ilMatrix[i][j] = ilMatrix[i][j - 1];
      }

      // A, B, C 글자 모두가 같을 때
      else if (currentA === currentC && currentB === currentC) {
        ilMatrix[i][j] = ilMatrix[i - 1][j] || ilMatrix[i][j - 1];
      }

      // C의 글자가 A, B 두 글자 어느 쪽과도 다를 때
      else {
        ilMatrix[i][j] = false;
      }
    }
  }
  return ilMatrix[M][N];
}

```





## 5.4 부분집합의 합 구하기

0을 포함한 양의 정수로 이루어진 집합의 부분집합 중에 원소의 합이 X인 부분집합이 존재하는지 검사하는 함수를 작성하자. 예를 들어 `[3,2,7,1]`이고 x가 6이라면, 부분집합 `[3,2,1]` 의 원소 합이 6이므로 참을 반환해야 한다.

​      

### 재귀 호출을 사용하는 풀이와 설명

합이 X가 되는 부분집합을 찾을 때 집합 내의 어떤 원소 P에 대해 2가지 경우가 있다.

- 부분 집합에 이 원소를 포함한다면 집합의 나머지에서 합이 X-P가 되는 부분 집합을 찾아야 한다.
- 부분 집합에 이 원소를 포함하지 않는 다면 집합의 나머지에서 합이 x가 되는 부분집합을 찾는다. 

두 경우다 원래 문제와 같은 유형의 문제가 되기 때문에 재귀 호출을 사용해 해결할 수 있다. 재귀 호출의 종료 조건은 x가 0이 되었는지(성공) 또는 집합 내의 원소를 모두 사용했는지(실패) 여부이다. 만약 원소의 값이 X보다 크다면 부분집합에 포함하지 않아도 된다.

```typescript
// 이 재귀함수를 실행하는 데에는 지수 시간인 O(2^n)이 필요하다.

function isSubsetSumRecursive(
  arr: number[],
  n: number,
  x: number
): boolean {
  // 종료 조건 1 : x가 0이면 성공 종료 조건이다.
  if (x === 0) return true;

  // 종료 조건 2 : x가 0이 아니고 남은 원소가 없다면 실패 종료 조건이다.
  if (n === 0) return false;

  // x보다 큰 원소는 무시해도 된다.
  if (arr[0] > x) return isSubsetSumRecursive(arr.slice(1), n - 1, x);

  // 부분집합에 원소를 포함하지 않는 경우와
  // 원소를 포함하는 경우 각각에 대해 재귀 호출합니다.
  return (
    isSubsetSumRecursive(arr.slice(1), n - 1, x) || // 포함하지 않는 경우
    isSubsetSumRecursive(arr.slice(1), n - 1, x - arr[0]) // 포함하는 경우
  );
}
```

​                  

### 다이나믹 프로그래밍을 사용하는 풀이와 설명

상향식으로 문제를 풀어나가면서 중간 결과를 2차원 배열 `subsum[i][j]`에 저장한다. 이때 `subsum[i][j]` 는 집합의 첫 `(i+1)` 개의 원소로 구성된 집합에 대해서 합이 `j (<=j<=x)` 인 부분집합이 있는지에 대한 찹/거짓 값이다.

예를들어 `[3,2,7,1]` 이고 x가 6인 경우를 보자.

먼저 0열인 경우 공집합이므로 모든 값들이 전부 T이다. 그리고 첫번째 행인 3부터 해당 값이 가능한지를 T/F로 채워나간다.

|  -   |  0   |  1   |  2   |  3   |  4   |  5   |  6   |
| :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
|  3   |  T   |  F   |  F   |  T   |  F   |  F   |  F   |
|  2   |  T   |      |      |      |      |      |      |
|  7   |  T   |      |      |      |      |      |      |
|  1   |  T   |      |      |      |      |      |      |

그 아래부터는 다음의 규칙을 지키며 채워나간다.

- `3,2,7,1` 의 각 행에서 각각의 값 p에 대해서 인덱스 p-1까지는 윗값을 그대로 가져온다. 왜냐하면 p-1까지는 p로는 어떻게 할 수 없기 때문이다.
- 인덱스 p부터는 바로 위의 행 값이 T면 자신도 T이다. 왜냐하면 부분집합에 p값을 포함하지 않아도 T가 되기 때문이다.
- 인덱스 p부터 바로 윗 값이 T가 아닐 경우 윗 행의 값중에`idx-p` 의 값을 가져온다. p값만큼 이동한 것은 p값만큼 값을 뺀 값이 T인지를 확인하는 것이기 때문이다. 

이런 로직을 통하고 나면 아래와 같이 완성된다. 여기서 최종 답은 가장 우하단 셀의 값이다. 

|  -   |  0   |  1   |  2   |  3   |  4   |  5   |  6   |
| :--: | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
|  3   |  T   |  F   |  F   |  T   |  F   |  F   |  F   |
|  2   |  T   |  F   |  T   |  T   |  F   |  T   |  F   |
|  7   |  T   |  F   |  T   |  T   |  F   |  T   |  F   |
|  1   |  T   |  T   |  T   |  T   |  T   |  T   |  T   |



```typescript
// 이 코드의 시간 복잡도는 O(x*n)이며 x는 상수이기 때문에 결국 O(n)이 된다.

function isSubsetSumDP(arr: number[], n: number, x: number) {
  // 합이 x인 부분 집합이 존재하는지의 결과를 저장해둘 2차원 배열
  const cache = makeArray<boolean>(false, n + 1, x + 1);

  // 맨처음 열 true로 초기화
  for (let i = 0; i <= n; i++) cache[i][0] = true;

  for (let row = 1; row <= n; row++) {
    const value = arr[row - 1];

    for (let col = 1; col <= x; col++) {
      if (col < value) {
        cache[row][col] = cache[row - 1][col];
      } else {
        if (cache[row - 1][col]) {
          cache[row][col] = cache[row - 1][col];
        } else {
          cache[row][col] = cache[row - 1][col - value];
        }
      }
    }
  }
  return cache[n][x];
}
```



## 5.5 최장 공통 부분 수열 길이 구하기

문자열 x의 부분 수열(subsequence)은 문자열 내에서 왼쪽에서 오른쪽 방향으로 문자열 내에 들어 있는 글자의 수열이다. 단, 반드시 연속적인 글자들로 구성될 필요는 없다.

예를 들어 문자열 x가 `ACTTGCG` 라면 

- ACT, ATTC, ACTTCG 등은 X의 부분 수열이다.
- TTA는 X의 부분수열이 아니다.
- 길이가 n인 문자열의 부분 수열의 개수는 2^n이다. 부분 수열에는 빈 수열과 원래의 문자열도 포함된다.



길이가 n인 문자열의 부분 수열의 개수는 `2^n` 개이다. 부분 수열에는 빈 수열과 원래의 문자열도 포함된다.

두 문자열 x와 y가 주어졌을 때 x의 부분 수열이기도 하고 y의 부분 수열이기도 한 문자열을 x와 y의 **공통 부분 수열** (common subsequence)라고 하고, 공통 부분 수열중 가장 긴 부분 수열을 **최장 공통 부분 수열** (longest common subsequence LCS) 이라고 한다. 

두 문자열이 주어졌을 때 두 문자열의 최장 공통 부분 수열의 글자 수를 반환하는 함수를 작성해보자.

예를 들어 `AAACCGTGAGTTATTCGTTCTAGAA` 와 `CACCCCTAAGGTACCTTTGGTTC`에 대해서는 `ACCTAGTATTGTTC` 의 길이 14를 반환해야 한다. 만약 `ABCD` 와 `AEBD` 이라면 두 문자열의 LCS는 `ABD` 이므로 3을 반환해야 한다.

​        

### 재귀 호출을 사용하는 풀이와 설명

이 문제도 최적의 하위 구조를 가지고 있으며 같은 유형의 작은 문제로 큰 문제를 정의할 수 있다.

두 문자열의 가장 마지막 글자를 비교하며 시작한다. 마지막 글자를 비교할 때 2가지 경우가 있다. 두 문자열 A, B의 길이를 각각 m,n이라고 한다.

1. 두 글자가 같은 경우 : 이 경우 이 글자가 두 문자열의 LCS의 마지막 글자가 된다. 즉 LCS에 들어가는 글자 하나를 찾아냈다는 의미이다. 결과에 1을 더하고 양쪽 문자열에서 이 글자를 삭제한 문자열로 함수를 재귀 호출한다.

```
LCS_LENGTH('ABCD', 'AEBD') = 1 + LCS_LENGTH('ABC', 'AEB')
```

2. 두 글자가 다른 경우 : 이 경우는 다음 두 LCS의 길이를 구해서 이 중 큰 값을 반환한다.
   1. 문자열 A의 마지막 글자를 제외한 문자열(길이 m-1)과 문자열 B(길이 n)의 LCS
   2. 문자열 A(길이 m)와 문자열 B의 마지막 글자를 제외한 문자열 (길이 n-1) 의 LCS

```
LCS_LENGTH('ABCDE', 'AEBDF') = MAX(LCS_LENGTH('ABCDE', 'AEBD'),
                                   LCS_LENGTH('ABCD', 'AEBDF'))
```

```typescript
// 이 코드의 시간 복잡도는 지수 시간이다. 

function lcsLengthRecursive( x: string, y: string, m: number, n: number ): number {
  // 종료 조건은 두 문자열 중 하낙 ㅏ빈 문자열일 떄이며 이 때의 LCS_LENGTH = 0
  if (m === 0 || n === 0) return 0;

  // 문자열의 마지막 글자를 비교해 조건에 따라 재귀 호출한다.
  if (x[m - 1] === y[n - 1]) return 1 + lcsLengthRecursive(x, y, m - 1, n - 1);
  else {
    return Math.max(
      lcsLengthRecursive(x, y, m, n - 1),
      lcsLengthRecursive(x, y, m - 1, n)
    );
  }
}

```

​                   

### 메모 전략을 사용한 풀이와 설명

하위 문제의 반복 계산을 피하려면 메모 전략이나 다이나믹 프로그래밍을 사용할 수 있다.

문자열 x의 첫 i 글자와 문자열 y의 첫 j글자의 LCS_LENGTH를 처음 계산할 때 이 값을 `LCSLTABLE[i][j]` 에 저장한다. 재귀 호출 과정을 이어나가다 다시 i와 j값으로 재귀 함수가 호출되면 LCS_LENGTH를 다시 계산하지 않고 캐시에서 저장된 `LCSLTable[i][j]` 의 값을 반환한다.

```typescript
function lcsLengthMemo( x: string, y: string, m: number, n: number ): number {
  const LCSLTable = makeArray(-1, m + 1, n + 1);

  function _lcsLengthMemo(m: number, n: number): number {
    // 종료 조건은 두 문자열 중 하나가 빈 문자열일 때이며 이 때의 LCS_LENGTH = 0
    if (m == 0 || n == 0) return 0;

    // 이미 캐시에 계산된 값이 있다면 캐시의 값을 반환한다. (!== -1)
    if (LCSLTable[m][n] !== -1) return LCSLTable[m][n];

    // 문자열의 마지막 글자를 비교에 조건에 따라 재귀 호출한다.
    if (x[m - 1] === y[n - 1])
      LCSLTable[m][n] = 1 + _lcsLengthMemo(m - 1, n - 1);
    else
      LCSLTable[m][n] = Math.max(
        _lcsLengthMemo(m, n - 1),
        _lcsLengthMemo(m - 1, n)
      );

    return LCSLTable[m][n];
  }
  return _lcsLengthMemo(m, n);
}

```

​            

### 다이나믹 프로그래밍을 사용하는 풀이와 설명

LCSLTable 배열을 재귀 호출 없이 상향식으로 채워나간다. 

```
// 추가된 글자가 같은 글자면 LCS에 이 글자를 추가할 수 있다.
IF(X[i-1] == Y[j-1])
    LCSLTable[i][j] = LCSLTable[i-1][j-1]+1

// 추가된 글자가 다른 글자면 LCS는 변하지 않는다.
// 따라서 위쪽 칸이나 왼쪽 칸의 값 중 큰 값이 유지된다.
ELSE
    LCSLTable[i][j] = MAX(LCSLTable[i-1][j], LCSLTable[i][j-1])
```



행렬을 모두 채우면 다음 그림과 같은 값이 되며 가장 우하단 셀의 값이 구하고자 하는 값이다.

|  -   |  ∅   |  A   |  E   |  B   |  D   |
| :--: | :--: | :--: | :--: | :--: | :--: |
|  ∅   |  0   |  0   |  0   |  0   |  0   |
|  A   |  0   |  1   |  1   |  1   |  1   |
|  B   |  0   |  1   |  1   |  2   |  2   |
|  C   |  0   |  1   |  1   |  2   |  2   |
|  D   |  0   |  1   |  1   |  2   |  3   |





```typescript
function lcsLengthDP(x: string, y: string): number {
  const m = x.length;
  const n = y.length;

  const cache = makeArray<number>(0, m + 1, n + 1);

  for (let cacheXIdx = 1; cacheXIdx <= m; cacheXIdx++) {
    const xOne = x[cacheXIdx - 1];

    for (let cacheYIdx = 1; cacheYIdx <= n; cacheYIdx++) {
      const yOne = y[cacheYIdx - 1];
      
      if (xOne === yOne) {
        cache[cacheXIdx][cacheYIdx] = cache[cacheXIdx - 1][cacheYIdx - 1] + 1;
      } else {
        cache[cacheXIdx][cacheYIdx] = Math.max(
          cache[cacheXIdx - 1][cacheYIdx],
          cache[cacheXIdx][cacheYIdx - 1]
        );
      }
    }
  }
  return cache[m][n];
}
```

​                   

## 5.6 최장 공통 부분 수열 출력하기

앞 절의 최장 공통 부분 수열 예제를 확장한 예제를 더 보자. 예를 들어 `ABCD`, `AEBD` 일 때 함수가 `ABD` 를 출력해야 한다.

앞의 절에서 셀에 값이 채워지는 경우를 보면 다음 3가지이다.

1. 왼쪽 셀과 같은 값
2. 위쪽 셀과 같은 값
3. 왼쪽 위 셀의 값 + 1

1,2 번의 경우 LCS의 변화가 없다. 대각선 방향인 3번의 경우에만 LCS 글자가 변화가 있었음을 알 수 있다. 행렬에 대응하는 배열을 완성하고 난 후에 우하단 셀에서 시작해서 좌상단 행까지 경로를 따라 이동하면서 3번의 경우에 해당되는 글자를 하나씩 추가해 간다. 이 과정을 통해서 수열을 얻을 수 있다.

```typescript
function lcsTextDPBook(x: string, y: string): string {
  // lcr 관련 배열을 받는다.
  const lcsArr = makeLcsArr(x, y);
  
  // 우하단부터 시작한다.
  let rowIdx = x.length;
  let colIdx = y.length;

  // 결과를 받을 변수이다.
  let result = "";

  // 첫번째 행 또는 첫번째 열까지 거슬러 올라간다.
  while (rowIdx > 0 && colIdx > 0) {
    
    // ㅌㅌ와 y의 현재의 글자가 같으면 result에 포함시킨다.
    if (x[rowIdx - 1] === y[colIdx - 1]) {
      result = x[rowIdx - 1] + result;
      rowIdx--;
      colIdx--;
    } else {
      // 같지 않다면 위쪽 또느 왼쪽 셀의 값 중 큰 값을 따라 이동한다.
      if (lcsArr[rowIdx - 1][colIdx] > lcsArr[rowIdx][colIdx - 1]) {
        rowIdx--;
      } else {
        colIdx--;
      }
    }
  }
  
  // lcr 수열 (문자열) 반환
  return result;
}
```

