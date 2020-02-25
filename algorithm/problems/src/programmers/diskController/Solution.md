# 프로그래머스 : 디스크 컨트롤러

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42627)

하드디스크는 한 번에 하나의 작업만 수행할 수 있습니다. 디스크 컨트롤러를 구현하는 방법은 여러 가지가 있습니다.  각 작업에 대해 [작업이 요청되는 시점, 작업의 소요시간]을 담은 2차원 배열 jobs가 매개변수로 주어질 때, 작업의 요청부터 종료까지 걸린 시간의 평균을 가장 줄이는 방법으로 처리하면 평균이 얼마가 되는지 return 하도록 solution 함수를 작성해주세요. (자세한 내용은 링크 참조)

```
[제한 사항]
- jobs의 길이는 1 이상 500 이하입니다.
- jobs의 각 행은 하나의 작업에 대한 [작업이 요청되는 시점, 작업의 소요시간] 입니다.
- 각 작업에 대해 작업이 요청되는 시간은 0 이상 1,000 이하입니다.
- 각 작업에 대해 작업의 소요시간은 1 이상 1,000 이하입니다.
- 하드디스크가 작업을 수행하고 있지 않을 때에는 먼저 요청이 들어온 작업부터 처리합니다.

[입출력 예제]
[[0, 3], [1, 9], [2, 6]] return 9
```



## 문제 풀이

jobs안의 job을 처리할 때마다 time이 증가한다. 그다음 그 time 기준으로 정렬을 해서 가장 적은 처리시간이 걸리는 것부터 처리해줘야 한다. 여기에 적절한 자료구조는 힙이다.

1. job을 요청시각, 처리시간 기준으로 정렬해준다.
2. 해당 time에 해당하는 요청시간을 가진 job 중에 가장 적은 처리시간을 가진 job을 처리
3. 처리하면서 time을 증가. 
4. 문제에서 구하려는 누적 시간을 증가. (acc)
5. 2~4를 계속 반복한다.



## 코드 구현 [[전체코드]](Solution.java)

```java
public int solution(int[][] jobs) {
        // 정렬
        Arrays.sort(jobs, (a,b)->a[0]==b[0]?a[1]-b[1]:a[0]-b[0]);

        int acc = 0, time = 0;
        int cnt = 0, cursor = 0, len = jobs.length;
        MinHeap heap = new MinHeap();

        while(cnt++ < len) {
            
            // time 이하 요청을 힙에 넣는다.
            while(cursor < len) {
                if(jobs[cursor][0] <= time) heap.add(jobs[cursor++]);
                else break;
            }
            
            // time 이하 요청을 다 처리한 상태인지 체크
            if(heap.length() == 0) heap.add(jobs[cursor++]);
			
            // 최소 job을 가져온다
            int[] one = heap.popMin();

            // 요청시각이 time으로라면 time을 옮긴다.
            if(time < one[0]) time = one[0];
	
            // 처리시간을 더해주고, 누적시간에도 반영한다.
            time += one[1];
            acc += (time - one[0]);
        }
    
        return acc / jobs.length;
    }
```

