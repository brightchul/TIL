# 프로그래머스 : 소수 찾기

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42839)

```
한자리 숫자가 적힌 종이 조각이 흩어져있습니다. 흩어진 종이 조각을 붙여 소수를 몇 개 
만들 수 있는지 알아내려 합니다. 각 종이 조각에 적힌 숫자가 적힌 문자열 numbers가 
주어졌을 때, 종이 조각으로 만들 수 있는 소수가 몇 개인지 알수 있는 함수를 만드세요.


[제한사항]
- numbers는 길이 1 이상 7 이하인 문자열입니다.
- numbers는 0~9까지 숫자만으로 이루어져 있습니다.
- 013은 0, 1, 3 숫자가 적힌 종이 조각이 흩어져있다는 의미입니다.

[입출력]
"17" return 3
"011" return 2
```



## 문제 풀이

문제는 크게 2가지로 나뉜다.

1. 숫자문자열에 대해서 가능한 모든 솟자들을 가진 배열을 생성하기
2. 소수 배열을 생성한 다음에 카운트 하기 (에라토스테네스의 체 활용)



## 코드 구현 [[전체코드]](./solution.java)

가능한 숫자들을 생성하는 코드이다. 아래코드는 일부 숫자문자열을 들고와서 붙이는 것을 번갈아 하면서 숫자를 생성하게 했다. `"12" + "3" & "3" + "12"` 이런식으로 하나의 recursive 메서드 안에서 생성한다.  `"99" + "9" & "9" + "99"` 의 경우에는 `"999"` 같기 때문에 이 경우에는 재귀호출이 되지 않도록 했다.

```java
private void recursive(String numbers, String accNumStr) {
    if(numbers.length() == 0) return;
    int len = numbers.length();
    for(int i=0; i<len; i++) {
        char one = numbers.charAt(i);
        String nextNumbers = 
            numbers.substring(0, i) + numbers.substring(i+1);

        String newAccNumStr1 = one + accNumStr;
        numSet.add(Integer.parseInt(newAccNumStr1));
        recursive(nextNumbers, newAccNumStr1);

        String newAccNumStr2 = accNumStr + one;
        if(newAccNumStr1.equals(newAccNumStr2)) continue;
        numSet.add(Integer.parseInt(newAccNumStr2));
        recursive(nextNumbers, newAccNumStr2);
    }
}
```

소수 생성은 에라토스테네스의 체를 이용해서 했다. 

```java
private int[] makePrimeArr(int maxValue) {
    int limit = (int)Math.sqrt(maxValue);
    int[] arr = new int[maxValue+1];

    arr[0] = arr[1] = 0;
    for(int i=2; i<arr.length; i++) arr[i] = 1;

    for(int i=2; i<=limit; i++) {
        for(int idx = i*i; idx<=maxValue; idx+=i) {
            arr[idx] = 0;
        }
    }
    return arr;
}
```

