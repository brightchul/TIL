# 백준 온라인 저지 : 가장 큰 정사각형

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/1912)

n×m의 0, 1로 된 배열이 있다. 이 배열에서 1로 된 가장 큰 정사각형의 크기를 구하는 프로그램을 작성하시오.

```
0 1 0 0
0 1 1 1
1 1 1 0
0 0 1 0
```

위와 같은 예제에서는 가운데의 2×2 배열이 가장 큰 정사각형이다. 

​    


```
[입력]
첫째 줄에 n, m(1 ≤ n, m ≤ 1,000)이 주어진다. 다음 n개의 줄에는 m개의 숫자로 배열이 주어진다.

[출력]
첫째 줄에 가장 큰 정사각형의 넓이를 출력한다.

[예시]
4 4
0100
0111
1110
0010

return 4
```



​    

## 문제풀이

DP를 이용하면 된다.

처음에 풀 때에는 4칸이 0이 아닌 것을 확인한 다음에 확인이 되면 현재 r, c 좌표에 나머지 3칸중 최소 값 + 1을 해서 계속 누적해 나가면서 배열을 배웠다. 그 다음 결과 값에서 그중 가장 최대값을 제곱해서 결과값을 반환하면 답이 나온다.



​    

## 코드 구현 [[전체 코드]](./Main.java)

`0,0` 부터 시작하면 열, 행의 -1값에 대해서 체크 로직을 해야 하기 때문에 `1,1`부터 했지만 그럴 경우 0행, 0열에만 1이 있을 경우 이것을 제대로 반영하지 못했다. 그래서 이 부분을 반영하기 위해서 for문을 2번 추가했다.

```java

public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int row, col;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        row = Integer.parseInt(st.nextToken());
        col = Integer.parseInt(st.nextToken());

        int[][] arr = new int[row][col];

        for (int r = 0; r < row; r++) {
            String a = br.readLine();
            for (int c = 0; c < col; c++) {
                arr[r][c] = a.charAt(c) - '0';
            }
        }

        int result = Integer.MIN_VALUE;

        // 가장 첫번째 행 체크
        for(int c=0; c<col; c++) {
            result = Math.max(result, arr[0][c]);
        }
        // 가장 첫번째 열체크
        for(int r=0; r<row; r++) {
            result = Math.max(result, arr[r][0]);
        }

        // 그외 체크
        for (int r = 1; r < row; r++) {
            for (int c = 1; c < col; c++) {
                boolean flag = true;
                int min = Integer.MAX_VALUE;

                if(arr[r][c]==0) continue;

                if(arr[r-1][c-1] == 0) continue;
                min = Math.min(min,arr[r-1][c-1]);

                if(arr[r-1][c] == 0) continue;
                min = Math.min(min,arr[r-1][c]);

                if(arr[r][c-1] == 0) continue;
                min = Math.min(min,arr[r][c-1]);

                arr[r][c] = min + 1;
                result = Math.max(result, arr[r][c]);
            }
        }
        System.out.println(result * result);
    }
}

```

​    

#### 개선된 코드

푼 다음에 다른 분들의 코드를 확인해 봤더니 더 간단하고 좋은 코드들이 보였다. 그래서 참고 해서 개선하였다.

먼저 row, col을 각각 +1씩을 해서 `1,1`부터 시작한다. 이렇게 하면 -1씩 해도 인덱스0을 넘어갈 일이 없다. 그리고 r, c의 값이 1인지 확인한 다음에, Math.min을 이용해서 if문 + Math.min을 단축시킨다. 만약 0이라면 0이 들어갈 것이고 아니면 그보다 클 것이기 때문이다. 

어차피 r,c 값이 1이기 때문에 Math.min을 이용해서 주변 3개의 값이 전부 0이라고 해도 +1을 하기 때문에 그대로 적용되며, 전부 1이상이면 그 최소값으로 반영되어서 정상적으로 로직이 진행된다.

```java
public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    row = Integer.parseInt(st.nextToken());
    col = Integer.parseInt(st.nextToken());

    int[][] arr = new int[row+1][col+1];
    int result = Integer.MIN_VALUE;

    for (int r = 1; r <= row; r++) {
        char[] inputArr = br.readLine().toCharArray();

        for (int c = 1; c <= col; c++) {
            if(inputArr[c-1] == '0') continue;
            
            arr[r][c] = Math.min(arr[r-1][c-1], Math.min(arr[r][c-1], arr[r-1][c]))+1;
            result = Math.max(result, arr[r][c]);
        }
    }
    System.out.println(result * result);
}
```



