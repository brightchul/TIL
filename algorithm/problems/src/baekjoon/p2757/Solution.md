# 백준 온라인 저지 : 엑셀

## 문제 설명 [[링크]](https://www.acmicpc.net/problem/2757)

엑셀의 첫 번째 열은 A이고, 두 번째 열은 B이고, 26번째 열은 Z이다. 26번째 열 다음 열부터는 2글자를 이용한다. 

예를 들어, 27번째 열은 AA이고, 28번째 열은 AB, 52번째 열은 AZ이다. 그 다음 53번째 열은 BA이며, 이와 같이 계속 열의 이름을 붙인다.

ZZ열 다음 열은 AAA가 되고, 그 다음은 AAB가 된다.

엑셀에서 행은 그냥 행 번호를 사용하면 된다.

엑셀 스프레드시트에서 각 칸은 위에서 설명한 열과 행을 합쳐서 이름을 만들 수 있다. 가장 왼쪽 위에 있는 칸은 A1이 되고, 55열 23행에 있는 칸은 BC23이 된다.

열과 행이 주어졌을 때, 그 칸의 엑셀 스프레드시트 상에서 이름을 출력하는 프로그램을 작성하시오.


```
[입력]
입력은 여러 줄이며, RnCm형태이다. n은 행 번호 (1<=n<=300000000), m은 열 번호 (1<=m<=300000000) 이다. 
입력의 마지막은 n과 m이 모두 0이며, 이때는 출력하지 않고 프로그램을 종료하면 된다.

[출력]
각 입력을 순서대로 한 줄에 하나씩 엑셀 스프레드시트 상에서의 이름을 출력하면 된다.

[예시]
R1C1
R3C1
R1C3
R299999999C26
R52C52
R53C17576
R53C17602
R0C0

return
A1
A3
C1
Z299999999
AZ52
YYZ53
YZZ53
```



## 문제풀이

정수값을 적절히 변환시키는 문제이다. 알파벳은 총 26개이므로 26진법 이라고 할수도 있겠으나 그렇게 접근하면 틀린다. 살짝 다르기 때문이다.

예를 들어 1~10까지의 십진법이라고 가정해 보자. 

```
십진법의 수 진행
1 2 3 4 5 6 7 8 9 10 11 12 13 14 ...
```
하지만 엑셀에서의 열은 아래와 같이 진행된다고 보면 된다. 그저 숫자 대신 알파벳인 점이 다르다.

```
문제에서의 열 진행
1 2 3 4 5 6 7 8 9 11 12 13 14 15 ...
```

따라서 진법 변환이라고 생각하고 계산하게 되면 1씩 값이 더해져서 계산되는 상황을 겪게된다. 이것을 방지하기 위해서는 각 연산을 할때 마다 1을 빼주고 진행하면 된다.

​    


## 코드 구현 [[전체 코드]](./Main.java)

```java
public static String solution(int row, int col) {
    StringBuilder sb = new StringBuilder();

    while (col > 0) {
        sb.append(ALPHA.charAt((col - 1) % 26));
        col = (col - 1) / 26;
    }
    sb.reverse();		// 1 2 3 을 3 2 1로 저장되었을테니 리버스 해준다.
    sb.append(row);
    return sb.toString();
}
```


