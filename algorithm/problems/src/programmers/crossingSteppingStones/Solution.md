# 프로그래머스 : 징검다리 건너기

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/64062)

​    징검다리를 건널 수 있도록 다음과 같이 규칙을 만들었습니다.

- 징검다리는 일렬로 놓여 있고 디딤돌에는 모두 숫자가 적혀 있으며 디딤돌의 숫자는 한 번 밟을 때마다 1씩 줄어듭니다.
- 디딤돌의 숫자가 0이 되면 더 이상 밟을 수 없으며 이때는 그 다음 디딤돌로 한번에 여러 칸을 건너 뛸 수 있습니다.
- 단, 다음으로 밟을 수 있는 디딤돌이 여러 개인 경우 무조건 가장 가까운 디딤돌로만 건너뛸 수 있습니다.

디딤돌에 적힌 숫자가 순서대로 담긴 배열 stones와 한 번에 건너뛸 수 있는 디딤돌의 최대 칸수 k가 매개변수로 주어질 때, 최대 몇 명까지 징검다리를 건널 수 있는지 return 하도록 solution 함수를 완성해주세요.

```
[제한사항]
- 징검다리를 건너야 하는 니니즈 친구들의 수는 무제한 이라고 간주합니다.
- stones 배열의 크기는 1 이상 200,000 이하입니다.
- stones 배열 각 원소들의 값은 1 이상 200,000,000 이하인 자연수입니다.
- k는 1 이상 stones의 길이 이하인 자연수입니다.

[입출력 예]
stones		        		k	result
[2, 4, 5, 3, 2, 1, 4, 2, 5, 1]          3	  3
```





## 문제 풀이

 처음에는 k만큼 구간을 이동하면서 k구간안에서 최대값, 그 최대값 중에 최소값을 구하는 식으로 접근했다. O(stones.length * k) 하지만 배열 크기가 20만이라서 효율성 테스트에서 전부다 실패했다. k가 최대가 stones 길이만큼이니 결국 최악은 O(n^2) 이 될 것이다. 

그 다음 시도는 이진 탐색을 이용했다. 먼저 이진 탐색으로 구간 크기가 k인 구간이 있는 값을 찾는다. 그 다음에 크기가 k인 구간에서 최대값, 각 구간 최대값 중에서는 최소값을 찾아서 mid에 더해서 끝낸다.





## 코드 구현 [[전체코드]](Solution.java)

```java
public int solution(int[] stones, int k) {
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    for(int one : stones) {
        if(min > one) min = one;
        if(max < one) max = one;
    }
    while(true) {
        // 만약 max, min이 동일하다면 바로 나온다.
        if(max == min) return max;
        
        // 구간들중에 가장 큰 구간을 구한다.
        int mid = (max + min) / 2;
        int maxCount = 0, count = 0;
        for(int one : stones) {
            if(one - mid < 1) count++;
            else if(count > 0) {
                maxCount = Math.max(maxCount, count);
                count = 0;
            }
        }
        maxCount = Math.max(maxCount, count);
        
        // 구간이 k보다 크면 max는 내린다.
        if (maxCount > k) {
            max = mid;
            continue;
        }
        
        // 구간이 k보다 크면 min을 올린다.
        if(maxCount < k) {
            min = mid+1;
            continue;
        }
        
        // 구간이 k구간이 존재하고 k초과 구간이 없을때
        if(maxCount == k) {
            int[] countArr = new int[k];
            int idx = 0, passedCount = 0, 
            	passedMax = Integer.MAX_VALUE;
            
            // 전체 배열 순회하면서 길이 k인 구간을 찾는다.
            for(int stone : stones) {
                int passedStone = stone - mid;
                if(passedStone < 1) {
                    countArr[idx++] = passedStone;
                    passedCount++;
                } else {
                    passedCount = 0;
                    idx = 0;
                }
                
                // k인 구간에서 최대값, 최대값들중에서 최소값을 찾는다.
                if(passedCount == k) {
                    passedMax = Math.min(passedMax, getMax(countArr));
                    passedCount = 0;
                    idx = 0;
                }
            }
            
            // 최대값들중 최소값을 중간값과 더한다.
            return mid + passedMax;
        }
    }
}
```

