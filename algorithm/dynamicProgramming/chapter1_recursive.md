# 1장 재귀 호출의 모든 것

## 1.1 재귀 접근 방법이란?

재귀 함수 (recursive function) : 직/간접적으로 자기 자신을 호출하는 함수를 재귀함수라고 한다. 

재귀 방식으로 문제를 풀면 이해하기 복잡한 문제도 경우에 따라서는 쉽게 구체화할 수 있다. 기본 경우에 해당되는 문제만 풀고 나머지 복잡한 경우는 재귀를 통해 해결할 수 있는 유형의 문제는 재귀 방식이 잘 맞다.

> 재귀는 큰 문제의 풀이법을 작은 문제의 같은 풀이법으로 구성할 수 있을 때 사용되는 문제 해결 기법이다.

- 재귀 주의 사항
  1. 재귀에는 항상 **종료 조건**이 있어야 한다. 재귀 호출을 멈추고 종료하는 조건을 말한다.
  2. 재귀함수는 **전체 작업의 일부만 수행**하고 나머지는 재귀 호출에 위임한다.

- 재귀 함수의 구성요소
  1. 더 큰 범위의 풀이법을 같은 형태지만 더 작은 범위의 인수를 가진 풀이법을 사용해 정의하여 구체화하기
  2. 종료 조건을 지정하기 (없으면 재귀 호출을 무한히 반복한다.)

​      

### 예제 : 1에서 n까지 양의 정수의 합을 계산하기

```typescript
type NumberFunc = (num: number, total?: number) => number;

const sum: NumberFunc = (num) => {
  if(num <= 1) retur num;		// 종료 조건
  return num + sum(num - 1);
}

const tailSum: NumberFunc = (num, total = 0) => {
  if(num < 1) return total;	
  return tailSum(num - 1, total + num);
}


// 재귀호출 없이 loop를 이용해서 작성
const loopSum: NumberFunc = (num) => {
  let sum: number = 0;
  for(let i=1; i<=num; i++) {
    sum += i;
  }
  return sum;
}
```

> 좋은 코드란?
>
> 쉬운 코드 와 복잡한 코드 중에 선택해야 한다면, 성능이나 메모리의 이점이 있지 않은 한 쉬운 코드가 좋다.

​      

#### 연습문제

##### 팩토리얼 함수를 재귀, 비재귀로 작성해보자

```typescript
type NumberFunc = (num: number, total?: number) => number;

const recurFact: NumberFunc = (num) =>
  num <= 1 ? 1 : num * recurFact(num - 1);
const tailFact: NumberFunc = (num, total = 1) =>
  num <= 1 ? total : tailFact(num - 1, total * num);

const loopFact: NumberFunc = (num) => {
  let result: number = 1;
  for(let i=2; i<=num; i++) {
    result *= i;
  }
  return result;
}
```

​      

##### 배열의 값을 누적합으로 갱신하는 함수를 작성해보자

ex) `[1,2,3,4,5,6] => [1,3,6,10,15,21]`

```typescript
type NumberArrFunc = (arr: number[], idx?: number) => number[] | number;

const a : NumberArrFunc = (arr, idx = arr.length-1) => {
  if(0 === idx) return arr[idx];
  return arr[idx] += a(arr, idx-1) as number;
}

const accumArr: NumberArrFunc = (arr, idx = 1) => {
  if (arr.length === idx) return arr;
  arr[idx] += arr[idx - 1];
  return accumArr(arr, idx + 1);
};
```

​      

### 예제 : 점화식으로 제곱 계산하기

어떤 수의 n승은 다음과 같은 점화식으로 정의가 가능하다.

```
x^n =  x * x^(n-1)  // n > 1  일 때
       1            // n == 1 일 떄
```

```typescript
type NumberFunc = (x: number, n: number ) => number;

const power : NumberFunc = (x, n) => {
  
  // 종료 조건 2개
  if(n === 0) return 1;
  if(x === 1) return x;
  return x * power(x, n-1);
}
```

​      

#### 함수 작성시 주의 사항 4가지

1. 함수는 목적 지향적이고 모호한 결과가 반환되어서는 안된다.
2. 함수가 수행되는 데 필요한 시간을 짧을 수록 좋다
3. 함수가 사용하는 메모리의 크기는 작을 수록 좋다.
4. 함수는 이해하기 쉬워야 한다.

> 같은 문제를 비슷한 노력으로 해결할 수 있다면 재귀 호출을 사용하지 않는 쪽으로 구현하는 것이 바람직하다. 만들어진 프로그램을 실행해보면 재귀호출을 사용하지 않는 경우가 실행도 빠르고 필요한 메모리의 양도 작다.

​      

### 예제 : 하노이의 탑

하노이의 탑은 3개의 기둥이 있ㅇ며 각 기둥에는 출발지, 목적지, 임시 가 있다. 그리고 n개의 서로 다른 크기의 원반이 있으며 이 원반은 어느 기둥에서 꽂을 수 있다. 원반에는 2가지 제약 조건이 있다.

1. 한 번에 하나의 원반만 옮길 수 있다.
2. 작은 원반 위에 큰 원반이 위치하도록 옮길 수 없다.

3개의 원반을 1번 기둥 -> 3번 기둥으로 옮긴다면 아래와 같이 할수 있다

```
[1] 1 -> 3
[2] 1 -> 2
[1] 3 -> 2
[3] 1 -> 3
[1] 2 -> 1
[2] 2 -> 3
[1] 1 -> 3
```

위의 경우를 잘 보면 이렇게 파악할 수 있다

```
[1][2][1]
[3]
[1][2][1]

다시 2를 보면
[1]
[2]
[1]

즉 하노이 탑 함수 H가 있다면
H(3) = H(2) 3이동 H(2)
H(2) = H(1) 2이동 H(1)
H(1) = 1이동

즉 H(1)은 베이스 조건이라고 보면 되고 H(2), H(3)... 은 재귀호출을 하면 되는 것이다.
```

따라서 함수로 정리하면 이렇게 만들 수 있다.

```typescript
// 모든 원반을 S에서 D로 옮긴 상태까지의 과정을 출력하는 함수

type StrNum = string | number;
type HanoiProps = (start: StrNum, destination: StrNum, extra: StrNum, ring: number) => void;

const towerOfHanoi: HanoiProps = (start, destination, extra, ring) => {
  // 함수 호출전에 막으면 횟수를 많이 줄일 수 있다.
  if (ring === 1) return console.log(`${ring}을 ${start}에서 ${destination}으로 이동`);
	//if(ring === 0) return;
  
  towerOfHanoi(start, extra, destination, ring - 1);
  console.log(`${ring}을 ${start}에서 ${destination}으로 이동`);
  towerOfHanoi(extra, destination, start, ring - 1);
};


towerOfHanoi(1, 2, 3, 3);
towerOfHanoi('S','D','E',3);
```

또한 2^n승으로 함수 호출이 증가하기 때문에 등비수열 합 공식으로 총 호출 횟수를 구할수 있다.

```
a(r^n-1) / r - 1 ==> 2^n - 1;
```

즉 원반 10개를 넣으면 1023번의 호출이 이루어지는 것이다. 

효율은 그다지 좋지 못하지만, 재귀가 시간과 메모리의 측면에서 문제해결에 도움이 된다는 것을 알 수 있다.

​     

### 선행 재귀와 후행 재귀

- 선행 재귀 (head recursion) : 함수가 작업을 수행하기 전에 재귀 호출하는 경우
- 후행 재귀 (tail recursion) : 함수가 작업을 수행한 후에 마지막에 재귀 호출하는 경우

```typescript
class MyNode<T> {
  public next: MyNode | null = null;
  public data?: T;

  constructor(value?: T) {
    this.data = value;
  }
}

class LinkedList<T> {
  protected NONE: MyNode<T>;
  protected tail: MyNode<T>;

  constructor() {
    this.NONE = new MyNode<T>();
    this.tail = this.NONE;
    this.NONE.next = this.NONE;
  }
  public add(value: T): void {
    this.tail.next = new MyNode<T>(value);
    this.tail = this.tail.next;
    this.tail.next = this.NONE;
  }
  public getValue(idx: number): T | undefined {
    const result = this.getNode(idx);
    return result ? result.data : result;
  }
  public getNode(idx: number): MyNode<T> | undefined {
    let curr: MyNode<T> = this.NONE;
    for (let i = 0; i <= idx; i++) {
      curr = curr.next;
      if (curr === this.NONE) return undefined;
    }
    return curr;
  }
}
class TraverLinkedList<T> extends LinkedList<T> {
  public isNone(target: MyNode<T> | undefined | null) {
    return target === this.NONE || target === null || target === undefined;
  }
  public traverseHead(head?: MyNode<T>): void {
    if (this.isNone(head)) return;

    this.traverseHead(head!.next);
    console.log(head!.data);
  }
  public traverseTail(head?: MyNode<T>): void {
    if (this.isNone(head)) return;

    console.log(head!.data);
    this.traverseTail(head!.next);
  }
}

const list: TraverLinkedList<string> = new TraverLinkedList();
["a", "b", "c", "d", "e"].forEach((one) => list.add(one));

list.traverseHead(list.getNode(0)); // 역방향으로 출력
list.traverseTail(list.getNode(0)); // 정방향으로 출력

```



후행 재귀는 루프를 사용하는 형태로 바꾸기 쉽다. 따라서 후행 재귀로 작성한 다음에 가능하면 루프로 바꿔서 하는 것이 좋다. 



### 재귀를 사용한 문제 해결



## 1.2 재귀 호출과 메모리

### 프로세스 주소 공간



### 재귀 호출을 사용할 때와 사용하지 않을 때의 메모리 상태 비교



### 메모리 배치를 알면 문제 풀이에 도움이 됩니다



### 마치며

1\. 함수가 재귀함수라면 스택 영역에 이 함수의 활성 레코드가 여러개 생성될 수 있으며, 활성 레코드가 여러 개인 함수가 있다면 이 함수는 반드시 재귀함수이다.

2\. 전역 변수와 정적 변수는 상수를 사용해서만 초기화할 수 있다.

3\. 로드타임 변수의 메모리는 모든 함수의 호출 이전에 할당된다.

4\. 로그타임 변수의 메모리는 프로그램의 실행이 종료된 다음에야 해제 된다.



### 

