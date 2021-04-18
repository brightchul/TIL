# js로 만드는 간단한 트랜스파일링 구현 

출처 : https://blog.mgechev.com/2017/09/16/developing-simple-interpreter-transpiler-compiler-tutorial/

### 왜 알아야 할까?

- 사용중인 프로그래밍 언어가 어떻게 작동하는지 더 잘 이해하고, 더 효율적인 프로그램을 개발할 수 있습니다.
- 필요에 따라서 사용할 일이 생길수도 있습니다. (설정 파일, 네트워크 메세지 파싱 등)
- DSL (Domain Specific Language) 작성에 도움이 될수 있습니다. 



### 커버할 범위

25라인 짜리 매우 간단한 컴파일러를 개발 할 것입니다.

- lexical analysis를 위한 모듈
- syntax analysis를 위한 모듈
  - EBNF 문법을 기반으로 한 파서
  - recursive descent parsing algorithm을 이용한 파서
- Code generator



### 간단한 Prefix 언어

```
예) 곱셈 3 뺄셈 2 덧셈 1 3 4
```



### 규칙

- 곱셈, 나눗셈, 덧셈, 뺄셈
- 각 문자열들은 공백으로 구분 됩니다.
- 자연수만 지원합니다.

```typescript
const 곱셈 = (...operands) => operands.reduce((a,c) => a * c, 1);
const 나눗셈 = (...operands) => operands.reduce((a,c) => a / c);
const 덧셈 = (...operands) => operands.reduce((a,c) => a + c, 0);
const 뺄셈셈 = (...operands) => operands.reduce((a,c) => a - c);
```



다음 규칙을 통해서 예시 코드는 아래와 같이 됩니다.

```
곱셈 (3, 뺄셈 (2, 덧셈( 1, 3, 4) ) )
or
3 * (2 - ( 1 + 3 + 4))
```



## 컴파일러의 프론트엔드 개발

모든 컴파일러의 프론트 엔드는 일반적으로 Lexical Analysis, Syntax Analysis 모듈이 있습니다.



### Lexical Analyzer 개발

Lexical analysis 단계에서는 프로그램의 입력 문자열을 token이라고 하는 작은 조각으로 나누는 역할을 합니다. 토큰은 일반적으로 그들의 타입 (숫자, 연산자, 키워드, 식별자 등), 프로그램에서의 위치, 하위 문자열에 대한 정보를 전달합니다.  위치(position)은 보통 잘못된 문법 구조의 경우 오류를 보고하는데 사용됩니다.

예를 들어 js 프로그램에서는 

```js
if (foo) {
  bar();
}
```

를 다음과 같이 생성을 합니다.

```json
[
  {
    lexeme: 'if',
    type: 'keyword',
    position: {
      row: 0,
      col: 0
    }
  },
  {
    lexeme: '(',
    type: 'open_paran',
    position: {
      row: 0,
      col: 3
    }
  },
  {
    lexeme: 'foo',
    type: 'identifier',
    position: {
      row: 0,
      col: 4
    }
  },
  ...
]
```



하지만 이글에서는 최대한 간단하게 유지합니다.

```js
const lex = str => str.split(' ')                // 공백 문자로 분할
                      .map(s => s.trim())        // 하위 문자열중 공백문자 제거
                      .filter(s => s.length);    // 제거된 빈문자열 필터링
```

결과는 아래와 같습니다.

```js
lex('곱셈 3 뺄셈 2 덧셈 1 3 4');
// ["곱셈", "3", "뺄셈", "2", "덧셈", "1", "3", "4"]
```



## Parser 개발

 syntax analyzer (또는 파서)는 토큰목록에서 추상 구문 트리 (Abstract Synctax Tree)를 생성하는 컴파일러 모듈이다. 이 과정에서 파서는 잘못된 프로그램의 경우 syntax error를 생성할 수 있습니다.

일반적으로 파서는 문법를 기반으로 해서 구현됩니다. 이 예제에서의 문법은 다음과 같습니다.

```
digit = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
num = digit+
op = 덧셈 | 뺄셈 | 곱셈 | 나눗셈
expr = num | op expr+
```

파서는 위의 문법에 따라 다음 AST를 생성합니다.

```
곱셈 3 뺄셈 2 덧셈 1 3 4
```

```
곱셈
├----ㄱ
3     뺄셈
       ├----ㄱ
       2    덧셈
              ├---ㅜ---ㄱ
              1   3    4
```



## 알고리즘

```javascript
const Op - Symbol('op');
const Num = Symbol('num');

const parser = tokens => {
  let c = 0;
  const peek = () => token[c];
  const consume = () => tokens[c++];
  const parseNum = () => ({val: parseInt(consume()), type: Num});
  
  const parseOp = () => {
    const node = {val: consume(), type: Op, expr:[]};
    while(peek()) node.expr.push(parseExpr());
    return node;
  };
  
  const parseExpr = () => /\d/.test(peek()) ? parseNum() : parseOp();
  
  return parseExpr();
}
```



### Node type

```javascript
const Op = Symbol('op');
const Num = Symbol('num');
```

AST에 포함할 여러 노드 타입들을 정의합니다. 여기서 숫자와 연산자만 가질 것입니다. 다음은 예시입니다.

```json
42

{
  type: Num,
  val: 42
}
```

```json
덧셈 2 3 4

{
  type: Op,
  val: 'sum',
  expr: [
    {type: Num, val: 2},
    {type: Num, val: 3},
    {type: Num, val: 4},
  ]
}
```



### Parser

`parse` 라는 함수를 정의하고, 그 안에 5개의 함수를 추가로 정의합니다.

- peek : token에서 인덱스 c의 값을 반환합니다.
- consume : token에서 인덱스 c의 값을 반환하면서 c를 증가시킵니다.
- parseNum : consume으로 값을 가져온 다음 새로운 숫자 토큰을 반환합니다.
- parseOp : 다음 단락에서 자세히 설명합니다.
- parseExpr : 현재 토큰이 숫자인지 확인하고 숫자면 parseNum, 아니면 parseOp를 호출합니다.



### Operations 파싱

```javascript
const parseOp = () => {
  const node = {type: Op, val: peek(), expr : []};
  while(peek()) node.expr.push(parseExpr());
  return node;
}
```

parseExpr에서 숫자 문자열이 아니면 parseOp를 호출하고 그 결과 새로운 operation node를 생성한다. 물론 현실에서 적용할 때에는 문법이 맞지 않을 경우 syntax error를 발생시킬 것이지만, 여기서는 추가적인 validation을 하지는 않을 것이다. 

입력된 문자열의 끝에 다다를 때까지 계속 loop를 돌면서 expr에 하위 표현식들을 추가하다가 끝에 다다르면 해당 node를 반환 할 것이다. 



### Recursive Descent Parsing

```
// EBNF 문법 규칙
digit = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
num = digit+
op = 덧셈 | 뺄셈 | 곱셈 | 나눗셈
expr = num | op expr+
```

Do they now may make a bit more sense? `expr` looks very much like `parseExpr`, where we parse either a `num`ber or an `op`eration. Similarly, `op expr+` looks very much like `parseOp` and `num` like `parseNum`. In fact, very often parsers are generated directly from the grammars since there’s a direct connection between both with the [recursive descent parsing algorithm](https://en.wikipedia.org/wiki/Recursive_descent_parser).

And in fact, we just developed a simple recursive descent parser! Our parser was quite simple (well, we have only 4 **production rules** in the grammar) but you can imagine how complex the parser of a real-life programming language is.

It’s extremely convenient to develop the grammar of a language before writing the actual parser in order to observe a simplified model of it. The parser contains a lot of details (for instance a lot of syntax constructs of the language you’re developing it with), in contrast to the grammar which is extremely simplified and minimalistic.



### 트랜스파일러 개발

```javascript
const transpile = ast => {
  const opMap = {"덧셈" : '+', "뺄셈" : '-', "곱셈" : '*', "나눗셈" : '/'};
  
  // 파서가 생성한 ast를 인수로 받는다.
  const transpileNode = ast => ast.type === Num ? transpileNum(ast) : transpileOp(ast);
  
  // 숫자 반환
	const transpileNum = ast => ast.val;
  
  // 연산을 javascript 산술 연산으로 변환
  const transpileOp = ast => `(${ast.expr.map(transpileNode).join(' ' + opMap[ast.val] + ' ')})`;
  return transpileNode(ast);
}
```



### 모든 것들을 조립

```javascript
const program = '곱셈 3 뺄셈 2 덧셈 1 3 4';
transpile(parse(lex(program)));
// (3 * (2 - (1 + 3 + 4)))
```



## 출처 

https://blog.mgechev.com/2017/09/16/developing-simple-interpreter-transpiler-compiler-tutorial/

