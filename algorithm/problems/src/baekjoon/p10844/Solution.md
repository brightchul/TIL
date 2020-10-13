# 백준 온라인 저지 : 쉬운 계단 수

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/10844)

45656이란 수를 보자. 이 수는 인접한 모든 자릿수의 차이가 1이 난다. 이런 수를 계단 수라고 한다. 

세준이는 수의 길이가 N인 계단 수가 몇 개 있는지 궁금해졌다. N이 주어질 때, 길이가 N인 계단 수가 총 몇 개 있는지 구하는 프로그램을 작성하시오. (0으로 시작하는 수는 없다.)

```
[입력]
첫째 줄에 N이 주어진다. N은 1보다 크거나 같고, 100보다 작거나 같은 자연수이다.

[출력]
첫째 줄에 정답을 1,000,000,000으로 나눈 나머지를 출력한다.

[예시]
1

return 9

2

return 17
```

​      

## 문제풀이

동적계획법을 이용해서 풀면 된다. 1,2,3정도 죽 써보면서 패턴을 파악해 보면 좋다.

```
[1]
1
2
3
4
5
6
7
8
9

[2]

1 0
1 2

2 1
2 3

3 2
3 4

...

8 7
8 9

9 8

[3]
1 0 1
1 2 1
1 2 3

2 1 0
2 1 2
2 3 2
2 3 4

...

8 7 6
8 7 8
8 9 8

9 8 7
9 8 9
```



이렇게 보면 각 자릿수의 값들이 이전 자릿수의 값들을 가지고 온다는 것을 알 수 있다. (단, 1과 9만 제외)

```
1 0
1 2

3 2
3 4

2 1 0
2 1 2
2 3 2
2 3 4
```

자릿수 9의 경우 이전 자릿수인 8만 하면 된다.

1의 경우는 살짝 더 생각해 봐야 하는데 2는 그냥 하면 된다. 하지만 0의 경우에는 0으로 시작하는 숫자는 없다. 하지만 0을 고려해야 한다. 만약 0이 적용된다면 그 다음 숫자는 1밖에 존재 하지 않는다. 따라서 현재 자리 N-2한 다음, 자릿수 1을 참고 하면 된다.

​    


## 코드 구현

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static final int DIV = 1_000_000_000;
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int len = Integer.parseInt(br.readLine());
        long[][] arr = new long[len + 1][10];
        Arrays.fill(arr[0], 1);
        Arrays.fill(arr[1], 1);

        for (int row = 2; row <= len; row++) {
            for (int col = 1; col < 10; col++) {
                switch (col) {
                    case 1:	// N자리의 -2만큼 이전으로 가서 1을 가져오고 2는 다른 것들과 동일하게 가져온다.
                        arr[row][col] = (arr[row - 2][col] + arr[row - 1][col + 1]) % DIV;
                        break;
                    case 9:	// 9는 8만 보면 된다
                        arr[row][col] = arr[row - 1][col - 1];
                        break;
                    default:
                        arr[row][col] = (arr[row - 1][col - 1] + arr[row - 1][col + 1]) % DIV;
                        break;
                }
            }
        }

        long result = 0;
        for (int i = 1; i < 10; i++) result += arr[len][i];

        System.out.println(result % DIV);
    }
}
```



