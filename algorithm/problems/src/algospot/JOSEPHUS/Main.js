//
/*

https://algospot.com/judge/submission/detail/711487

2
6 3
40 3

return 3 5
return 11 26
*/

start(parsing, solution);

function solution(N, K) {
  const list = new LinkedList();
  for (let i = 0; i < N; i++) list.push(i + 1);

  list.setCurrent();

  while (list.length > 2) {
    list.delCurrent();
    list.next(K);
  }
  return list.toString();
}

function parsing(input) {
  return input
    .trim()
    .split(" ")
    .map((num) => parseInt(num));
}

function start(parsing, solution) {
  const inputs = [];
  const result = [];

  require("readline")
    .createInterface(process.stdin, {})
    .on("line", function (line) {
      inputs.push(line);
    })
    .on("close", function () {
      const count = parseInt(inputs[0]);

      for (let i = 1; i <= count; i++) {
        const arr = parsing(inputs[i]);
        result.push(solution(...arr));
      }
      console.log(result.join("\n"));
    });
}

class LinkedList {
  constructor() {
    const NONE = new Node();
    this.NONE = NONE.prev = NONE.next = NONE;
    this.length = 0;
  }
  get first() {
    return this.NONE.next;
  }
  get last() {
    return this.NONE.prev;
  }
  push(value) {
    const lastNode = this.last;
    this.NONE.prev = lastNode.next = new Node(value, lastNode, this.NONE);
    this.length++;
  }
  setCurrent() {
    this.current = this.first;
  }
  delCurrent() {
    let current = this.current;
    const prevNode = current.prev;
    const nextNode = current.next;

    prevNode.next = nextNode;
    nextNode.prev = prevNode;
    current.reset();

    this.current = nextNode !== this.NONE ? nextNode : nextNode.next;
    this.length--;
  }
  next(count) {
    let newCurrent = this.current;
    while (--count > 0) {
      this.current =
        this.current.next !== this.NONE
          ? this.current.next
          : this.current.next.next;
    }
  }
  toString() {
    return `${this.first.value} ${this.last.value}`;
  }
}

class Node {
  constructor(value, prev, next) {
    this.prev = prev;
    this.next = next;
    this.value = value;
  }
  reset() {
    this.prev = this.next = null;
  }
}
