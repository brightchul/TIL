# 프로그래머스 : 소수 찾기

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/62049)

```
직사각형 종이를 n번 접으려고 합니다. 이때, 항상 오른쪽 절반을 왼쪽으로 접어 나갑니다.
종이를 모두 접은 후에는 종이를 전부 펼칩니다. 종이를 펼칠 때는 종이를 접은 방법의 
역순으로 펼쳐서 처음 놓여있던 때와 같은 상태가 되도록 합니다. 
종이를 펼치면 접은 흔적이 생기게 됩니다.

종이를 접은 횟수 n이 매개변수로 주어질 때, 종이를 절반씩 n번 접은 후 모두 펼쳤을 때 
생기는 접힌 부분의 모양을 배열에 담아 return 하도록 solution 함수를 완성해주세요.

좀더 자세한 설명은 링크를 참조하세요.

[제한사항]
- 종이를 접는 횟수 n은 1 이상 20 이하의 자연수입니다.
- 종이를 접었다 편 후 생긴 굴곡이 ∨ 모양이면 0, ∧ 모양이면 1로 나타냅니다.
- 가장 왼쪽의 굴곡 모양부터 순서대로 배열에 담아 return 해주세요.

[입출력]
1 return [0]
2 return [0, 0, 1]
2 return [0, 0, 1, 0, 0, 1, 1]
```



## 문제 풀이

패턴을 찾으면 쉽게 풀리는 문제이다.

```
1 => [0]
2 => [0, 0, 1]
3 => [0, 0, 1, 0, 0, 1, 1]
4 => [0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1]
```

일단 전체 개수가 어떻게 증가하는지 보자면 각각 1, 3, 7, 15로 증가하는게 보인다. 이것은 `2^n -1` 이다.  그다음 패턴을 찾아보자.

```
0
0, [0], 1
0, [0], 1, [[0]], 0, [1], 1
0, [0], 1, [[0]], 0, [1], 1, [[[0]]], 0, [0], 1, [[1]], 0, [1], 1
```

이것을 보면 왼편은 0, 오른편은 1로 뻗어나가는 이진트리를 연상할 수 있다. 

![](./1.PNG)

즉 깊이 우선 탐색 & 중위 순회로 하면 답이 나온다.



## 코드 구현

```java
public class Solution {
    private static int idx = 0;
    public int[] solution(int n) {
        idx = 0;
        int len = (int)(Math.pow(2, n)-1);
        int[] arr = new int[len];
        _solution(0, 1, n, arr);
        return arr;
    }
    private void _solution(int cValue, int cHeight, int tHeight, int[] arr) {
        if(cHeight > tHeight) return;
        _solution(0, cHeight+1, tHeight, arr);
        arr[idx++] = cValue;
        _solution(1, cHeight+1, tHeight, arr);
    }
}

```

