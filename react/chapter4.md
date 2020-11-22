# 4장 리액트 실전 활용법

## 4.1 가독성과 생산성을 고려한 컴포넌트 코드 작성법

### 4.1.1 추천하는 컴포넌트 파일 작성법

1) 파일의 최상단에는 속성값의 타입을 정의한다. 컴포넌트를 사용하기 위해서 컴포넌트의 속성값을 알아야 하기 때문이다.

2) 컴포넌트 함수의 매개변수는 명명된 매개변수로 정의하는게 좋다. 이름 없는 컴포넌트로 만들면 리액트 개발자 도구에서 디버깅이 힘들다.

3) 컴포넌트 바깥의 변수와 함수는 파일의 가장 밑에 정의한다. 되도록이면 변수는 상수 변수(const)로 정의 하는게 좋다.  상수는 대문자로 작성한다. 큰 객체 생성코드는 컴포넌트 바깥에서 생성하여 써야 렌더링시 불필요한 객체 생성을 피할수 있다.

```tsx
interface MyComponentProps {

}

const MyComponent : React.FC<MyComponentProps> = ({prop1, prop2}) => {
 	// ... 
}

const COLUMNS = [
  {id:1, name: 'phoneNumber', width: 200, color: 'white'},
  {id:2, name: 'city', width: 100, color 'grey'},
];

const URL_PRODUCT_LIST = '/api/products';
function getTotalPrice({price, total}) {
  // ...
}
```

​      

#### 서로 연관된 코드를 한곳으로 모으기

코드를 모을 때에는 연관된 코드끼리 모으는 게 좋다. 

```tsx 
function Propfile({userId}) {
  // 사용자 정보를 가져오는 기능을 한곳으로 모았다.
  const [user, setUser] = useState(null);
  useEffect(() => {
    getUserApi(userId).then(data => setUser(data));
  }, [userId]);

  // 창의 너비를 가져온느 기능을 한곳으로 모았다.
  const [width, setWidth] = useState(window.innerWidth);
  useEffect(() => {
    const onResize = () => setWidth(window.innerWidth);
    window.addEventListener("resize", onResize);
    return () => {
      window.removeEventListener("resize", onResize);
    }
  }, []);

}
```

컴포넌트 코드가 복잡해지면 각 긱능을 커스텀 훅으로 분리하는 것도 좋은 방법이다. 

기능을 커스텀 훅으로 분리하면 같은 기능을 다른 곳에서라도 사용하기 좋다. 다만 컴포넌트 코드가 복잡하지 않은 경우에는 커스텀 훅이 오히려 가독성을 떨어뜨릴수도 있다.

```tsx
function Propfile({userId}) {
  const user = useUser(userId);
  const width = useWindowWidth();
}

function useUser(userId) {
  const [user, setUser] = useState(null);
  useEffect(() => {
    getUserApi(userId).then(data => setUser(data));
  }, [userId]);
  
  return user;
}

function useWindowWidth() {
   const [width, setWidth] = useState(window.innerWidth);
  useEffect(() => {
    const onResize = () => setWidth(window.innerWidth);
    window.addEventListener("resize", onResize);
    return () => {
      window.removeEventListener("resize", onResize);
    }
  }, []);
  return width;
}
```

​      

### 4.1.2 속성값 타입 정의 하기

타입을 사용하게 되면 타입 에러를 사전에 검사할 수 잇다. 또한 타입 정의를 하게 되면 컴포넌트를 사용하는 사람 입장에서 속성 값의 정보를 파악하기가 더 쉬워진다. 

해당 부분은 prop-types를 이용하지만 우리는 타입스크립트를 이용하기 때문에 생략한다.

​      

### 4.1.3 가독성을 높이는 조건부 렌더링 방법

조건부 렌더링 (conditional redering) : 특정 값에 따라 선택적으로 렌더링 하는 방법

```tsx
// 삼항 연산자를 사용한 조건부 렌더링
function Greeting({isLogin, name, cash}) {
  return (
  	<div>
    	저희 사이트에 방문해 주셔서 감사합니다.
      {isLogin ? (
      	<div>
          <p>{name}님 안녕하세요</p>
          <p>현재 보유하신 금액은 {cash} 입니다.</p>
        </div>
      ) : null}
    </div>
  )
}
```

```tsx
// && 연산자를 사용한 조건부 렌더링
function Greeting({isLogin, name, cash}) {
  return (
  	<div>
    	저희 사이트에 방문해 주셔서 감사합니다.
      {isLogin && (
      	<div>
        	<p>{name}님 안녕하세요.</p>
          <p>현재 보유하신 금액은 {cash}원 입니다.</p>
        </div>
      )}
    </div>
  )
}
```

> && 연산자 사용시 주의할 점
>
> && 연산자를 사용할 때에는 변수가 숫자 타입인 경우 0은 거짓이고, 문자열 타입인 경우 빈 문자열 ''도 거짓이다. 이것 때문에 의도하지 않은 동작이 일어날 수 있다. 
> 자신이 사용할 값이 falsy, truthy인지, 아니면 undefined, null에 대해서만 하는 것인지 잘 파악해서 해야 한다.



변수가 배열인 경우에는 기본값으로 빈 배열을 넣어주는게 좋다. 그러면 `student &&` 를 입력하지 않아도 바로 `student.map` 이렇게 확인 과정없이 바로 사용할 수 있기 때문이다.변수가 undefined, null을 가질 수 있다면, 컴포넌트 함수에서 변수를 사용할 때마다 && 를 작성해줘야 한다. 배열의 기본값을 빈 배열로 설정하면 코드가 간결해진다. 



조건에 따라 아무것도 렌더링하지 않는 경우에는 null을 반환하기도 한다. 또는 해당 컴포넌트를 사용하는 부모 컴포넌트에서 조건에 따라 자식 컴포넌트를 보이거나 가릴 수도 있다. 

후자의 경우 해당 컴포넌트가 마운트와 언마운트를 반복할 수 있다는 점을 인지해야 한다. 단점은 성능상 안좋은 영향을, 장점은 부모 컴포넌트에서 조건을 작성해서 자식 컴포넌트입장에서는 로직이 더 간단해 진다. 

​      

### 4.1.4 관심사 분리를 위한 프레젠테이션, 컨테이너 컴포넌트 구분하기

> 관심사의 분리 : 복잡한 코드를 비슷한 기능을 하는 코드끼리 모아서 별도로 관리하는 것을 말한다.



비즈니스 로직과 상탯값은 일부 컴포넌트로 한정해서 관리하는게 좋다.컴포넌트가 비즈니스 로직이나 상탯값을 가지고 있으면 재사용하기 힘들다. 컴포넌트에 비즈니스 로직이나 상탯값이 있어서 재사용을 못하고 새로운 컴포넌트를 만들면 코드 중복이 발생할 수 있다.

비즈니스 로직과 상탯값의 유무에 따라 프레젠테이션과 컨테이너 컴포넌트로 구분한다.



#### 프레젠테이션 컴포넌트

- 비즈니스 로직이 없다.
- 상탯값이 없다. 단 아무스 오버같은 UI효과를 위한 상탯값은 제외한다. 

- 컴포넌트를 프레젠테이션과 컨테이너로 구분하고 폴더도 별도로 관리하는게 좋다. 일반적으로 프레젠테이션 컴포넌트 코드가 가독성이 더 좋고 재사용성도 높다. 

​      

​     

## 4.2 useEffect 훅 실전 활용법

### 4.2.1 의존성 배열을 관리하는 방법

의존성 배열은 useEffect 훅에 입력하는 두 번째 매개변수이다. 의존성 배열의 내용이 변경됐을 때 부수 효과 함수가 실행된다.

의존성 배열은 잘못 관리하면 쉽게 버그로 이어지므로 가능하면 입력하지 않는 게 좋다. 몇가지 예제를 통해 의존성 배열을 관리하는 방법을 알아보자.

​      

#### 부수 효과 함수에서 API를 호출하는 경우

부수 효과 함수에서 API를 호출한다면 불필요한 API호출이 발생하지 않도록 주의해야 한다. 

```tsx
useEffect(
  () => {fetchUser(userId).then(data => setrUser(data));}
  , [userId]
)
```

나중에 부수 효과 함수를 수정할 때 새로 추가된 변수를 빠짐없이 의존성 배열에 추가해야 한다. 

실수로 의존성 배열에 추가하지 않는 일을 방지하기 위해 eslint에서 exhaustive-deps 규칙을 만들어서 제공한다.

> eslint -> exhaustive-deps : 잘못 사용된 의존성 배열을 찾아서 알려준다. (강추)

​       

#### useEffect 훅에서 async await 함수 사용하기

useEffect 훅에서 async await 함수를 부수 효과 함수로 사용하면 에러가 난다. 반환 값이 항상 '함수 타입' 이어야 하기 때문이다. 

async await 함수는 promise 객체를 반환하기 때문에 부수 효과 함수가 될 수 없다. 따라서 사용하기 위해서는 부수 효과 함수 내에서 async await 함수를 만들어서 효출해야 한다.

```tsx
useEffect(() => {
  async function fetchAndSetUser() {
    const data = await fetchUser(userId);
    setUser(data);
  }
  fetchAndSetUser();
}, [userId]);
```

만약 fetchAndSetUser함수를 다른 곳에서도 쓴다면 아래와 같이 밖으로 뺄수 있다.

```tsx
function Profile({userId}) {
  const [user, setUser] = useState();
  
  // 그냥 단순히 빼주면 Propfile이 호출될 때마다 fetchAndSetUser가 새로 생성되고 따라서 매번 useEffect에서 호출하게 된다.
  // 이것을 방지하기 위해서 useCallback을 사용해서 userId가 변경되었을 때에만 fetchAndSetUser가 변경되도록 해준다.
  const fetchAndSetUser = useCallback(
  	async needDetail => {
      const data = await fetchUser(userId, needDetail);
      setUser(data);
    }, [userId]
  );
  
  // 의존성 배열에 fetchAndSetUser를 넣어서 변경되었을 때에만 실행할 수 있게 해준다.
  useEffect(() => {
    fetchAndSetUser(false);
  }, [fetchAndSetUser]);
}
```

​      

### 4.2.2 의존성 배열을 없애는 방법

가능하다면 의존성 배열을 사용하지 않는 게 좋다. 관리하는데 생각보다 많은 노력이 들어가기 때문이다, 특히 속성 값으로 전달되는 함수를 의존성 배열에 넣었을 때 그 함수는 useCallback등을 사용해서 자주 변경되지 않도록 신경 써서 관리해야 한다.

​       

#### 부수 효과 함수 내에서 분기 처리하기

의존성 배열 대신 부수 효과 함수 내에서 실행 시점을 조절할 수 있다.

```tsx
function Profile({userId}) {
  const [user, setUser] = useState();
  async function fetchAndSetUser(needDetail) {
    const data = await fetchUser(userId, needDetail);
    setUset(data);
  }
  useEffect(() => {
    // if문으로 호출 시점을 관리한다. 
    if(!user||user.id !== userId) {
      fetchAndSetUser(false);
    }
  });  // 의존성 배열을 사용하지 않아도 된다.
  // ...
}
```

​       

#### useState의 상탯값 변경 함수에 함수 입력하기

이전 상탯값을 기반으로 다음 상탯값을 계산하기 위해 상탯값을 의존성 배열에 추가하는 경우가 있다. 이런 경우 상탯값 변경 함수에 함수를 입력하면 함탯값을 의존성 배열에서 제거할 수 있다.

```tsx
// 의존성 배열을 사용해서 이전값을 사용한다.
function MyComponent() {
  const [count, setCount] = useState(0);
  useEffect(() => {
    function onClick() {
      setCount(count + 1);	// 의존성 배열에 있는 값을 이용한다.
    }
    window.addEventListener("click", onClick);
    return () => window.removeEventListener("click", onClick);
  }, [count]) // 의존성 배열에 값을 넣어놓는다.
}
```



```tsx
// 상탯값 변경 함수에 함수 입력으로 이전값을 이용한다. 
function MyComponent() {
  const [count, setCount] = useState(0);
  useEffect(() => {
    function onClick() {
      setCount(prev => prev + 1);	// 이전값이 setCount에 자동으로 들어옴
    }
    window.addEventListener("click", onClick);
    return () => window.removeEventListener("click", onClick);
  }) // 더이상 의존성 배열에 값이 없다.
}
```

​       

#### useReducer 활용하기

여러 상탯값을 참조하면서 값을 변경할 때는 useReducer 훅을 사용하는게 좋다. useReducer를 사용하면 다양한 액션과 상탯값을 관리하기 용이하고, 상탯값 변경 로직을 여러 곳에서 재사용하기에도 좋다.

```tsx
// useReducer를 사용하지 않았을 때
function Timer({initialToalSeconds}) {
  const [hour, setHour] = useState(Math.floor(initialTotalSeconds / 3600));
  const [minute, setMinute] = useState(Math.floor((initialTotalSeconds % 3600) / 60));
  const [second, setSecon] = useState(Math.floor(initialTotalSecond % 60));
  
  useEffect(() => {
    const id = setInterval(() => {
      if(second) {
        setSecond(second - 1);
      } else if (minute) {
        setSecond(59);
      } else if(hour) {
        setHour(hour-1);
        setMinute(59);
        setSecond(59);
      }
    }, 1000);
    return () => clearInterval(id);
  }, [hour, minute, second]);
  
  // ....
}
```

```tsx
// useReducer를 사용했을 때
function Timer({initialTotalSeconds}) {
  const [state, dispatch] = useReducer(reducer, {
    hour: Math.floor(initialTotalSeconds / 3600),
    minute: Math.floor(initialTOtalSeconds % 3600) / 60,
    second: initialTotalSeconds % 60
  });
  
  const {hour, minute, second} = state;
  useEffect(() => {
    const id = setInterval(dispatch, 1000);	// dispatch는 변경되지 않으므로 의존성 배열 쓰지 않는다.
    return () => clearInterval(id);
  });
  // ...
}

// 상탯값을 변경하는 로직은 reducer 함수에서 구현한다.
function reducer(state) {
  const {hour, minute, second} = state;
  if(second) {
    return {...state, second: second -1};
  }
  if(minute) {
    return {...state, minute: minute -1, second: 59};
  }
  if(hour) {
    return {hour: hour-1, minute: 59, second: 59};
  }
	return state;
}
```

​       

#### useRef 활용하기

속성값으로 전달되는 함수가 자주 변경되는 경우가 많다. 해당 속성값이 렌더링 결과에 영향을 주는 값이 아니라면 useRef훅을 이용해서 의존성 배열을 제거할 수 있다. 

```tsx
// 문제 상황

function MyComponent({onClick}) {
  useEffect(() => {
    window.addEventListener("click", () => {
      onClick();
      // ...
    });
    // 연산량이 많은 코드
  }, [onClick]);	 
  // ...
}
```

속성값으로 전달된 함수는 함수 내용은 그대로인데 렌더링할 때마다 변경되는 경우가 많다. 이로인해 부수 효과 함수가 불필요하게 자주 호출된다. 이를 해결하기 위해 useRef 훅을 사용하는게 좋을 수 있다.

```tsx
function MyComponent ({onClick}) {
  const onClickRef = useRef();
  
  useEffect(() => {
    // useRef에는 렌더링 결과와 무관한 값만 저장한다. 
    // useRef에 저장된 값이 변경돼도 컴포넌트가 다시 렌더링 되지 않기 때문이다.
    onClickRef.current = onClick;
  });
  
  useEffect(() => {
    window.addEventListener("click", () => {
      onClickRef.current();
      // ...
    });
    // ...
  });  // 부수 효과 함수에 사용된 useRef 값은 의존성 배열에 추가할 필요가 없다.
  // ...
}
```

> useRef 값을 useEffect안의 부수효과 함수에서 변경하는 이유
>
> 부수효과 함수에서 useRef 값을 수정하는 이유는 나중에 concurrent 모드를 염두해 두어서이다. concurrent 모드로 동작할 때는 컴포넌트 함수가 실행되었다고 하더라도 중간에 렌더링이 취소될 수 있다. 렌더링은 취소되었는데 useRef에 잘못된 값이 저장될 수 있기 때문에 컴포넌트 내에서 직접적으로 수정하면 안된다. 단, concrrent 모드로 동작하지 않는 리액트 버전에서는 문제가 되지 않는다.

​       

## 4.3 렌더링 속도를 올리기 위한 성능 최적화 방법

리액트가 실행될 때 가장 많은 CPU 리소스를 사용하는 것은 렌더링이다. 화면을 그리는 과정에서 대부분의 연산은 컴포넌트 함수의 실행과 가상 돔으로 발생한다. 리액트에서 최초 렌더링 이후에는 데이터 변경 시 렌더링을 하는데, 이 때 다음과 같은 단계를 거친다.

**1) 이전 렌더링 결과를 재사용할지 판단한다.**

- 속성값, 상탯값만 비교하고 생략가능하다. 
- 클래스형 컴포넌트 shouldComponentUpdate 메서드, 함수형 컴포넌트에서는 React.memo를 이용해서 구현가능

**2) 컴포넌트 함수를 호출한다.**

**3) 가상 돔끼리 비교해서 변경된 부분만 실제 돔에 반영한다.**



​       

### 4.3.1 React.memo로 렌더링 결과 재사용하기

React.memo 함수로 감싼 컴포넌트라면 속성값 비교 함수가 호출된다. 이 함수는 이전 이후 속성값을 매개변수로 받아서 참(패쓰), 거짓(컴포넌트 함수 실행) 을 반환한다. 컴포넌트를 React.memo함수로 감싸지 않았다면 속성값이 변경되지 않아도 부모 컴포넌트가 렌더링 될 때마다 자식 컴포넌트도 렌더링 된다

물론 감싸지 않았더라도 속성값이 변경되지 않으면 실제 돔도 변경되지 않아서 대부분 문제가 되지 않지만, 렌더링 성능이 중요한 상황에서는 컴포넌트를 React.memo 함수로 감싸서 컴포넌트 함수의 실행과 가상 돔의 계산을 생략할 수 있다.

```tsx
function MyComponent(props) {
  // ...
}

function isEqual(prevProps, nextProps) {
  // true, false 반환
}

React.memo(MyComponent, isEqual);  
// 속성값 비교를 통해서 단계를 생략할 수 있다. 
// 만약 입력하지 않으면 얕은 비교를 수행하는 기본함수가 사용된다.
// 이때 불변객체로 하면 변경 여부를 좀더 빠르게 파악할 수 있어, 속성값을 불변객체로 관리하면 렌더링 성능에 도움이 된다. 
```

> JSX 가 createElement로 변환된 코드를 보면 렌더링 할 때마다 새로운 속성 값 객체가 생성된다. 객체 내부 속성값이 변경되지 않아도 최상위 객체의 참조값은 항상 변경된다. 따라서 리액트는 속성값의 최상위 객체에 직접 연결된 모든 값을 단순 비교한다. 이를 얕은 비교라 부른다.

​       

### 4.3.2 속성값과 상태값을 불변 변수로 관리하는 방법

```tsx
// 문제되는 코드

function Parent() {
  const [selectedFruit, setSelectedFruit] = useState("apple");
  const [count, setCount] = useState(0);
  
  return (
  	<div>
      <p>{`count: ${count}`}</p>
      <button onClick={() => setCount(count + 1)}>increase count</button>
      <SelectFruit
        selected={selectedFruit}
        onChange={fruit => setSelectedFruit(fruit)}		
        // 비록 React.memo를 사용했다고 해도 이부분이 매번 새로 생성되어 컴포넌트 호출된다.
        />
    </div>)
}
```

```tsx
// 1번 setSelectedFruit는 변경되지 않는 것을 이용
function Parent() {
  const [selectedFruit, setSelectedFruit] = useState("apple");
  // ...
  
  return (
  		// ....
      <SelectFruit
        selected={selectedFruit}
        onChange={setSelectedFruit}		
        // 비록 React.memo를 사용했다고 해도 이부분이 매번 새로 생성되어 컴포넌트 호출된다.
        />
      // ...
  )
}


// 2번 상태 변경 외에도 여러가지를 해야 할 때는 useCallback을 사용한다.
function Parent() {
  const onChangeFruid = useCallback(fruit => {
    setSelectedFruit(fruit);
    sendLog({type: "fruid change", value: fruit});
  }, []); // 의존성 배열에 빈배열을 입력했으므로 이 함수는 항상 고정된 값만 가진다.
  
  // ...
  return (
    // ...
    <SelectFruit
      selected={selectedFruit}
      onChange={onChangeFruit}	// useCallback 반환 함수를 속성값으로 입력
      />
    // ...
  )
}
```

​       

#### 객체의 값이 변하지 않도록 관리하기

컴포넌트 내부에서 객체를 정의해서 자식 컴포넌트의 속성값으로 입력하면 자식 컴포넌트는 객체의 내용이 변경되지 않았는데도 속성값이 변경되었다고 인식한다. 이럴 때에는 해당 객체를 컴포넌트 밖으로 빼서 상수 변수로 관리한다.

```tsx
function SelectFruit({selectedFruit, onChange}) {
	// ...
  return (
    <div>
      <Select options={FRUITS} selected={selectedFruit} onChange={onChange} />
      // ......
    </div>)
}

const FRUITS = [
  {name: "apple, price: 500"},
  // .....
]
```

```tsx
// 값을 이용해서 계산되어 만들어지는 값. 이럴 때는 useMemo를 사용한다.
function SelectFruit({selectedFruit, onChange}) {
  // ...
	const filterFruits = useMemo(() => FRUITS.filter(item => item.price <= maxPrice), 
                               [maxPrice]		// maxPrice 변경 전까지는 값이 유지된다.
                              );
    <div>
      <Select options={filterFruits} selected={selectedFruit} onChange={onChange} />
      // ......
    </div>)
}
```

- useMemo 훅은 필요한 경우에만 속성값이 변하도록 만들수도 있다.
- useMemo는 입력된 함수를 최소한으로 실행시킬 수 있다. 불필요한 무거운 연산을 막아줄때도 사용할 수 있다.

> 성능을 최적화하는 코드는 가독성이 안 좋고 유지보수 비용을 증가시키므로 성능 이슈가 발생했을 때 해당하는 부분만 최적화 하도록 한다.

​       

#### 상탯값을 불변 객체로 관리하기

속성값이나 상탯값이 변경되면 반드시 객체들 새로 생성해야 한다. 

```tsx
// 새로 객체 생성이 되지 않는 경우
function SelectFruit({selectedFruit, onChange}) {
  const [fruits, setFruits] = useState(["applce", "banana", "orange"]);
  const [newFruit, setNewFruit] = useState("");
  
  function addNewFruit() {
    fruits.push(newFruit);	// 이렇게 하면 새로운 객체가 안만들어진다. 
    setNewFruit("");
  }
  return (
  	// ....
    // fruits에 추가되어도 동일한 배열객체라서 React.memo를 사용했다면 화면에 새로운 과일이 추가되지 않는다.
    <Select optionas={fruits} selected={selectedFruit} onChange={onChange} />
    <button onClick={addNewFruit}>추가하기</button>
    // ....
  )
} 
```

```tsx
// 불변 객체로 업데이트 하는 방식
// 새로 객체 생성이 되지 않는 경우
function SelectFruit({selectedFruit, onChange}) {
  const [fruits, setFruits] = useState(["applce", "banana", "orange"]);
  const [newFruit, setNewFruit] = useState("");
  
  function addNewFruit() {
    setFruits([...fruits, newFruit]);// 새로운 객체가 만들어진다.
    setNewFruit("");
  }
  return (
  	// ....
    // 다른 fruits 배열 객체이기 때문에 새로 추가되었을 때 React.memo라고 할지라도 화면에 바로 새로 추가 반영이 된다.
    <Select optionas={fruits} selected={selectedFruit} onChange={onChange} />
    <button onClick={addNewFruit}>추가하기</button>
    // ....
  )
} 
```

​       

### 4.3.3 가상 돔에서의 성능 최적화

#### 엘리먼트의 타입 또는 속성을 변경하는 경우

엘리먼트의 타입을 변경하면 해당 엘리먼트의 모든 자식 엘리먼트도 같이 변경된다. 

```tsx
// 엘리먼트의 타입 변경시
function App() {
  const [flag. setFlag] = useState(true);
  useEffect(() => {setTimeout(() => setFlag(prev => !prev), 1000)});
  
  // div엘리먼트와 span 엘리먼트가 변경되면 자식컴포넌트들도 삭제후 전부 다시 그려지며 상탯값도 초기화 된다.
  // 실제 돔일 경우 돔이 삭제후 다시 만들어진다.
  return (
    flag 
    ? 
    (<div> 				
      <Counter /> 
      <p>사과</p>
     </div>) 
    :
    (<span> 
      <Counter /> 
      <p>사과</p>
     </span>);
}
```

```tsx
// 엘리먼트의 속성값을 변경하는 경우
function App() {
  const [flag. setFlag] = useState(true);
  useEffect(() => {setTimeout(() => setFlag(prev => !prev), 1000)});
  
  // 엘리먼트의 속성값을 변경하면 해당 속성만 실제 돔에 반영된다. 
  return (
     <div
       className={flag ? "aaa" : "bbb"}
       style={{color: 'black', backgroundColor: flag ? 'green' : 'red'}}
       > 				
      <Counter /> 
      <p>사과</p>
     </div>
  ); 
}
```

​         

#### 엘리먼트를 추가하거나 삭제하는 경우

일반적으로 새로운 엘리먼트를 추가하거나 삭제하면 해당 엘리먼트만 실제 돔에 추가, 삭제하고 기존 엘리먼트는 건드리지 않는다. 

리액트는 만약 중간에 엘리먼트를 추가하면 그  뒤에 있는 엘리먼트가 변경되지 않았다는 것을 알지 못한다. 따라서 모든 값을 비교해야 하므로 연산량이 많아진다. 따라서 리액트에서는 효율적으로 연산하기 위해 순서 정보를 사용한다.

```tsx
function App() {
  // ...
  if (flag) {
    return (
    	<div>
      	<p>사과</p>
        <p>바나나</p>
      </div>
    )
  } else {
    return (
    	<div>
      	<p>사과</p>
        <p>파인애플</p>
        <p>바나나</p>
      </div>
    )
  }
}
```


만약 2개인 상황에서 가운데에 추가가 되었다면 두번째 요소를 변경하고, 세번째는 새로 추가하게 된다. key 속성값을 이용하면 특정한 값만 실제 돔에 추가할 수 있다. key속성값을 입력하면 리액트는 같은 키를 가지는 요소끼리만 비교해서 변경점을 찾는다.

```tsx
// key 속성값을 입력하는 경우
function App() {
  // ...
  if (flag) {
    return (
    	<div>
      	<p key="apple">사과</p>
        <p key="banana">바나나</p>
      </div>
    )
  } else {
    return (
    	<div>
      	<p key="apple">사과</p>
        <p key="pineapple">파인애플</p>
        <p key="banana">바나나</p>
      </div>
    )
  }
}
```


key 속성값은 리액트가 렌더링을 효율적으로할 수 있도록 우리가 제공하는 추가 정보이다. 만약 key 속성값에 입력할 만한 값이 없다면 차선책으로 배열 내에서 순서(index)정보를 입력할 수도 있다. 하지만 배열 중간에 원소 추가/삭제하는 경우 , 원소의 순서를 변경하는 경우에는 비효율적으로 렌더링 된다. 따라서 key 속성값에 순서 정보를 입력하는 것은 배열의 끝에서만 원소를 추가/삭제하면서 원소 순서를 변경하지 않는 경우에 적합하다.

```tsx
// key 속성값으로 배열의 순서 정보를 입력
function App() {
  // ...
  const fruits = flag ? FRUITS_1 : FRUITS_2;
  return (
  	<div>
    	{fruits.map((item, index) => (
      	<p key={index}>{item}</p>
      ))}
    </div>
  );
}

```









