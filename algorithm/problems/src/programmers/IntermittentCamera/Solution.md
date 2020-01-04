# 프로그래머스 문제 : 단속 카메라

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42884)

```
고속도로를 이동하는 모든 차량이 고속도로를 이용하면서 단속용 카메라를 한 번은 만나도록 
카메라를 설치하려고 합니다.

고속도로를 이동하는 차량의 경로 routes가 매개변수로 주어질 때, 모든 차량이 한 번은 
단속용 카메라를 만나도록 하려면 최소 몇 대의 카메라를 설치해야 하는지를 return 하도록
solution 함수를 완성하세요.


[제한사항]
- 차량의 대수는 1대 이상 10,000대 이하
- routes[i][0]에는 i번째 차량이 고속도로에 진입한 지점 
- routes[i][1]에는 i번째 차량이 고속도로에서 나간 지점
- 차량의 진입/진출 지점에 카메라가 설치되어 있어도 카메라를 만난것으로 간주
- 차량의 진입 지점, 진출 지점은 -30,000 이상 30,000 이하
```



## 문제 풀이

머릿속에 x좌표를 가지는 가상의 수평선을 그려놓고 해보면 된다. 

정렬된 배열 안의 값을 순회하면서 값을 체크하고 카운트를 했다. 이 방법이 더 빨랐다. 1씩 증가하는게 아니라 각 배열 크기만큼만 순회했기 때문이다. 서로 겹치는 구간인지 확인하는 방법은 아웃하는 X좌표와 들어오는 X좌표들의 크기를 비교하면 가능하다.



## 코드 구현 [[전체코드]](./Solution.java)

배열안을 순회할 때 각 요소들간의 `들어오는 X좌표`와 `나가는 X좌표`를 확인한 다음 `나가는 X좌표`가 `들어오는 X좌표`보다 클경우에는 `나가는 X좌표`들 중에 `가장 작은 값`을 설정한다. 만약 `들어오는 X좌표`가 큰 요소를 만났을 경우 카운트를 1증가 시키고 `나가는 X좌표`를 새로 설정해 준다.

맨 마지막으로 그 과정을 담고 비우는 리스트의 길이가 1이상이라면 아직 남아있다는 증거이기 때문에 카운트를 1증가 시켜준다.

```java
public int solution(int[][] routes) {
    Arrays.sort(routes, (ints, t1) -> ints[0] - t1[0]);

    int [] one;
    ArrayList<int[]> list = new ArrayList<>();
    int count = 0, outValue = Integer.MAX_VALUE;

    for(int i=0; i<routes.length; i++) {
        one = routes[i];
        if(outValue < one[0]) {
            list.clear();
            count++;
            outValue = one[1];
        } else {
            outValue = Math.min(one[1], outValue);
        }
        list.add(one);
    }
    return list.size() > 0 ? count+1 : count;
}
```

