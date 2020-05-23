# 백준 온라인 저지 : 거듭제곱

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/1806)

3의 제곱수를 생각하자. 3의 0제곱, 3의 1제곱, 3의 2제곱, ... 은 순서대로 1, 3, 9, 27, ... 이 된다.
이를 바탕으로, 한 개 이상의 서로 다른 3의 제곱수의 합으로 표현되는 수를 생각할 수 있다. 예를 들어 30은 27과 3의 합이므로, 이러한 수에 포함된다. 한 개 이상의 서로 다른 3의 제곱수의 합으로 표현되는 N번째로 작은 수를 구하는 프로그램을 작성하시오.



```
[입력]
첫째 줄에 N이 주어진다. N은 500,000,000,000 이하의 자연수이다.

[출력]
첫째 줄에 한 개 이상의 서로 다른 3의 제곱수의 합으로 표현되는 N번째로 작은 수를 출력한다.

[예시]
4

return 9
```





## 문제풀이

숫자들이 1, 3, 9, 27 이런식으로 사용이 된다. 이 수들이 1+3, 3+27, 1+3+9+27  등으로  조합이 이루어 진다. 이것을 보면 2진수의 그것과 비슷하다. 1, 2, 4, 8, ..... 이런식으로 나온다. 여기서 3으로 바꾸기만 하면 되는 것이다. 

n번째 수를 2진수로 바꾸면 몇번째수에 대해서 n승한 값들이 각각 쓰이는지 아닌지를 알 수 있다. 여기에 1인 값들에 대해서 자릿수를 포함해서 3의 n승의 합으로 만들면 간단하게 문제는 풀린다.



## 코드 구현

```java
// N은 500,000,000,000 이하의 자연수라 했기 때문에 long을 써야 한다.

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        long n = Long.parseLong(br.readLine());
        System.out.println(solution(n));
    }
    public static long solution(long n) {
        String binaryN = Long.toBinaryString(n);
        return Long.parseLong(binaryN, 3);
    }
}

```

