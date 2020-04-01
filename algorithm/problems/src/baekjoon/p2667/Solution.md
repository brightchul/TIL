# 백준 온라인 저지 : 단지번호붙이기

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/2667)

정사각형 모양의 지도가 있다. 1은 집이 있는 곳을, 0은 집이 없는 곳을 나타낸다. 철수는 이 지도를 가지고 연결된 집들의 모임인 단지를 정의하고, 단지에 번호를 붙이려 한다. 여기서 연결되었다는 것은 어떤 집이 좌우, 혹은 아래위로 다른 집이 있는 경우를 말한다. 대각선상에 집이 있는 경우는 연결된 것이 아니다

지도를 입력하여 단지수를 출력하고, 각 단지에 속하는 집의 수를 오름차순으로 정렬하여 출력하는 프로그램을 작성하시오.

```
[입력]
첫 번째 줄에는 지도의 크기 N(정사각형이므로 가로와 세로의 크기는 같으며 5≤N≤25)이 입력되고, 
그 다음 N줄에는 각각 N개의 자료(0혹은 1)가 입력된다.




[출력]
첫 번째 줄에는 총 단지수를 출력하시오. 그리고 각 단지내 집의 수를 오름차순으로 정렬하여 
한 줄에 하나씩 출력하시오.



[예시]
7
0110100
0110101
1110101
0000111
0100000
0111110
0111000


3
7
8
9

```



​    

## 문제풀이

입력값 크기가 5~25이므로 N^2이 되더라도 25 ~ 625 가 된다. 따라서 부담없이 전체를 순회하면서 맵의 영역들을 배열 캐시로 채워 나가면 된다. 만약 캐시가 안되어 있으면 채우면되고 캐시가 되어있으면 다음 값으로 넘어가면 된다.



​    

## 코드 구현 [[전체 코드]](./Main.java)

입력 배열을 x, y 좌표에 따라서 하나씩 순회하면서 0이면 그냥 넘어가고 1이면 캐시를 확인해서 캐시가 되어 있으면 그냥 넘어가고 아니면 재귀호출로 체크하는 한다.

```java
public static int run(char[][] board, int[][] cache, int len) {
    int count = 0;
    for(int y=0; y < len; y++) {
        for(int x=0; x<len; x++) {
            if(board[y][x] == '0') continue;
            if(cache[y][x] > 0) continue;
            count++;
            recur(x, y, count, board, cache);
        }
    }
    return count;
}
```



재귀호출내에서는 일단 x,y 좌표값이 배열을 넘어갔는지 확인한 다음에 값이 1이고 캐시가 안되어 있으면 캐시에 입력하고 그 다음 계속 4방향으로 다시 재귀호출을 한다.

```java
public static void recur(int x, int y, int count, char[][] board, int[][] cache) {
    if(x < 0 || y < 0) return;
    if(x >= board.length || y >= board.length) return;
    if(board[y][x] == '0'|| cache[y][x] > 0) return;

    cache[y][x] = count;
    countArr[count]++;

    recur(x-1, y, count, board, cache);
    recur(x, y-1, count, board, cache);
    recur(x+1, y, count, board, cache);
    recur(x, y+1, count, board, cache);
}
```



