# 프로그래머스 : 추석 트래픽

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/17676)

이번 추석에도 시스템 장애가 없는 명절을 보내고 싶은 어피치는 서버를 증설해야 할지 고민이다. 장애 대비용 서버 증설 여부를 결정하기 위해 작년 추석 기간인 9월 15일 로그 데이터를 분석한 후 초당 최대 처리량을 계산해보기로 했다. **초당 최대 처리량**은 요청의 응답 완료 여부에 관계없이 임의 시간부터 1초(=1,000밀리초)간 처리하는 요청의 최대 개수를 의미한다.

​    

### 입력 형식

- `solution` 함수에 전달되는 `lines` 배열은 **N**(1 ≦ **N** ≦ 2,000)개의 로그 문자열로 되어 있으며, 각 로그 문자열마다 요청에 대한 응답완료시간 **S**와 처리시간 **T**가 공백으로 구분되어 있다.
- 응답완료시간 **S**는 작년 추석인 2016년 9월 15일만 포함하여 고정 길이 `2016-09-15 hh:mm:ss.sss` 형식으로 되어 있다.
- 처리시간 **T**는 `0.1s`, `0.312s`, `2s` 와 같이 최대 소수점 셋째 자리까지 기록하며 뒤에는 초 단위를 의미하는 `s`로 끝난다.
- 예를 들어, 로그 문자열 `2016-09-15 03:10:33.020 0.011s`은 2016년 9월 15일 오전 3시 10분 **33.010초**부터 2016년 9월 15일 오전 3시 10분 **33.020초**까지 **0.011초** 동안 처리된 요청을 의미한다. **(처리시간은 시작시간과 끝시간을 포함)**
- 서버에는 타임아웃이 3초로 적용되어 있기 때문에 처리시간은 **0.001 ≦ T ≦ 3.000**이다.
- `lines` 배열은 응답완료시간 **S**를 기준으로 오름차순 정렬되어 있다.

​    

### 출력 형식

- `solution` 함수에서는 로그 데이터 `lines` 배열에 대해 **초당 최대 처리량**을 리턴한다.

```
[예시]

입력: 
[2016-09-15 01:00:04.001 2.0s,
 2016-09-15 01:00:07.000 2s]

출력: 1
```

​    

## 문제 풀이

[[카카오 Tech 풀이]](https://tech.kakao.com/2017/09/27/kakao-blind-recruitment-round-1/#7-%EC%B6%94%EC%84%9D-%ED%8A%B8%EB%9E%98%ED%94%BD%EB%82%9C%EC%9D%B4%EB%8F%84-%EC%83%81) 에도 이미 잘 나와 있지만, 1ms씩 체크를 해서는 답이 나오질 않는다. 대신 각 로그별 시작, 끝 시간을 배열로 만든 다음 각 시간에서 -1s으로 구간을 만든 다음 체크를 하면 쉽게 값이 나온다. 포인트는 아래와 같다.

1. 문자열로 들어온 시간을 숫자로 변환
2. 각 시작, 끝 시간을 1초 구간으로 해서 확인

​    

## 코드 구현  [[전체코드]](./Solution.java)

### 1\. 문자열로 들어온 시간을 숫자로 변환

문자를 숫자로 변환할때 문제자체가 0.001초까지 들어오기 때문에 ms 단위로 변환했다. 그리고 Float.parseFloat를 하게 될 경우 이후 1000 등을 곱할 때 정확도 문제가 발생한다. 따라서 Double.parseDouble를 써줘야 한다.

```java
public int convertTimeStringToMs(String timeString) {
    return Integer.parseInt(timeString.substring(0, 2)) * 3600000
        + Integer.parseInt(timeString.substring(3, 5)) * 60000
        + (int) (Double.parseDouble(timeString.substring(6)) * 1000);
}

public int convertSecondStringToMS(String secondString) {
    String str = secondString.substring(0, secondString.length() - 1);
    int result = (int) (Double.parseDouble(str) * 1000);
    return result;
}
```

​    

### 2\. 각 시작, 끝 시간을 1초 구간으로 해서 확인

- 1초 구간이라고 하면 `1.001~2.000` 를 말한다. 이것을 조금 주의해야 한다.
- 해당 구간에서 카운트를 하기 위해서는 3가지를 확인한다.
  1. 요청이 들어올 때
  2. 응답이 나갈때
  3. 처리중 일때

이건 조금만 생각해 보면 쉽게 체크가 가능하다.  요청이 확인 구간 뒤에 들어오거나 응답이 확인 구간 전에 나간거만 확인하면 되기 때문이다.

```java
for (int one : timeArr) {

    // 확인할 시간 구간 정하기 (1초)
    int startTime = one - 999;
    int endTime = one;

    // 확인 시간 동안 적용되는 시간 카운트
    int count = 0;
    for (Req req : reqArr) {
        if (endTime < req.inMs || req.outMs < startTime) continue;
        count++;
    }
    answer = Math.max(answer, count);
}

```

```java
class Req {
    int inMs, outMs;

    Req(int outMs, int during) {
        this.outMs = outMs;
        inMs = outMs - during + 1;
    }
}
```

