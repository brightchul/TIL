# 프로그래머스 문제 : 길 찾기 게임



## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/42892)

라이언은 아래와 같은 특별한 규칙으로 트리 노드들을 구성한다.

- 트리를 구성하는 모든 노드의 x, y 좌표 값은 정수이다.
- 모든 노드는 서로 다른 x값을 가진다.
- 같은 레벨(level)에 있는 노드는 같은 y 좌표를 가진다.
- 자식 노드의 y 값은 항상 부모 노드보다 작다.
- 임의의 노드 V의 왼쪽 서브 트리(left subtree)에 있는 모든 노드의 x값은 V의 x값보다 작다.
- 임의의 노드 V의 오른쪽 서브 트리(right subtree)에 있는 모든 노드의 x값은 V의 x값보다 크다.

곤경에 빠진 카카오 프렌즈를 위해 이진트리를 구성하는 노드들의 좌표가 담긴 배열 nodeinfo가 매개변수로 주어질 때,
노드들로 구성된 이진트리를 전위 순회, 후위 순회한 결과를 2차원 배열에 순서대로 담아 return 하도록 solution 함수를 완성하자.

```
[제한 사항]
- nodeinfo는 이진트리를 구성하는 각 노드의 좌표가 1번 노드부터 순서대로 들어있는 2차원 배열이다.
- nodeinfo의 길이는 1 이상 10,000 이하이다.
- nodeinfo[i] 는 i + 1번 노드의 좌표이며, [x축 좌표, y축 좌표] 순으로 들어있다.
- 모든 노드의 좌표 값은 0 이상 100,000 이하인 정수이다.
- 트리의 깊이가 1,000 이하인 경우만 입력으로 주어진다.
- 모든 노드의 좌표는 문제에 주어진 규칙을 따르며, 잘못된 노드 위치가 주어지는 경우는 없다.

[입력 예시]
[[5,3],[11,5],[13,3],[3,5],[6,1],[1,3],[8,6],[7,2],[2,2]]
return [[7,4,6,9,1,8,5,2,3],[9,6,5,8,1,4,3,2,7]]
```



## 문제 풀이

이 문제는 y좌표가 레벨이라고 보면 되고, 순서는 입력배열에서 인덱스 +1이다. 그리고 x와 y크기별로 이진 트리에 넣은 다음에 전위순회, 후위순회를 하면 끝나는 문제이다.

다만 이진 트리를 가정한다고 미리 언급되어 있었기 때문에 같은 y좌표일 때 점은 2개일 거라고 가정하고 풀었다.




## 코드 구현 [[전체코드]](./Solution.java) 

먼저 입력 순서대로 노드 객체를 생성하여 순서를 각 노드가 가지고 있도록 만들었다. 또한 정렬을 하되 y는 내림차순으로, x는 오름차순으로 정렬할 수 있도록 compareTo를 오버라이드 했다.

```java
class Node implements Comparable<Node>{
    Node left, right;
    int x, y, value;
    Node(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public int compareTo(Node node) {
        if(y == node.y) return x-node.x;
        return node.y-y;
    }
}
```



트리구조에서 새로운 노드를 추가할 때 기존의 노드 값과 비교해서 추가하는데 그 과정에서 기존 노드의 위치에 들어가게 되면 재귀호출로 그 과정이 반복된다. 그 과정에서 실수가 발생할 수 있으므로 트리에 넣기 전에 미리 정렬을 다 해놓고 루트부터 차례대로 트리에 추가하는 방식으로 만들었다.

```java
public int[][]solution(int[][] nodeinfo) {
    Node[] nodeArr = new Node[nodeinfo.length];
    
	// 입력값 순서대로 노드들을 생성
    for(int i=0; i<nodeinfo.length;)
        nodeArr[i] = new Node(nodeinfo[i][0], nodeinfo[i][1], ++i);

    // 각 노드들을 정렬
    Arrays.sort(nodeArr);
    
    BinaryTree bt = new BinaryTree();

    // 정렬한 다음 순서대로 추가
    for(Node one : nodeArr)
        bt.add(one);
    
    // 이하 중략
```



전위순회, 후위순회를 하면서 계속 push만 해주면 되므로 간소화된 Stack클래스를 만들었다. ArrayList의 경우 다시 배열로 반환하려면 배열을 생성하고 순회를 해줘야 했다. 문제풀기용으로 만들었기 때문에 Stack 내부의 배열을 바로 반환하는 방식으로 구현했다. 

```java
class Stack {
    int[] arr;
    int idx = 0;
    Stack(int size) {
        arr = new int[size];
    }
    void push(int v) {
        arr[idx++] = v;
    }
    int[] toArray() {
        return arr;
    }
}
```

