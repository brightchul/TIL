# 백준 온라인 저지 : 자두나무

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/2240)

자두는 자두를 좋아한다. 그래서 집에 자두나무를 심어두고, 여기서 열리는 자두를 먹고는 한다. 하지만 자두는 키가 작아서 자두를 따먹지는 못하고, 자두가 떨어질 때까지 기다린 다음에 떨어지는 자두를 받아서 먹고는 한다. 자두를 잡을 때에는 자두가 허공에 있을 때 잡아야 하는데, 이는 자두가 말랑말랑하여 바닥에 떨어지면 못 먹을 정도로 뭉개지기 때문이다.

매 초마다, 두 개의 나무 중 하나의 나무에서 열매가 떨어지게 된다. 만약 열매가 떨어지는 순간, 자두가 그 나무의 아래에 서 있으면 자두는 그 열매를 받아먹을 수 있다. 두 개의 나무는 그다지 멀리 떨어져 있지 않기 때문에, 자두는 하나의 나무 아래에 서 있다가 다른 나무 아래로 빠르게(1초보다 훨씬 짧은 시간에) 움직일 수 있다. 하지만 자두는 체력이 그다지 좋지 못해서 많이 움직일 수는 없다.

자두는 T(1≤T≤1,000)초 동안 떨어지게 된다. 자두는 최대 W(1≤W≤30)번만 움직이고 싶어 한다. 매 초마다 어느 나무에서 자두가 떨어질지에 대한 정보가 주어졌을 때, 자두가 받을 수 있는 자두의 개수를 구해내는 프로그램을 작성하시오. 자두는 1번 자두나무 아래에 위치해 있다고 한다.



```
[입력]
첫째 줄에 두 정수 T, W가 주어진다. 다음 T개의 줄에는 각 순간에 자두가 떨어지는
나무의 번호가 1 또는 2로 주어진다.



[출력]
첫째 줄에 자두가 받을 수 있는 자두의 최대 개수를 출력한다.



[예시1]
7 2
2
1
1
2
2
1
1

return
6

[예시2]
7 2
1
2
2
2
2
2
2

return
7

```





## 문제풀이

DP 문제이다. 

나무의 개수가 1,2 즉 2개가 있다. 따라서 이전 인덱스에서의 값을 참고해서 가져오면 된다. 좀더 단순하게 접근하기 위해서 먼저 점프 횟수가 0일때는 생각해 보자.

​       

#### 점프 횟수 0번

이 때는 나무를 이동할 수가 없다. 따라서 1번 나무에만 그대로 있는다.

​     

#### 점프 횟수 1번

이 때는 나무를 1번 이동할 수가 있다. 이전 초에서 0번 이동했을 때와 1번 이동했을 때의 값을 가져오면 된다. 

예를 들어 2초가 있을 때 1초 시점에서 1번 나무 (0번 이동)에 있을 때와 2번 나무(1번 이동) 했을 때의 값을 가져와서 그 중 큰 값을 확인한다. 그리고 2초 시점에서 몇번 나무에 떨어지는지를 확인한 다음에 그 나무와 현재 횟수가 대응이 되면 +1을 해주고 아니면 +0을 해준 다음에 캐시 배열에 넣는다.

나무가 2개 밖에 없기 때문에 이동을 했을때 1번인 경우 2번으로, 2번인 경우 1번으로만 이동이 가능하다. 따라서 짝수 횟수로 이동할 때에는 1번 나무가, 그외 홀수횟수로 이동할 때에는 2번 나무에 가있게 되는 것이다. 



#### 주의할 점

1. 0번 ~ w 횟수 중 가장 큰 값을 가져와야 한다. 반드시 w번 이동한 값이 가장 큰 것은 아니다.
2. 1초가 걸리지 않는 이동 횟수 때문에 제일 첫번째 떨어지는 것부터 0~w번 이동과 상관 없이 동일하게 적용이 가능하다. 즉 1번 나무에 열매가 떨어질때 0번, 2번, 4번.. 등이 전부다 카운트가 되는 것이다. 



## 코드 구현 [[전체코드]](./Main.java)

```java
private static int[][] cache;

public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    int len = Integer.parseInt(st.nextToken());
    int jumpCount = Integer.parseInt(st.nextToken());
    cache = new int[jumpCount + 1][len];

    for (int i = 0; i < len; i++) {
        int curTree = Integer.parseInt(br.readLine());

        for (int j = 0; j <= jumpCount; j++) {
            int tree = j % 2 == 0 ? 1 : 2;

            cache[j][i] = Math.max(
                getValue(j, i - 1), getValue(j - 1, i - 1)
            ) + (tree == curTree ? 1 : 0);
        }
    }

    int result = 0;
    for (int i = 0; i <= jumpCount; i++) {
        result = Math.max(result, cache[i][len - 1]);
    }
    System.out.println(result);
}
```
