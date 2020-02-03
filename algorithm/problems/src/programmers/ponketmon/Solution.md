# 프로그래머스 : 폰켓몬

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/1845)

당신은 폰켓몬을 잡기 위한 오랜 여행 끝에, 홍 박사님의 연구실에 도착했습니다. 홍 박사님은 당신에게 자신의 연구실에 있는 총 N 마리의 폰켓몬 중에서 N/2마리를 가져가도 좋다고 했습니다. 

당신은 최대한 다양한 종류의 폰켓몬을 가지길 원하기 때문에, 최대한 많은 종류의 폰켓몬을 포함해서 N/2마리를 선택하려 합니다. N마리 폰켓몬의 종류 번호가 담긴 배열 nums가 매개변수로 주어질 때, N/2마리의 폰켓몬을 선택하는 방법 중, 가장 많은 종류의 폰켓몬을 선택하는 방법을 찾아, 그때의 폰켓몬 종류 번호의 개수를 return 하도록 solution 함수를 완성해주세요.

```
[제한사항]
- nums는 폰켓몬의 종류 번호가 담긴 1차원 배열입니다.
- nums의 길이(N)는 1 이상 10,000 이하의 자연수이며, 항상 짝수로 주어집니다.
- 폰켓몬의 종류 번호는 1 이상 200,000 이하의 자연수로 나타냅니다.
- 선택하는 방법이 여러 가지인 경우 선택가능한 폰켓몬 종류 개수의 최댓값 하나만 return 하면 됩니다.

[입출력 예]
[3,1,2,3]		return 2
[3,3,3,2,2,4]	return 3
[3,3,3,2,2,2]	return 2
```




## 문제 풀이 

이 문제는 종류를 센 다음에 그 종류 가짓수가 전체 배열 길이보다 크면 전체 배열 길이 / 2 만큼 반환하면 되고, 그렇지 않다면 가짓수를 반환하면 되는 문제이다.



## 코드 구현

종류를 세기 위해서 해쉬맵을 이용했다. 

```java
public int solution(int[] nums) {
    HashMap<Integer, Integer> countMap = new HashMap<>();

    // 카운팅
    for(int i=0; i<nums.length; i++) {
        if(countMap.containsKey(nums[i])) countMap.put(nums[i], countMap.get(nums[i])+1);
        else countMap.put(nums[i], 1);
    }
    
    int count = countMap.size();
    int half = nums.length/2;
    // length체크
    return Math.min(count, half);
}
```

