# 프로그래머스 : 호텔 방 배정


## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/64063)

스노우타운에서 호텔을 운영하고 있는 스카피는 호텔에 투숙하려는 고객들에게 방을 배정하려 합니다. 호텔에는 방이 총 k개 있으며, 각각의 방은 1번부터 k번까지 번호로 구분하고 있습니다. 처음에는 모든 방이 비어 있으며 스카피는 다음과 같은 규칙에 따라 고객에게 방을 배정하려고 합니다.

1. 한 번에 한 명씩 신청한 순서대로 방을 배정합니다.
2. 고객은 투숙하기 원하는 방 번호를 제출합니다.
3. 고객이 원하는 방이 비어 있다면 즉시 배정합니다.
4. 고객이 원하는 방이 이미 배정되어 있으면 원하는 방보다 번호가 크면서 비어있는 방 중 가장 번호가 작은 방을 배정합니다.

예를 들어, 방이 총 10개이고, 고객들이 원하는 방 번호가 순서대로 [1, 3, 4, 1, 3, 1] 일 경우 다음과 같이 방을 배정받게 됩니다.

| 원하는 방 번호 | 배정된 방 번호 |
| -------------- | -------------- |
| 1              | 1              |
| 3              | 3              |
| 4              | 4              |
| 1              | 2              |
| 3              | 5              |
| 1              | 6              |

전체 방 개수 k와 고객들이 원하는 방 번호가 순서대로 들어있는 배열 room_number가 매개변수로 주어질 때, 각 고객에게 배정되는 방 번호를 순서대로 배열에 담아 return 하도록 solution 함수를 완성해주세요.



#### **[제한사항]**

- k는 1 이상 10^12 이하인 자연수입니다.
- room_number 배열의 크기는 1 이상 200,000 이하입니다.
- room_number 배열 각 원소들의 값은 1 이상 k 이하인 자연수입니다.
- room_number 배열은 모든 고객이 방을 배정받을 수 있는 경우만 입력으로 주어집니다.
  - 예를 들어, k = 5, room_number = [5, 5] 와 같은 경우는 방을 배정받지 못하는 고객이 발생하므로 이런 경우는 입력으로 주어지지 않습니다.

------

##### **[입출력 예]**

| k    | room_number   | result        |
| ---- | ------------- | ------------- |
| 10   | [1,3,4,1,3,1] | [1,3,4,2,5,6] |

​                                     

## 문제 풀이

프로그래머스에서는 정확성 테스트, 효율성 테스트가 각각 존재한다. 정확성 테스트 통과는 상당히 쉽다. 그냥 해당 인덱스부터 하나씩 확인하면서 올라가면 된다. 

하지만 이렇게 하면 효율성 테스트를 통과할 수 가 없다.

만약 하나씩 확인하는 로직으로 최악의 경우를 만나게 되면 대략 20,000,000,000 번의 연산이 필요하다. (예를 들면 200,000명이 모두 2번만 원한다거나 하는 경우 이다.)

따라서 이것을 통과하기 위해서는 이미 방 예약이 되어 있다면 그 다음 순번을 캐시해줘야 통과가 가능하다. 고민 했던 부분은 만약 2번을 선택했을 때 2,3번이 차있는 경우, 2,3,4,5번이 차있는 경우 등에 대해서 2번 뿐만 아니라 그 이후에 이미 예약이 완료된 방들에 대해서도 캐시를 어떻게 해줄 것인가였다.

이것을 위해 재귀호출을 통해서 각각의 방번호의 탐색을 콜스택으로 쌓아놓고 빈 방을 찾았을 때 그 빈방 +1 만큼의 값을 설정하는 식으로 풀어보았다.  만약 그 빈방이 이후에 비어있지 않을 지라도 캐시된 방으로 가서 비어있는지 확인해서 계속 반복하면 되므로 큰 문제가 되지 않았다.

​                                  

## 코드 구현 [[전체코드]](Solution.java)

```java
public long[] solution(long k, long[] roomNumber) {
    HashMap<Long, Long> hotel = new HashMap<>();
    long[] answer = new long[roomNumber.length];

    for(int i=0; i<roomNumber.length; i++) {
        answer[i] = this.recursive(roomNumber[i], hotel);
    }
    return answer;
}

public long recursive(long index, Map<Long, Long> hotel) {
    // 만약 해당 키가 없다면, 해당 index를 보내고, 그다음 인덱스(빈방)을 저장해준다.
    if(!hotel.containsKey(index)) {
        hotel.put(index, index+1);
        return index;
    }

    // 만약 해당인덱스에 캐시된 값이 있다면 해당 키의 값으로 재귀호출을 한다.
    long jumpIndex = hotel.get(index);
    long result = recursive(jumpIndex, hotel);
    hotel.put(index, result+1);
    return result;
}
```
