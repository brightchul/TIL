# 알고스팟 문제 : 트리 순회 순서 변경

## 문제 설명 [[링크]](https://algospot.com/judge/problem/read/TRAVERSAL)

```
어떤 이진 트리를 전위 순회했을 때 노드들의 방문 순서와, 중위 순회했을 때 노드들의 방문 순서가 주어졌다고 합시다. 이 트리를 후위 순회했을 때 각 노드들을 어떤 순서대로 방문하게 될지 계산하는 프로그램을 작성하세요.

입력
입력의 첫 줄에는 테스트 케이스의 수 C (1≤C≤100)가 주어집니다. 각 테스트 케이스는 세 줄로 구성되며, 첫 줄에는 트리에 포함된 노드의 수 N (1≤N≤100)이 주어집니다. 그 후 두 줄에 각각 트리를 전위 순회했을 때와 중위순회 했을 때의 노드 방문 순서가 N개의 정수로 주어집니다. 각 노드는 1000 이하의 자연수로 번호 매겨져 있으며, 한 트리에서 두 노드의 번호가 같은 일은 없습니다.

출력
각 테스트 케이스마다, 한 줄에 해당 트리의 후위 순회했을 때 노드들의 방문 순서를 출력합니다.

[예제 입력]
2
7
27 16 9 12 54 36 72
9 12 16 27 36 54 72
6
409 479 10 838 150 441
409 10 479 150 838 441

[예제 출력]
12 9 16 36 72 54 27
10 150 441 838 479 409
```



## 문제 풀이

전위 순회의 경우 그 특성상 해당 트리의 root가 가장 처음에 온다. 그리고 중위 순회의 경우 root값을 기준으로 해서 오른편과 왼편으로 서브트리가 나뉜다. 이 2가지 특성을 이용해서 재귀호출로 순회가 가능하다.

1. root 노드 값을 가져온 뒤 중위 순회에서 어느 위치에 있는지 파악한다.
2. 전위 순회, 중위 순회하는 각각의 값에서 해당 서브트리의 height만큼 잘라서 재귀호출을 한다. 
3. 후위 순회로 출력한다.

```
전위 순회 : 27 16  9 12 54 36 72
중위 순회 :  9 12 16 27 36 54 72
root 값: 27
좌 : 전위 16 9 12, 중위 9 12 16
우 : 전위 54 36 72, 중위 36 54 72


전위 순회 : 16  9 12
중위 순회 :  9 12 16
root 값: 16
좌 : 전위 9 12, 중위 9 12
우 : 전위 없음, 중위 없음

...재귀호출... 
```





## 코드 구현 [[전체코드]](./Main.java)

```java
public static void recursive(List<String> pList, List<String> iList) {
    int totalHeight = pList.size();
    if(totalHeight == 0) return;

    // 전위 호출, 중위 호출로 받은 각각의 리스트에서 root와 rootIdx를 뽑아낸다
    String root = pList.get(0);
    int rootIdx = iList.indexOf(root);
    
    // rootIdx는 좌측 서브트리의 height이기도 하다.
    int leftHeight = rootIdx;

    // recursive 재귀함수를 이용해서 각각 좌측 서브트리와 우측 서브트리를 순회한다
    recursive(pList.subList(1, 1+leftHeight), 
              iList.subList(0, leftHeight));

    recursive(pList.subList(rootIdx + 1, totalHeight), 
              iList.subList(rootIdx + 1, totalHeight));

    // root 변수 출력하는 위치에 따라서 전,중,후위 순회가 가능하다
    // 이 위치에 있으면 후위 순회로 출력
    System.out.println(root + " ");	
}
```

