# 프로그래머스 문제 : [3차] 압축

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/17684)

```
LZW 압축은 다음 과정을 거친다.

1. 길이가 1인 모든 단어를 포함하도록 사전을 초기화한다.
2. 사전에서 현재 입력과 일치하는 가장 긴 문자열 `w`를 찾는다.
3. `w`에 해당하는 사전의 색인 번호를 출력하고, 입력에서 `w`를 제거한다.
4. 입력에서 처리되지 않은 다음 글자가 남아있다면(`c`), `w+c`에 해당하는 단어를 사전에 등록한다.
5. 단계 2로 돌아간다.

예를 들어 입력으로 `KAKAO`가 들어온다고 하자.

1. 현재 사전에는 `KAKAO`의 첫 글자 `K`는 등록되어 있으나, 두 번째 글자까지인 `KA`는 없으므로, 첫 글자 `K`에 해당하는 색인 번호 11을 출력하고, 다음 글자인 `A`를 포함한 `KA`를 사전에 27 번째로 등록한다.
2. 두 번째 글자 `A`는 사전에 있으나, 세 번째 글자까지인 `AK`는 사전에 없으므로, `A`의 색인 번호 1을 출력하고, `AK`를 사전에 28 번째로 등록한다.
3. 세 번째 글자에서 시작하는 `KA`가 사전에 있으므로, `KA`에 해당하는 색인 번호 27을 출력하고, 다음 글자 `O`를 포함한 `KAO`를 29 번째로 등록한다.
4. 마지막으로 처리되지 않은 글자 `O`에 해당하는 색인 번호 15를 출력한다.

자세한 내용은 링크 참고.

[입력 형식]
입력으로 영문 대문자로만 이뤄진 문자열 msg가 주어진다. msg의 길이는 1 글자 이상, 1000 글자 이하이다.

[출력 형식]
주어진 문자열을 압축한 후의 사전 색인 번호를 배열로 출력하라.

입출력
KAKAO                    [11, 1, 27, 15]
TOBEORNOTTOBEORTOBEORNOT [20, 15, 2, 5, 15, 18, 14, 15, 20, 27, 29, 31, 36, 30, 32, 34]
ABABABABABABABAB         [1, 2, 27, 29, 28, 31, 30]
```



## 문제 풀이

구현 내용은 이미 문제에 다 있다. 그것을 보고 구현하면 되는 문제이다.

한가지 신경 써야 하는 부분은 마지막 부분일 것이다. 사전에 마지막 문자가 이전 문자의 조합으로 같이 있는 경우와 그게 아니라 마지막 문자 하나를 체크할 때가 각각 다르게 된다. 이 때 인덱스 이동을 좀더 신경 써야 한다.



## 코드 구현 [[전체코드1]](./Solution.java) [[전체코드2]](./Solution2.java)

처음에는 문제를 보고 별 생각없이 구현을 했다. 

```java
public int[] solution(String msg) {
    map = initMap();
    arr = msg.split("");
    List<Integer> resultList = new ArrayList<>();
    int idx= 0;
    int lastIndex = 26;
    while(idx < arr.length-1) {
        String one = findLongWord(idx);
        idx += one.length();
        resultList.add(map.get(one));
        if(idx >= arr.length) break;
        map.put(one+arr[idx], ++lastIndex);
    }
    if(idx == arr.length-1) resultList.add(map.get(arr[idx]));
    return convertListToArr(resultList);
}

public String findLongWord(int idx) {
    String one = arr[idx];
    String pre = one;
    while(idx < arr.length) {
        if(map.get(one) == null) break;
        pre = one;
        if(++idx >= arr.length) break;
        one += arr[idx];
    }
    return pre;
}
```

압축 로직을 보면 문자열과 인덱스를 찾은 다음에 문자열을 비워버린다. 위의 코드에서는 비워버리는 것, 마지막 인덱스일때, 마지막 인덱스가 아니지만 가장 긴 문자를 찾는 과정에서 마지막 인덱스에 다다를 때에 대해서 조금 복잡하게 하고 있다. 



두번째는 스택을 활용했는데 위의 방식과 다르게 많이 단순해 진다.

```java
public int[] solution(String msg) {
    init(msg);
    List<Integer> ret = new ArrayList<>();
    while(!stack.isEmpty()) {
        int idx = findLongestWordIdx();
        ret.add(idx);
    }
    return convertListToArr(ret);
}

public int findLongestWordIdx() {
    String one = "";
    int idx = -1;
    while(!stack.isEmpty()) {
        if(!map.containsKey(one + stack.peek())) break;
        idx = map.get((one += stack.pop()));
    }
    if(!stack.isEmpty())
        map.put(one + stack.peek(), map.size());
    return idx;
}
```

