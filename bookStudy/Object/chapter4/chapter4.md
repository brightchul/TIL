# 4장 설계 품질과 트레이드 오프

이번 장에서는 상태를 표현하는 데이터 중심의 설계를 살펴보고 객체지향 적 설계 구조와의 차이점을 살펴본다.

​    

## 01. 데이터 중심의 영화 예매 시스템

데이터 중심의 관점에서 객체는 자신이 포함하고 있는 데이터를 조작하는 데 필요한 오퍼레이션을 정의한다. 데이터 중심의 관점은 객체의 상태에 초점을 맞추고 객체를 독립된 데이터 덩어리로 바라본다.

상태를 객체 분할의 중심축으로 삼으면 구현에 관한 세부사항이 객체의 인터페이스에 스며들게 되어 캡슐화의 원칙이 무너진다. 결과적으로 상태 변경은 인터페이스의 변경을 초래하며 이 인터페이스에 의존하는 모든 객체에게 변경의 영향이 퍼지게 된다. 따라서 데이터에 초점을 맞추는 설계는 변경에 취약할 수 밖에 없다.

​         

### 데이터를 준비하자

데이터 중심의 설계란 객체 내부에 저장되는 데이터를 기반으로 시스템을 분할하는 방법이다. 데이터 중심의 설계는 객체가 내부에 저장해야 하는 '데이터가 무엇인가'를 묻는 것으로 시작한다. 먼저 Movie에 저장될 데이터를 결정하는 것으로 설계를 시작한다.

```typescript
public class Movie {
    private title: string;
    private runningTime: number;
    private fee: Money;
    private discountConditions: Array<DiscountCondition>;
    
    private movieType: MovieType;
    private discountAmount: Money;
    private discountPercent: number;
}
```

가장 두드러지는 차이점은 할인 조건의 목록(discountCondition)이 인스턴스 변수로 Movie 안에 직접 포함되어 있다는 것이다. 또한 할인 정책을 DiscountPolicy라는 별도의 클래스로 분리했던 이전 예제와 달리 금액 할인 정책에 사용되는 할인 금액(discountAmount)과 비율 할인 정책에 사용되는 할인 비율(discountPercent)을 Movie안에서 직접 정의하고 있다.

할인 정책은 영화별로 오직 하나만 지정할 수 있기 때문에 한 시점에 discountAmount와 discountPercent중 하나의 값만 사용될 수 있다. 할인 정책의 종류를 결정하는 것이 movieType이다. movieType은 현재 영화에 설정된 할인 정책의 종류를 결정하는 열거형 타입이느 MovieType의 인스턴스이다.

movieType의 값이 AMOUNT_DISCOUT라면 discountAmount에 저장된 값을 사용하고 PERCENT_DISCOUNT라면 discountPercent에 저장된 값을 사용한다. NONE_DISCOUNT인 경우에는 할인 정책을 적용하지 말아야 하기 때문에 discountAmount와 discountPercent중 어떤 값도 사용하지 않는다.

```typescript
const enum MovieType {
    AMOUNT_DISCOUNT,
    PERCENT_DISCOUNT,
    NONE_DISCOUNT
}
```

이것은 말 그대로 데이터 중심의 접근 방법이다. Movie가 할인 금액을 계산하는데 필요한 데이터는 무엇인가? 금액 할인 정책의 경우에는 할인 금액이 필요하고 비율 할인 정책의 경우에는 할인 비율이 필요하다. 이 데이터들을 각각 discountAmount와 discountPercent라는 값으로 표현한다. 예매 가격을 계산하기 위해서는 Movie에 설정디ㅗㄴ 할인 정책이 무엇인지를 알아야 한다. 어떤 데이터가 필요한가? MovieType을 정의하고 이 타입의 인스턴스를 속성으로 포함시켜 이 값에 따라 어떤 데이터를 사용할지를 결정한다.

데이터 중심의 설계에서는 객체가 포함해야 하는 데이터에 집중한다. 이 객체가 포함해야 하는 데이터는 무엇인가? 객체의 책임을 결정하기 전에 이런 질문에 휩싸여 있다면 데이터 중심의 설계에 매몰돼 있을 확률이 높다. 특히 Movie 클래스의 경우처럼 객체의 종류를 저장하는 인스턴스 변수movieType와 인스턴스 종류에 따라 배타적으로 사용될 인스턴스 변수 discountAmount, discountPercent를 하나의 클래스 안에 함께 포함시키는 방식은 데이터 중심의 설계 안에서 흔히 볼 수 있는 패턴이다.

내부 데이터가 외부 다른 객체들을 오염시키는 것을 막기 위해 내부 데이터를 반환하는 접근자 accessor와 데이터를 변경하는 수정자 mutator를 추가한다.

```typescript
public class Movie {
    public getMovieType(): MovieType {
        return movieType;
    }
    public setMovieType(movieType: MovieType): void {
        this.movieType = movieType;
    }
    public getFee(): Money {
        return fee;
    }
    public setFee(fee: Money): void {
        this.fee = fee;
    }
    public getDiscountConditions(): Array<DiscountCondition> {
        return Object.freeze(this.discountConditions.slice());
    }
    public setDiscountConditions(discountConditions: Array<DiscountConditions>): void {
        this.discountConditions = discountConditions;
    }
    public getDiscountAmount(): Money {
        return this.discountAmount;
    }
    public setDiscountAmount(discountAmount:Money): void {
        this.discountAmount = discountAmount;
    }
    public getDiscountAmount(): number {
        return this.discountPercent;
    }
    public setDiscountAmount(discountPercent: number): void {
        this.discountPercent = discountPercent;
    }
}
```

Movie를 구현하는데 필요한 데이터를 결정했고 메서드를 이용해 내부 데이터를 캡슐화하는 데도 성공했다. 이제 할인 조건을 구현해 보자. 영화 예매 도메인에는 순번 조건과 기간 조건이라는 두가지 종류의 할인 조건이 존재한다. 순번 조건은 상영 순번을 이용해 할인 여부를 판단하고 기간 조건은 상영 시간을 이용해 할인 여부를 판단한다.

데이터 중심의 설계방법을 따르기 때문에 할인 조건을 설계하기 위해 해야 하는 질문은 다음과 같다. 할인 조건을 구현하는데 필요한 데이터는 무엇인가? 먼저 현재의 할인 조건의 종류를 저장할 데이터가 필요하다. 

```typescript
const enum DiscountConditionType {
    SEQUENCE,	// 순번 조건
    PERIOS		// 기간 조건
}
```

할인 조건을 구현하는 DiscountCondition은 할인 조건의 타입을 저장할 인스턴스 변수인 type을 포함한다. 또한 movieType의 경우와 마찬가지로 순번 조건에서만 사용되는 데이터인 상영 순번 sequence과 기간 조건에서만 사용되는 데이터인 요일 dayOfWeek, 시작 시간 startTime, 종료 시간 endTime을 함게 포함한다.

```typescript
public class DiscountCondition {
    private type: DiscountConditionType;
    private sequence: number;
    private dayOfWeek: DayOfWeek;
    private startTime: Date;
    private endTime: Date;
    
    public getType(): DiscountConditionType {
        return this.type;
    }
    public setType(type: DiscountCondition): void {
        this.type = type;
    }
    public getDayOfWeek(): DayOfWeek {
        return this.dayOfWeek;
    }
    public void setDayOfWeek(dayOfWeek: DayOfWeek): void {
        this.dayOfWeek = dayOfWeek;
    }
    public getStartTime(): Date {
        return this.startTime;
    }
    public setStartTime(startTime: Date): void {
        this.startTime = startTime;
    }
    public getEndTime(): Date {
        return endTime;
    }
    public setEndTime(endTime: LocalTime): void {
        this.endTime = endTime;
    }
    public getSequence(): number {
        return this.sequence;
    }
    public setSequence(sequence: number): void {
        this.sequence = sequence;
    }
}
```

```typescript
public class Screening {
    private movie: Movie;
    private sequence: number;
    private whenScreened: Date;
    
    public getMovie(): Movie {
        return this.movie;
    }
    public setMovie(movie: Movie) : void {
        this.movie = movie;
    }
    public getWhenScreened(): Date {
        return this.whenScreened;
    }
    public setWhenScreenined(whenScreened: Date): void {
        this.whenScreened = whenScreened;
    }
    public getSequence(): number {
        return this.sequence;
    }
    public setSequence(sequence: number): void {
        this.sequence = sequence;
    }
}
```

영화 예매 시스템의 목적은 영화를 예매하는 것이다. Reservation 클래스를 추가한다.

```typescript
public class Reservation {
    private customer: Customer;
    private screening: Screening;
    private fee: Money;
    private audienceCount: number;
    
    constructor(customer: Customer, screening: Screening, fee: Money, audienceCount: number) {
        this.customer = customer;
        this.screening = screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }
    public getCustomer(): Customer {
        return customer;
    }
    public setCustomer(customer: Customer): void {
        this.customer = customer;
    }
    public getScreening(): Screening {
        return this.screening;
    }
    public setScreening(screening: Screening): void {
        this.screening = screening;
    }
    public getFee(): Money {
        return this.fee;
    }
    public setFee(fee: Money): void {
        this.fee = fee;
    }
    public getAudienceCount(): number {
        return this.audienceCount;
    }
    public setAudienceCount(audienceCount: number): void {
        this.audienceCount = audienceCount;
    }
}
```

```typescript
// Customer는 고객의 정보를 보관하는 간단한 클래스다.
public class Customer {
    private name: string;
    private id: string;
    
    constructor(name: string, id: string) {	
    	this.id = id;
        this.name = name;
    }
}
```



![](./1.png)



​    

### 영화를 예매하자

```typescript
// ReservationAgency는 데이터 클래스들을 조합해서 영화 예매 절차를 구현하는 클래스이다.
public class ReservationAgency {
    
    // reserve
    // 2. discountable 변수의 값을 체크, 적절한 할인 정책에 따라 예매 요금 계산
    public reserve(screening: Screening, customer: Customer, audienceCount: number) {
        movie: Movie = screening.getMovie();
        
        discountable: boolean = false;
        // 1. DiscountCondition의 루프를 돌면서 할인 가능 여부를 확인
        movie.getDiscountConditions().find(condition => {
            // 할인 조건이 기간
            if(condition.getType() === DiscountConditionType.PERIOD) {
                discountasble = 
                    screening.getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) 
                && condition.getStartTime().compareTo(screening.getWhenScreened().toLocalTime()) <= 0 
                && condition.getEndTime().compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
            } else {
                // 할인 조건이 순번 조건
                discountable = condition.getSequence() === screening.getSequence();
            }
            return discountable;
        });
        
        fee: Money;
        
        // 하이ㅏㄴ 정책의 타입에 따라 할인 요금을 계산하는 로직을 분기
        if(discountable) {
            discountAMount: Money = Money.ZERO;
            switch(movie.getMovieType()) {
                case AMOUNT_DISCOUNT:
                    discountAmount = movie.getDiscountAmount();
                    break;
                case PERCENT_DISCOUNT:
                    discountAMount = movie.getFee().times(movie.getDiscountPercent());
                    break;
                case NONE_DISCOUNT:
                    discountAMount = Money.ZERO;
                    break;
            }
            fee = movie.getFee().minus(discountAmount).times(audienceCount);
        } else {
            fee = movie.getFee();
        }
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
```

지금까지 영화 예매 시스템을 데이터 중심으로 설계하는 방법을 살펴봤다. 이제 이 설계를 책임 중심의 설계방법과 비교해 보면서 두 방법의 장단점을 파악해 보자.

​    

## 02. 설계 트레이드 오프

### 캡슐화

캡슐화란 변경 가능성이 높은 부분을 객체 내부로 숨기는 추상화 기법이다. 

### 응집도와 결합도

응집도는 모듈에 포함된 내부 요소들이 연관돼 있는 정보를 나타낸다. 모듈 내의 요소들이 하나의 목적을 위해 긴밀하게 협력한다면 그 모듈은 높은 응집도를 가진다. 모듈 내의 요소들이 서로 다른 목적을 추구한다면 그 모듈은 낮은 응집도를 가진다. 객체지향의 관점에서 응집도는 객체 또는 클래스에 얼마나 관련 높은 책임들을 할당했는지를 나타낸다.

결합도는 의존성의 정도를 나타내며 다른 모듈에 대해 얼마나 많은 지식을 갖고 있는지를 나타내는 척도이다. 어떤 모듈이 다른 모듈에 대해 너무 자세한 부분까지 알고 있다면 두 모듈은 높은 결합도를 가진다. 어떤 모듈이 다른 모듈에 대해 꼭 필요한 지식만 알고 있다면 두 모듈은 낮은 결합도를 가진다. 객체지향의 관점에서 결합도는 객체 또는 클래스가 협력에 필요한 적절한 수준의 관계만을 유지하고 있는지를 나타낸다.

일반적으로 좋은 설계란 높은 응집도와 낮은 결합도를 가진 모듈로 구성된 설계를 의미한다. 이렇게 되면 설계를 변경하기 쉽게 만든다. 

하나의 변경ㅇㄹ 수용하기 위해 모듈 전체가 함께 변경된다면 응집도가 높은 것이고 모듈의 일부만 변경된다면 응집도가 낮은 것이다. 또한 하나의 변경에 대해 하나의 모듈만 변경된다면 응집도가 높지만 다수의 모듈이 함께 변경되어야 한다면 응집도가 낮은 것이다. 

결합도는 한 모듈이 변경되기 위해서 다른 모듈의 변경을 요구하는 정도로 측정이 가능하다. 하나의 모듈을 수정할 때 얼마나 많은 모듈을 함께 수정해야 하는지를 나타낸다. 인터페이스에 의존하도록 코드를 작성해야 낮은 결합도를 얻을 수 있다.

캡슐화를 지키면 모듈 안의 응집도는 높아지고 모듈 사이의 결합도는 낮아진다. 

​    

## 03. 데이터 중심의 영화 예매 시스템의 문제점

### 캡슐화 위반

```typescript
public class Movie {
    private fee: Money;
    
    public getFee(): Money {
        return this.fee;
    }
    public setFee(fee: Money): void {
        this.fee = fee;
    }
}
```

위 코드에서 접근자와 수정자 메서드는 객체 내부의 상태에 대한 어떤 정보도 캡슐화하지 못한다. getFee, setFee 메서드는 Movie 내부에 Money 타입의 fee라는 이름의 인스턴스 변수가 존재한다는 사실을 퍼블릭 인터페이스에서 드러낸다. 이렇게 Movie가 캡슐화의 원칙을 어기게 된 근본적인 원인은 객체가 수행할 책임이 아니라 내부에 저장할 데이터에 초점을 맞췄기 때문이다. 객체에게 중요한 것은 책임이다. 그리고 구현을 캡슐화할 수 있는 ㅈ거절한 책임은 협력이라는 문맥을 고려할 때만 얻을 수 있다.

설계할 때 협력에 관해 고민하지 않으면 캡슐화를 위반하는 과도한 접근자와 수정자를 ㅏ가지게 되는 경향이 있다. 객체가 사용될 문맥을 추측할 수 밖에 없는 경우 개발자는 어던 상황에서도 해당 객체가 사용될 수 있게 최대한 많은 접근자 메서드를 추가하게 되는 것이다. 결과적으로 대부분의 내부 구현이 퍼블릭 인터페이스에 그대로 노출될 수 밖에 없는 것이다. 그 결과 캡슐화의 원칙을 위반하는 변경에 취약한 설계를 얻게 된다.
    

### 높은 결합도

객체 내부의 구현이 객체의 인터페이스에 드러난다는 것은 클라이언트가 구현에 강하게 결합된다는 것을 의미한다. 그리고 더 나쁜 소식은 단지 객체의 내부 구현을 변경했음에도 이 인터페이스에 의존하는 모든 클라이언트들도 함께 변경해야 한다는 것이다.

데이터 중심 설계는 객체의 캡슈로하를 약화시키기 때문에 클라이언트가 객체의 구현에 강하게 결합된다.

결합도 측면에서 데이터 중심 설계가 가지는 또 다른 단점은 여러 데이터 객체들을 사용하는 제어 로직이 특정 객체 안에 집중되기 때문에 하나의 제어 객체가 다수의 데이터객체에 강하게 결합된다는 것이다. 이 결합도로 인해 데이터 객체를 변겨아더라도 제어 객체를 함께 변경할 수 밖에 없다.

![](./2.png)

영화 예매 시스템을 보면 대부분의 제어 로직을 가지고 있는 제어 객체인 ReservationAgency가 모든 데이터 객체에 의존한다는 것을 알 수 잇다. DiscountCondition의 데이터가 변경되면 DiscountCondition뿐만 아니라 ReservationAgency도 함께 수정해야 한다. Screening의 데이터가 변경되면 Screening뿐만 아니라 ReservatiopnAgency도 함게 수정해야 한다. ReservationAgency는 모든 의존성이 모이는 결합도의 집결지이다. 시스템 안의 어떤 변경도 ReservationAgency의 변경을 유발한다.

데이터 중심의 설계는 전체 시스템을 하나의 거대한 의존성 덩어리로 만들어 버리기 때문에 어떤 변경이라도 일단 발생하고 나면 시스템 전체가 요동칠 수 밖에 없다.

​    
