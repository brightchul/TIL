# 백준 온라인 저지 : 대표 자연수

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/2548)

정보초등학교의 연아는 여러 개의 자연수가 주어졌을 때, 이를 대표할 수 있는 대표 자연수에 대하여 연구하였다. 그 결과 어떤 자연수가 다음과 같은 성질을 가지면 대표 자연수로 적당할 것이라고 판단하였다.

“대표 자연수는 주어진 모든 자연수들에 대하여 그 차이를 계산하여 그 차이들 전체의 합을 최소로 하는 자연수이다.”

예를 들어 주어진 자연수들이 [4, 3, 2, 2, 9, 10]이라 하자. 이때 대표 자연수는 3 혹은 4가 된다. 왜냐하면 (4와 3의 차이) + (3과 3의 차이) + (2와 3의 차이) + (2와 3의 차이) + (9와 3의 차이) + (10과 3의 차이) = 1+0+1+1+6+7 = 16이고, (4와 4의 차이) + (3과 4의 차이) + (2와 4의 차이) + (2와 4의 차이) + (9와 4의 차이) + (10과 4의 차이) = 0+1+2+2+5+6 = 16으로 같으며, 이 두 경우가 차이들의 합을 최소로 하기 때문이다. 비교를 위하여 평균값인 5의 경우를 생각하여 보면, (4와 5의 차이) + (3과 5의 차이) + (2와 5의 차이) + (2와 5의 차이) + (9와 5의 차이) + (10과 5의 차이) = 1+2+3+3+4+5 = 18로 위의 두 경우보다 차이들의 합이 더 커짐을 볼 수 있다.

연아를 도와서 위의 성질을 만족하는 대표 자연수를 구하는 프로그램을 작성하시오.

```
[입력]
첫째 줄에는 자연수의 개수 N이 입력된다. N은 1 이상 20,000 이하이다. 둘째 줄에는 N개의 자연수가 빈칸을 사이에 두고 입력되며, 이 수들은 모두 1 이상 10,000 이하이다.

[출력]
첫째 줄에 대표 자연수를 출력한다. 대표 자연수가 두 개 이상일 경우 그 중 제일 작은 것을 출력한다.

[예시]
6
4 3 2 2 9 10

return
3
```



## 문제풀이

N이 20,000개 이하이므로 브루트포스로 풀면 된다. 하지만 풀고 나서 다른 분들의 코드를 봤더니 한가지를 알게 되었다. 

이 값은 사실상 **중앙값(Median)**인 것이다. 따라서 정렬한 다음에 배열의 가운데값을 반환해 주면 더 빠르게 처리가 가능하다.



## 코드 구현

코드는 중앙값으로 구한 버전이다.

```java
public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int len = Integer.parseInt(br.readLine()), idx = 0;
        int[] arr = new int[len];
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        while (idx < len) {
            arr[idx++] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        int mid = len / 2;
        bw.write(arr[mid - (len % 2 ^ 1)] + "");	// if문을 줄이기 위해 XOR을 이용
        bw.flush();
    }
}

```





