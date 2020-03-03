# 프로그래머스 : 베스트 앨범

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42579)

스트리밍 사이트에서 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범을 출시하려 합니다. 노래는 고유 번호로 구분하며, 노래를 수록하는 기준은 다음과 같습니다.

1. 속한 노래가 많이 재생된 장르를 먼저 수록합니다.
2. 장르 내에서 많이 재생된 노래를 먼저 수록합니다.
3. 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.

노래의 장르를 나타내는 문자열 배열 genres와 노래별 재생 횟수를 나타내는 정수 배열 plays가 주어질 때, 베스트 앨범에 들어갈 노래의 고유 번호를 순서대로 return 하도록 solution 함수를 완성하세요.

```
[제한 사항]
- genres[i]는 고유번호가 i인 노래의 장르입니다.
- plays[i]는 고유번호가 i인 노래가 재생된 횟수입니다.
- genres와 plays의 길이는 같으며, 이는 1 이상 10,000 이하입니다.
- 장르 종류는 100개 미만입니다.
- 장르에 속한 곡이 하나라면, 하나의 곡만 선택합니다.
- 모든 장르는 재생된 횟수가 다릅니다.

[예시]
genres
["classic", "pop", "classic", "classic", "pop"]
plays
[500, 600, 150, 800, 2500]
return
[4,1,3,0]
```



## 문제 풀이

이 문제는 각 장르별 플레이 합을 구하고, 그 합이 가장 큰 장르부터 2개 이하로 인덱스를 출력하는 문제이다. 위의 예시로 보면 좀더 이해하기가 쉽다.

```
// 위의 예시
classic 총합 : 1430
pop 총합 : 3100

출력 순서 pop, classic
pop에서 1번째로 큰, 2번째로 큰 인덱스 : 4, 1
classic에서 1번째로 큰, 2번째로 큰 인덱스 : 3, 0

return [4, 1, 3, 0]
```

각각 크기 순서별로 2개 이하로 꺼내면 되므로 힙을 이용하기로 했다.  2개의 힙을 이용했는데 하나는 장르와 장르 플레이 총합을 기준으로 , 또 하나는 인덱스와 개별 플레이값 기준으로 만들었다.

2개의 힙을 다 채우는 게 끝이 나면 장르와 장르 플레이 총합에서 가장 큰 장르를 뽑은 다음에 개별 플레이가 있는 힙에서 2개씩 뽑아서 배열에 채우고 반환하면 끝이 난다. 



## 코드 구현 [[전체코드]](Solution.java)

힙을 위해 자바의 PriorityQueue를 이용했고 내부에 들어갈 Pair은 Comparable를 implements하여 구현했다. 

genreMap에서는 각 장르별 총합을 구했고 playMap에서는 장르별 각 플레이값과 인덱스 값을 PriorityQueue를 이용해서 가장 큰 순서부터 뽑을 수 있게 정리를 했다. 그다음 genreMap에서 다시 PriorityQueue에 값을 넣어 가장 큰 장르부터 뽑아서 리스트에 채운다음 배열로 복사해서 반환했다. 

리스트를 이용한 이유는 반환해야 하는 배열의 길이를 알려면 조금더 번거로웠기 때문이다. countMap을 하나 더 만들어서 순회하면서 각 장르별로 2개 이하인 것을 다 체크해야 하고 그 이후에 다시 countMap을 순회하면서 값을 누적한 다음에 배열 크기를 구해야 했기 때문이다. 차라리 리스트를 만들어서 끝낸다음 배열로 복사하는게 좀더 단순하다고 생각되어 리스트를 사용했다.

```java
// OnePair 구현은 생략. 전체 코드 참고

public class Solution {
    public int[] solution(String[] genres, int[] plays) {
        // 2개의 Map 생성
        Map<String, PriorityQueue<OnePair<Integer>>> playMap = new HashMap<>();
        Map<String, OnePair<String>> genreMap = new HashMap<>();
        
        // 결과 저장 위한 리스트 생성
        List<Integer> list = new ArrayList<>();

        // genres, plays를 순회한다.
        int len = genres.length;
        for(int i=0; i<len; i++) {
            String genre = genres[i];
            int play = plays[i];
            
            if(!genreMap.containsKey(genre)) 
                genreMap.put(genre, new OnePair<>(genre, 0));
            if(!playMap.containsKey(genre)) 
                playMap.put(genre, new PriorityQueue<>());
            
            // 장르 총합에 추가
            genreMap.get(genre).addValue(play);
            
            // 장르별 플레이, 인덱스를 우선순위큐에 추가
            playMap.get(genre).add(new OnePair<Integer>(i, play));
        }

        // 장르 총합을 크기 순으로 뽑기 위한 우선순위 큐 생성
        PriorityQueue<OnePair<String>> genrePQ = new PriorityQueue<>();
        
        for(OnePair<String> one : genreMap.values()) {
            genrePQ.add(one);
        }
        
        // 장르 총합 큰 순서대로 뽑아서 답을 낸다.
        while(genrePQ.size() > 0) {
            OnePair<String> genrePair = genrePQ.poll();
            
            // 해당 장르의 우선순위 큐를 뽑아서 2개 이하로 플레이, 인덱스값을 추가한다.
            PriorityQueue<OnePair<Integer>> playPQ = playMap.get(genrePair.key);
            int loopLimit = Math.min(2, playPQ.size());
            
            for(int i=0; i<loopLimit; i++) {
                list.add(playPQ.poll().key);
            }
        }
        
        // 반환하기 위해 배열로 복사
        int[] ret = new int[list.size()];
        for(int i=0; i<list.size(); i++) ret[i] = list.get(i);
        return ret;
    }
}
```

