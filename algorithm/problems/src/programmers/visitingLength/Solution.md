# 프로그래머스 문제 : 방문 길이

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/49994)

게임 캐릭터를 4가지 명령어를 통해 움직이려 합니다. 명령어는 다음과 같습니다.

- U: 위쪽으로 한 칸 가기
- D: 아래쪽으로 한 칸 가기
- R: 오른쪽으로 한 칸 가기
- L: 왼쪽으로 한 칸 가기

캐릭터는 좌표평면의 (0, 0) 위치에서 시작합니다. 좌표평면의 경계는 왼쪽 위(-5, 5), 왼쪽 아래(-5, -5), 오른쪽 위(5, 5), 오른쪽 아래(5, -5)로 이루어져 있습니다.

이때, 우리는 게임 캐릭터가 지나간 길 중 **캐릭터가 처음 걸어본 길의 길이**를 구하려고 합니다. 예를 들어 위의 예시에서 게임 캐릭터가 움직인 길이는 9이지만, 캐릭터가 처음 걸어본 길의 길이는 7이 됩니다. (8, 9번 명령어에서 움직인 길은 2, 3번 명령어에서 이미 거쳐 간 길입니다)

단, 좌표평면의 경계를 넘어가는 명령어는 무시합니다.

```
[제한사항]
dirs는 string형으로 주어지며, 'U', 'D', 'R', 'L' 이외에 문자는 주어지지 않습니다.
dirs의 길이는 500 이하의 자연수입니다.

[입출력 예]
dirs	answer
ULURRDLLU	7
LULLLLLLU	7

// 자세한 예시 설명은 홈페이지 참조
```



## 문제 풀이

문제가 각 좌표의 걸어본 경로를 기록하는 것과 그 기록에서 중복되는 것을 1회로 카운트 하는 것을 요구한다.

그리고 주의해야 할 점은 (0,1) -> (0.2)를 하게 되면 (0.2) -> (1.0) 된 것이라고 봐야 한다. (0,1) - (0,2) 사이의 경로가 이미 걸어본 것이 되기 때문이다. 




## 코드 구현 [[전체코드]](./Solution.java)

경로를 이동하면서 기록하는 것으로 배열을 생각했다. 즉 각 좌표에서 U, D, R, L에 대한 경우를 카운트 하는 것이다. 

```java
public int solution(String dirs) {
    countArr = new int[11][11][4];	// 각 좌표마다 이동하는 방향 기록
    curRow = curCol = 0;

    for(char dir : dirs.toCharArray()) {
        int[] amountArr = dirMap.get(dir);	// 각 이동 방향에 대한 이동값을 받는다.
        if(isOut(curRow + amountArr[0], curCol + amountArr[1]))
            continue;

        addToCountArr(dir, amountArr);
        curRow += amountArr[0];
        curCol += amountArr[1];
    }
    return getCountArr();
}

public void addToCountArr(char dir, int[] amountArr) {
    
    // 인덱스 0부터 시작하므로 -5~5좌표를 배열에 맞게 보정한다.
    int curRow = this.curRow + 5;	
    int curCol = this.curCol + 5;

    switch(dir) {
        case 'U':   // 0
            countArr[curRow][curCol][0]++;
            countArr[curRow+amountArr[0]][curCol+amountArr[1]][1]++;
            break;
        case 'D':   // 1
            countArr[curRow][curCol][1]++;
            countArr[curRow+amountArr[0]][curCol+amountArr[1]][0]++;
            break;
        case 'R':   // 2
            countArr[curRow][curCol][2]++;
            countArr[curRow+amountArr[0]][curCol+amountArr[1]][3]++;
            break;
        case 'L':   // 3
            countArr[curRow][curCol][3]++;
            countArr[curRow+amountArr[0]][curCol+amountArr[1]][2]++;
            break;
        default : throw new RuntimeException("ERROR");
    }
}
```



반면에 다른 분이 푼 코드를 보니 Set을 이용한 것이 보였다. 생각해보면 Set을 이용하면 자동적으로 중복이 없어지므로 간편하다고도 생각이 들었다. 하지만 Set으로 기록할 때 이전좌표와 이동 후 좌표를 문자열로 붙여서 만드는데 이 때 걸리는 시간이 좀 많이 걸렸다.  어차피 시간안에 들어서 통과되기 때문에 큰 문제는 아니었다. 

```java
public int solution(String dirs) {
    countSet = new HashSet<>();
    curRow = curCol = 0;

    for(char dir : dirs.toCharArray()) {
        int[] amountArr = dirMap.get(dir);
        if(isOut(curRow + amountArr[0], curCol + amountArr[1]))
            continue;

        addToSet(amountArr);
        curRow += amountArr[0];
        curCol += amountArr[1];
    }
    return countSet.size() / 2;
}

public void addToSet(int[] amountArr) {
    // 문자열 변환때문에 속도가 좀더 느리다.
    countSet.add(curRow+""+curCol+","+curRow+amountArr[0]+""+curCol+amountArr[1]));
    countSet.add(curRow+amountArr[0]+""+curCol+amountArr[1]+","+curRow+""+curCol));
}
```