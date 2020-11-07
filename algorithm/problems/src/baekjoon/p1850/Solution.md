# 백준 온라인 저지 : 최대공약수

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/1850)

모든 자리가 1로만 이루어져있는 두 자연수 A와 B가 주어진다. 이때, A와 B의 최대 공약수를 구하는 프로그램을 작성하시오.

예를 들어, A가 111이고, B가 1111인 경우에 A와 B의 최대공약수는 1이고, A가 111이고, B가 111111인 경우에는 최대공약수가 111이다.

```
[입력]
첫째 줄에 두 자연수 A와 B를 이루는 1의 개수가 주어진다. 입력되는 수는 2^63보다 작은 자연수이다.

[출력]
첫째 줄에 A와 B의 최대공약수를 출력한다. 정답은 천만 자리를 넘지 않는다.

[예시]
3 4
return 1

3 6
return 111

500000000000000000 500000000000000002
return 11
```





## 문제풀이

입력으로 들어오는 값들이 최대공약수를 구할 대상 숫자 값이 아니라 숫자 값의 1의 갯수라는 점을 지나치면 안된다. 즉 여기서 2^63이라는 것은 2^63자리를 가진 `111 ..... 11` 라는 숫자 값 이라는 것이다. 자바의 long은 2^63, 즉 18자리에 불과하다. 그렇기에 별생각 없이 이것을 `111 ... 111` 로 파싱해서 구하려고 하면 런타임 에러가 나온다. 

`111 ... 111` 숫자의 특성을 잘 보면 일종의 타협이 없는 숫자라는 것을 알 수 있다. 즉 각각의 숫자값들의 자릿수가 서로 약수관계가 있어야만 나눠질 수가 있다는 것이다. 따라서 각 숫자값들의 자릿수를 가지고 최대 공약수를 구하면 그게 답이 된다.

문제를 보면 결과값이 천만자리 이하라고 되어 있다. 따라서 String의 repeat메서드를 사용할 수 잇다. (인자로 int만 받는다.)




## 코드 구현 [[전체 코드]](./Main.java)

```java
public static void main(String[] args) throws IOException {
    StringTokenizer st = new StringTokenizer(br.readLine(), " ");
    long one = Long.parseLong(st.nextToken());
    long two = Long.parseLong(st.nextToken());

    long big = Math.max(one, two);
    long small = Math.min(one, two);
    long temp;

    while (true) {
        temp = big % small;
        
        // 아무리 안나눠지는 값이라도 1에서는 나머지가 0이므로 멈추게 된다.
        if(temp == 0) {	
            System.out.println("1".repeat((int)small));
            break;
        }
        big = small;
        small = temp;
    }
}
```

