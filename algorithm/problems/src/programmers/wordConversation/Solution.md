# 프로그래머스 문제 : 단어 변환

## 문제 설명 [[링크]](https://programmers.co.kr/learn/courses/30/lessons/43163)

```
두 개의 단어 begin, target과 단어의 집합 words가 있습니다. 아래와 같은 규칙을 이용하여 begin에서 target으로 변환하는 가장 짧은 변환 과정을 찾으려고 합니다.

1. 한 번에 한 개의 알파벳만 바꿀 수 있습니다.
2. words에 있는 단어로만 변환할 수 있습니다.
예를 들어 begin이 hit, target가 cog, words가 [hot,dot,dog,lot,log,cog]라면 hit -> hot -> dot -> dog -> cog와 같이 4단계를 거쳐 변환할 수 있습니다.

두 개의 단어 begin, target과 단어의 집합 words가 매개변수로 주어질 때, 최소 몇 단계의 과정을 거쳐 begin을 target으로 변환할 수 있는지 return 하도록 solution 함수를 작성해주세요.

[제한사항]
- 각 단어는 알파벳 소문자로만 이루어져 있습니다.
- 각 단어의 길이는 3 이상 10 이하이며 모든 단어의 길이는 같습니다.
- words에는 3개 이상 50개 이하의 단어가 있으며 중복되는 단어는 없습니다.
- begin과 target은 같지 않습니다.
- 변환할 수 없는 경우에는 0를 return 합니다.

[입출력 예]
hit	cog	[hot, dot, dog, lot, log, cog]	return 4
hit	cog	[hot, dot, dog, lot, log]	return 0
```



## 문제 풀이

그래프에 1개만 다른 문자끼리 addEdge를 해준다음에 DFS 순회하면서 최소값을 찾으면 된다.

1. 1글자만 다른 문자를 찾아서 addEdge해준다.
2. dfs로 순회하면서 카운트한 결과값들 중에서 최소값을 찾는다.





## 코드 구현 [[전체코드]](./Solution.java)

1글자만 다른 단어 찾는 코드이다. 원래는 O(N^2)으로 1글자 다른 문자를 찾으려 했다. 

```
public boolean checkWords(String one, String target) {
	char[] arr = target.toCharArray();
	int len = one.length(), count = 0;
	for(int i=0; i < len; i++) {
		for(int j=0; j<arr.length; j++) {
			if(one.charAt(i) == arr[j]) {
				arr[j] = 0;
				count++;
				break;
			}
		}
	}
	return count == (len-1);
}
```

하지만 통과하고 나서 다른 사람들의 코드를 보니 더욱 좋은 방식이 있었다. 생각해보면 1글자만 다른거니까 그외에는 서로 같은 단어이기에 굳이 이중 for문을 사용할 필요가 없다.

```java
public boolean checkWords(String one, String target) {
	int count = 0, len = one.length();
	for(int i=0; i<len; i++) {
		if(one.charAt(i) != target.charAt(i)) count++;
		if(count > 1) return false;
	}
	return true;
}
```



그래프의 dfs를 사용했다. 

```java
private int _dfs(String start, String end, int count) {
	if(start.equals(end)) return count;
	passedList.push(start);
	ArrayList<String> oneList = this.vMap.get(start);
	int ret = MAX_VALUE;
	for(int i=0; i<oneList.size(); i++) {
		String one = oneList.get(i);
		if(passedList.indexOf(one) > -1) continue;
		ret = Math.min(ret, _dfs(one, end, count+1));
	}
	passedList.pop();
	return ret;
}
```

