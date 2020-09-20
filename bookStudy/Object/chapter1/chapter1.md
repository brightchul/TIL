# 1장 객체, 설계

## 1) 티켓 판매 애플리케이션 구현하기



### 요구사항

>  소극장 이벤트를 연다. 이벤트에 선정되어 초대장을 받은 관람객은 무료로 입장할 수 있다. 이벤트에 당첨된 관람객과 그렇지 못한 관람객은 다른 방식으로 입장시켜야 한다. 이벤트에 당첨된 관람객은 초대장을 티켓으로 교환한 후에 입장할 수 있다. 이벤트에 당첨되지 않은 관람객은 티켓을 구매해야만 입장할 수 있다. 관람객을 입장시키기 전에 이벤트 당첨 여부를 확인해야 하고 이벤트 당첨자가 아닌 경우에는 티켓을 판매한 후에 입장시켜야 한다.



### 구현

```typescript
// 초대장
class Invitation {
    private when?: Date;

    constructor(when: Date) {
        this.when = when;
    }
}
// 티켓
class Ticket {
    private fee: number;

    constructor(fee: number) {
        this.fee = fee;
    }
    getFee(): number {
        return this.fee;
    }
}

// 관람객 가방에 초대장, 현금, 티켓
class Bag {
    private amount: number;
    private invitation?: Invitation;
    private ticket?: Ticket;

    // 현금만 있거나 현금 + 초대장이 있거나
    // 위의 2가지 제약을 강제하는 의도로 생성자 구현
    // typescript 생성자 오버로딩시 에러가 발생해서 아래와 같이 구현
    constructor(amount: number, invitaion?: Invitation) {
        this.invitation = invitaion;
        this.amount = amount;
    }

    // 초대장 여부 확인
    hasInvitation(): boolean {
        return this.invitation !== undefined;
    }

    // 티켓 여부 확인
    hasTicket(): boolean {
        return this.ticket !== undefined;
    }

    setTicket(ticket: Ticket): void {
        this.ticket = ticket;
    }

    // 현금 증가 및 감소
    plusAmount(amount: number): void {
        this.amount += amount;
    }
    minusAmount(amount: number) {
        this.amount -= amount;
    }
}

// 관람객
class Audience {
    private bag: Bag;

    constructor(bag: Bag) {
        this.bag = bag;
    }

    getBag(): Bag {
        return this.bag;
    }
}

// 관람객은 매표소에서 초대장을 티켓으로 교환 또는 티켓을 구매
// 매표소에는 티켓과 티켓 판매 금액이 보관되어 있어야 한다.
class TicketOffice {
    private amount: number;
    private tickets: Array<Ticket>;

    constructor(amount: number, ...tickets: Array<Ticket>) {
        this.amount = amount;
        this.tickets = Array.from(tickets) as Array<Ticket>;
    }

    hasTicket(): boolean {
        return this.tickets.length > 0;
    }

    getTicket(): Ticket | undefined {
        return this.tickets.shift();
    }

    minusAmount(amount: number): void {
        this.amount -= amount;
    }

    plusAmount(amount: number): void {
        this.amount += amount;
    }
}

// 판매원은 매표소에서 초대장을 티켓으로 교환, 티켓을 판매하는 역할을 수행
// 판매원은 자신이 일하는 매표소를 알고 있어야 한다.
class TicketSeller {
    private ticketOffice: TicketOffice;

    constructor(ticketOffice: TicketOffice) {
        this.ticketOffice = ticketOffice;
    }

    getTicketOffice() {
        return this.ticketOffice;
    }
}

// 소극장을 구현, 극장은 관람객을 맞이할 수 있어야 한다.
class Theater {
    private ticketSeller: TicketSeller;

    constructor(ticketSeller: TicketSeller) {
        this.ticketSeller = ticketSeller;
    }

    enter(audience: Audience): void {
        if (!this.ticketSeller.getTicketOffice().hasTicket()) return;
        const ticket: Ticket = this.ticketSeller.getTicketOffice().getTicket()!;
        
        // 소극장은 관람객의 가방안에 초대장이 들어 있는지 확인한다.
        if (audience.getBag().hasInvitation()) {
            // 이벤트에 당첨된 관람객에겐 판매원에게서 받은 티켓을 관람객의 가방안에 넣어준다.
            audience.getBag().setTicket(ticket);
            
        } else {
            // 가방 안에 초대장이 없다면 티켓을 판매해야 한다.
            audience.getBag().minusAmount(ticket.getFee());
            // 관람객의 가방에서 티켓 금액만큼을 차감한 후 매표소에 금액을 증가시킨다.
            this.ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
            // 소극장은 관람객의 가방안에 티켓을 넣어줘서 관람객의 입장 절차를 끝낸다.
            audience.getBag().setTicket(ticket);
        }
    }
}
```

​    

## 2) 무엇이 문제인가

> 소프트웨어 모듈(클래스, 패키지, 라이브러리와 같이 프로그램을 구성하는 임의의 요소)이 가져야 하는 세가지 기능 
> 1) 실행 중에 제대로 동작할 것 
> 2) 변경 용이성 : 간단한 작업으로도 변경이 가능해야 한다. 
> 3) 읽는 사람과 의사소통 : 특별한 훈련 없이도 개발자가 쉽게 읽고 이해할 수 있어야 한다.

위의 코드는 변경 용이성과 의사소통이 제대로 만족시키지 못한다.

​    

### 예상을 빗나가는 코드

문제점 : 관람객과 판매원이 소극장의 통제를 받는 수동적인 존재하는 점이다.

- 관람객 : 소극장이 관람객의 가방을 마음대로 열어본다.
- 판매원 : 소극장이 마음대로 매표소에 보관 중인 티켓과 현금에 마음대로 접근할 수 있다. 티켓을 관람객의 가방에 넣고 관람객에게서 받은 돈을 매표소에 적립하는 일을 소극장이 대신 수행하고 있다.



#### 이해 가능한 코드

이해 가능한 코드란 동작이 우리의 예상에서 크게 벗어나지 않는 코드이다. 하지만 앞의 코드는 우리의 상식과는 다르게 동작해서 코드를 읽는 사람과 제대로 의사소통하지 못한다.



#### 이해하기 어려운 코드

이 코드를 이해하기 위해서는 여러 가지 **세부적인 내용들을 한꺼번에 기억하고 있어야 한다**는 점이다. 

Theater의 enter 메서드를 이해하기 위해서는 Audience가 Bag을 가지고 있고 Bag 안에는 현금과 티켓이 들어 있고 TicketSeller가 TicketOffice에서 티켓을 판매하고, TicketOffice 안에 돈과 티켓이 보관되어 있다는 모든 사실을 동시에 기억하고 있어야 한다. 

이 코드는 하나의 클래스나 메서드에서 너무 많은 세부사항을 다루기 때문에 코드를 작성하는 사람 뿐만 아니라 코드를 읽고 이해해야 하는 사람 모두에게 큰 부담을 준다.

또한 Audience와 TicketSeller를 변경할 경우 Theater도 함께 변경해야 한다는 것이 가장 큰 문제이다.

​    

### 변경에 취약한 코드

**가장 큰 문제는 변경에 취약하다는 점이다.**

관람객이 가방을 들고 있다는 가정이 바뀐다면 Audience 클래스는 Bag를 제거해야하며, Audience의 Bag에 직접 접근하는 Theater의 enter 메서드 역시 수정되어야 한다. Theater은 관람객이 가방을 들고 있고 판매원이 매표소에서만 티켓을 판매한다는 지나치게 세부적인 사실에 의존해서 동작한다. 이 세부사항 중 사실 중 하나라도 바뀌면 해당 클래스, 이 클래스에 의존하는 Theater도 함께 변경해야 한다. 이처럼 다른 클래스가 특정 클래스의 내부에 대해 더 많이 알면 알수록 특정 클래스를 변경하기 어려워진다.

이것은 객체 사이의 의존성과 관련된 문제이다. 문제는 의존성이 변경과 관련돼있다는 점이다. 의존성은 변경에 대한 영향을 암시한다. 의존성이라는 말 속에는 어떤 객체가 변경될 때 그 객체에게 의존하는 다른 객체도 함께 변경될 수 있다는 사실이 내포되어 있다.

객체지향 설계는 서로 의존한면서 협력하는 객체들의 공동체를 구축하는 것이다. 따라서 우리의 목표는 애플리케이션의 기능을 구현하는데 필요한 최소한의 의존성만 유지하고 불필요한 의존성을 제거하는 것이다.

**객체 사이의 의존성이 과한 경우를 결합도(coupling)가 높다**고 말한다. 반대로 객체들이 합리적인 수준으로 의존할 경우에는 결합도가 낮다고 말한다. 결합도는 의존성과 관련되어있기 때문에 결합도 역시 변경과 관련이 있다. 두 객체간의 결합도가 높으면 높을수록 함께 변경될 확률도 높아지기 때문에 변경하기 어려워진다. 따라서 설계의 목표는 객체 사이의 결합도를 낮춰 변경이 용이한 설계를 만드는 것이어야 한다.

​    

## 3) 설계 개선하기

문제를 해결하는 방법은 Theater가 Audience와 TicketSeller에 관해 너무 세세한 부분까지 알지 못하도록 차단하면 된다. 관람객이 스스로 가방안의 현금과 초대장을 처리하고 판매원이 스스로 매표소의 티켓과 판매 요금을 다루게 한다면 해결할 수 있다.
    

### 자율성을 높이자

Theater의 enter 메서드에서 TicketOffice에 접근하는 모든 코드를 TicketSeller 내부의 sellTo 메서드 안으로 숨긴다.

```typescript
class Theater {
    private ticketSeller: TicketSeller;

    constructor(ticketSeller: TicketSeller) {
        this.ticketSeller = ticketSeller;
    }

    enter(audience: Audience): void {
        /* 판매원에게 넘긴다 */
        // if (!this.ticketSeller.getTicketOffice().hasTicket()) return;

        // const ticket: Ticket = this.ticketSeller.getTicketOffice().getTicket()!;
        // if (audience.getBag().hasInvitation()) {
        //     audience.getBag().setTicket(ticket);
        // } else {
        //     audience.getBag().minusAmount(ticket.getFee());
        //     this.ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
        //     audience.getBag().setTicket(ticket);
        // }
        
        this.ticketSeller.sellTo(audience);
    }
}

/* 외부에서 ticketOffice에 직접 접근할 수 없다. ticketSeller은 티켓, 요금 적립을 스스로 수행한다. 
   ticketOffice를 캡슐화했다. */
class TicketSeller {
    private ticketOffice: TicketOffice;

    constructor(ticketOffice: TicketOffice) {
        this.ticketOffice = ticketOffice;
    }

    /* 소극장이 직접할 필요가 없으니 필요가 없어졌다. */
    // getTicketOffice() {
    //     return this.ticketOffice;
    // }

    sellTo(audience: Audience): void {
        if (!this.ticketOffice.hasTicket()) return;

        const ticket: Ticket = this.ticketOffice.getTicket()!;
        if (audience.getBag().hasInvitation()) {
            audience.getBag().setTicket(ticket);
        } else {
            audience.getBag().minusAmount(ticket.getFee());
            this.ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
```

ticketOffice에 대한 접근은 오직 TicketSeller 안에만 존재하게 된다. 이처럼 개념적이나 물리적으로 객체 내부의 세부적인 사항을 감추는 것을 **캡슐화(encapsulation)** 이라고 부른다. **캡슐화의 목적인 변경하기 쉬운 객체를 만드는 것**이다. 캡슐화를 통해 객체 내부로의 접근을 제한하면 객체와 객체 사이의 **결합도를 낮출 수 있기 때문**에 설계를 좀 더 쉽게 변경할 수 있게 된다.

​    

```typescript
class TicketSeller {
    private ticketOffice: TicketOffice;

    constructor(ticketOffice: TicketOffice) {
        this.ticketOffice = ticketOffice;
    }

    getTicketOffice() {
        return this.ticketOffice;
    }
    sellTo(audience: Audience): void {
        if (!this.ticketOffice.hasTicket()) return;

        // const ticket: Ticket = this.ticketOffice.getTicket()!;
        // if (audience.getBag().hasInvitation()) {
        //     audience.getBag().setTicket(ticket);
        // } else {
        //     audience.getBag().minusAmount(ticket.getFee());
        //     this.ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
        //     audience.getBag().setTicket(ticket);
        // }
        
        /* TicketSeller는 Audience의 인터페이스 buy에만 의존하도록 변경했다. */
        this.ticketOffice.plusAmount(audience.buy(this.ticketOffice.getTicket()));
    }
}

class Audience {
    private bag: Bag;

    constructor(bag: Bag) {
        this.bag = bag;
    }

    /* 외부에서 Bag을 알 필요가 없으므로 내부에 Bag을 캡슐화할 수 있다. */
    // getBag(): Bag {
    //     return this.bag;
    // }
    
    buy(ticket: Ticket): number {
        
        /* Audience는 자신의 가방 안에 초대장이 들어있는지 스스로 확인한다. */
        if (this.bag.hasInvitation()) {
            this.bag.setTicket(ticket);
            return 0;
        } else {
            this.bag.setTicket(ticket);
            this.bag.minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }
}
```

Theater는 오직 TicketSeller의 **인터페이스**에만 의존한다. TicketSeller가 내부에 TicketOffice 인스턴스를 포함하고 있다는 사실은 구현 영역에 속한다. 객체를 인터페이스 구현으로 나누고 인터페이스만을 공개하는 것은 객체 사이의 결합도를 낮추고 번경하기 쉬운 코드를 작성하기 위해 따라야 하는 가장 기본적인 설계 원칙이다.

캡슐화를 개선한 후에 가장 크게 달라진 점은 Audience와 TicketSeller가 내부 구현을 외부에 노출하지 않고 자신의 문제를 스스로 책임지고 해결한다는 것이다.

​    

### 무엇이 개선되었는가?

수정된 Audience와 TicketSeller는 자신이 가지고 있는 소지품을 스스로 관리한다. 코드를 읽는 사람과의 의사소통이라는 관점에서 개선되었다. 또한 더 중요한 점은 Audience 나 TicketSeller의 내부 구현은 변경하더라도 Theater를 함께 변경할 필요가 없어졌다는 점이다. 가방을 변경, 금액 보관 방식을 변경하는 것에 대해서 Audience, TicketSeller 내부에서 변경이 제한될 수 있다. 따라서 수정된 코드는 변경 용이성 측면에서 개선되었다.

​    

### 어떻게 한 것인가?

자기 자신의 문제를 스스로 해결하도록 코드를 변경한 것이다. 즉 객체의 자율성을 높이는 방향으로 설계를 개선했고, 이해하기 쉽고 유연한 설계를 얻을 수 있었다.

​    

### 캡슐화와 응집도

핵심은 객체 내부의 상태를 캡슐화하고 객체간에 오직 메세지를 통해서만 상호작용하도록 만드는 것이다. **밀접하게 연관된 작업만을 수행하고 연관성 없는 작업은 다른 객체에게 위임하는 객체를 가리켜 응집도(cohesion)가 높다**고 말한다. 자신의 데이터를 스스로 처리하는 자율적인 객체를 만들면 결합도를 낮출 수 있을뿐더러 응집도를 높일 수 있다.

**객체의 응집도를 높이기 위해서는 객체 스스로 자신의 데이터를 책임**져야 한다. 외부의 간섭을 최대한 배제하고 메세지를 통해서만 협력하는 자율적인 객체들의 공동체를 만드는 것이 훌륭한 객체지향 설계를 얻을 수 있는 지름길인 것이다.

​    

### 절차지향과 객체지향

수정하기 전의 코드에서는 Audience, TicketSeller, Bag, TicketOffice는 관람객을 입장시키는데 필요한 정보를 제공하고 모든 처리는 Theater의 enter 메서드안에서 했다.

이 관점에서 Theater의 enter 메서드는 프로세스 이며, Audience, TicketSeller, Bag, TicketOffice는 데이터이다. 이처럼 프로세스와 데이터를 별도의 모듈에 위치시키는 방식을 절차적 프로그래밍(Procedural Programming) 이라고 부른다. 프로세스를 담당하는 Theater가 TicketSeller, TicketOffice, Audience, Bag 모두에 의존하고 있다. 모든 처리가 하나의 클래스 안에 위치하고 나머지 클래스를 단지 데이터의 역할만 수행하기 때문이다.

일반적으로 절차적 프로그래밍은 우리의 직관에 위배된다. 따라서 코드를 읽는 사람과 원활하게 의사소통하지 못한다. 더 큰 문제는 절차적 프로그래밍 세상에서는 데이터의 변경으로 인한 영향을 지역적으로 고립시키기 어렵다는 것이다. 절차적 프로그래밍은 변경하기 어려운 코드를 양산하는 경향이 있다. 절차적 프로그래밍은 프로세스가 필요한 모든 데이터에 의존해야 한다는 근본적인 문제점 때문에 변경에 취약할 수 밖에 없다.

변경하기 쉬운 설계는 한번에 하나의 클래스만 변경할 수 있는 설계다. 

해결 방법은 자신의 데이터를 스스로 처리하도록 프로세스의 적절한 단계를 Audience와 TicketSeller로 이동시키는 것이다.수정한 후의 코드에서는 데이터를 사용하는 프로세스가 데이터를 소유하고 있는 Audience와 TicketSeller내부로 옮겨졌다. 이처럼 데**이터와 프로세스가 동일한 모듈 내부에 위치하도록 프로그래밍하는 방식을 객체지향 프로그래밍**이라고 부른다.

객체지향 설계의 핵심은 캡슐화를 이용해 의존성을 적절히 관리해서 객체 사이의 결합도를 낮추는 것이다. 일반적으로 객체지향이 절차지향에 비해 변경에 좀 더 유연하다고 말하는 이유가 이 때문이다. 객체 지향 코드는 자신의 문제를 스스로 처리하기에 이해하기 쉽고, 객체 내부의 변경이 객체 외부에 파급되지 않도록 제어할 수 있기 때문에 변경하기가 수월하다.

​    

### 책임의 이동

두 방식 사이에 근본적인 차이를 만드는 것은 **책임의 이동(shift of responsibility)**이다. 여기서 '책임'은 객체지향 패러다임에서 기능을 가리키는 용어로 생각해도 된다. 기존의 절차적 프로그래밍 방식에서 책임이 Theater에 집중되어 있었다.

그에 반해 객체지향 설계에서는 제어 흐름이 각 객체에 적절하게 분산되어 있음을 알 수 있다. 즉, 하나의 기능을 완성하는데 필요한 책임이 여러 객체에 걸쳐 분산되어 있는 것이다.

변경전에 Theater가 전체적인 작업을 처리했는데, 변경후에는 각 객체가 자신이 맡은 일을 스스로 처리하면서 '책임의 이동'이 일어난 것이다. 객체지향 설계에서는 각 객체가 자신을 스스로 책임진다. 객체지향 설계의 핵심은 적절한 객체에 적절한 책임을 할당하는 것이다. 객체는 다른 객체와의 협력이라는 문맥 안에서 특정한 역할을 수행하는데 필요한 적절한 책임을 수행해야 한다. 따라서 객체가 어떤 데이터를 가지느냐보다는 객체에 어떤 책임을 할당할 것이냐에 초점을 맞춰야 한다.

적절한 객체에 적절한 책임을 할당하면 이해하기 쉬운 구조와 읽기 쉬운 코드를 얻게 된다.

설계를 어렵게 만드는 것은 의존성이라는 것을 기억해라. 해결방법은 **불필요한 의존성을 제거해서 객체 사이의 결합도를 낮추는 것**이다. 예제에서는 결합도를 낮추기 위해 세부사항을 각 객체에 캡슐화 했다. 이렇게 세부사항을 객체 내부로 캡슐화하는 것은 객체의 자율성을 높이고 응집도 높은 객체들의 공동체를 만들 수 있다. 불필요한 세부사항을 캡슐화하는 자율적인 객체들이 낮은 결합도와 높은 응집도를 가지고 협력하도록 최소한의 의존성만을 남기는 것이 훌륭한 객체지향 설계이다.

​    

### 더 개선할 수 있다

Bag을 자율적인 존재로 바꿔보자. Bag의 내부 상태에 접근하는 모든 로직을 Bag 안으로 캡슐화해서 결합도를 낮추면 된다.

```typescript
class Bag {
    private amount: number = 0;
    private invitation?: Invitation;
    private ticket?: Ticket;

    constructor(amount: number, invitaion?: Invitation) {
        this.invitation = invitaion;
        this.amount = amount;
    }
    
	// 새로 추가, 아래의 새부 구현이 아닌 hold만 알도록 의도함.
    hold(ticket: Ticket): number {
        if (this.hasInvitation()) {
            this.setTicket(ticket);
            return 0;
        } else {
            this.setTicket(ticket);
            this.minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }

    /* 아래의 모든 메소드들이 전부 private으로 변경했다.
       코드의 중복을 줄이고 표현력을 높이기 위해서 그대로 유지했다. */
    private hasInvitation(): boolean {
        return this.invitation !== undefined;
    }

    private setTicket(ticket: Ticket): void {
        this.ticket = ticket;
    }
    private minusAmount(amount: number) {
        this.amount -= amount;
    }
}

class Audience {
    private bag: Bag;

    constructor(bag: Bag) {
        this.bag = bag;
    }

    /* Bag의 hold만 알면된다. */
    buy(ticket: Ticket): number {
        // if (this.bag.hasInvitation()) {
        //     this.bag.setTicket(ticket);
        //     return 0;
        // } else {
        //     this.bag.setTicket(ticket);
        //     this.bag.minusAmount(ticket.getFee());
        //     return ticket.getFee();
        // }
        
        return this.bag.hold(ticket);
    }
}
```

```typescript
class TicketSeller {
    private ticketOffice: TicketOffice;

    constructor(ticketOffice: TicketOffice) {
        this.ticketOffice = ticketOffice;
    }

    // getTicketOffice(): TicketOffice {
    //     return this.ticketOffice;
    // }
    
    sellTo(audience: Audience): void {
        /* TicketOffice안으로 아래로직을 옮겨서 캡슐화를 해주자 */
        // this.ticketOffice.plusAmount(audience.buy(this.ticketOffice.getTicket()));
        
        /* ticketOffice의 sellTicketTo 인터페이스만 의존하게 되었다. */
        this.ticketOffice.sellTicketTo(audience);
    }
}

class TicketOffice {
    private amount: number;
    private tickets: Array<Ticket>;

    constructor(amount: number, ...tickets: Array<Ticket>) {
        this.amount = amount;
        this.tickets = Array.from(tickets);
    }

    /* 아래 메소드가 추가되면서 getTicket, plusAmount는 private으로 변경하게 되었다. */
    /* 다만 Audience에 대한 의존성이 추가되었다. */
    /* Audience에 대한 결합도 VS TicketOffice의 자율성 ==> 트레이드 오프 */
    sellTicketTo(audience: Audience): void {
        this.plusAmount(audience.buy(this.getTicket()));
    }

    private getTicket(): Ticket {
        const oneTicket = this.tickets.splice(0, 1);
        return oneTicket[0];
    }

    private plusAmount(amount: number): void {
        this.amount += amount;
    }
}
```

-   어떤 기능을설계하는 방법은 한가지 이상일 수 있다.
-   동일한 기능을 한 가지 이상의 방법으로 설계할 수 있기 때문에 결국 설계는 트레이드오프의 산물이다. 어떤 경우에도 모든 사람들을 만족시킬 수 있는 설계를 만들 수는 없다.
-   훌륭한 설계는 적절한 트레이드오프의 결과물이다.

[[실행]](https://codesandbox.io/s/heuristic-khorana-c9h0o?file=/src/example.ts)

​    

### 그래, 거짓말이다!

현실에서는 수동적인 존재, 무생물이라고 하더라도 객체지향 세계에서는 모든 것이 능동적이고 자율적인 존재로 바뀐다. 이처럼 능동적이고 자율적인 존재로 소프트웨어 객체를 설계하는 원칙을 가리켜 의인화(anthropomrphism)이라고 부른다.

훌륭한 객체지향 설계란 소프트웨어를 구성하는 모든 객체들이 자율적으로 행동하는 설계를 가리킨다. 이해하기 쉽고 변경하기 쉬운 코드를 작성하고 싶다면 한편의 애니메이션을 만든다고 생각하는게 좋다.

​    

## 4) 객체지향 설계

### 설계가 왜 필요한가?

설계를 구현과 떨어트려서 이야기하는 것은 불가능하다. 설계는 코드를 작성하는 매 순간 코드를 어떻게 배치할 것인지를 결정하는 과정에서 나온다. 설계는 코드 작성의 일부이며 코드를 작성하지 않고서는 검증할 수 없다.

좋은 설계란 오늘 요구하는 기능을 온전히 수행하면서 내일의 변경을 매끄럽게 수용할 수 있는 설계이다.

변경을 수용할 수 있는 설계가 중요한 이유는 요구사항이 항상 변경되기 떄문이다. 또한 코드를 변경할 때 버그가 추가될 가능성이 높기 때문이다. 버그의 가장 큰 문제점은 불확실성으로 인한 두려움을 만들어내서 코드를 수정하려는 의지를 꺾는다는 것이다.

​    

### 객체지향 설계

우리가 진정으로 원하는 것은 변경에 유연하게 대응할 수 있는 코드이다. 변경가능한 코드란 이해하기 쉬운 코드이다.

객체지향의 세계에서 애플리케이션은 객체들로 구성되며 애플리케이션의 기능은 객체들 간의 상호작용을 통해 구현된다. 그리고 객체들 사이의 상호작용은 객체 사이에 주고 받는 메세지로 표현된다. 메세지를 전송하기 위해 서로의 객체를 알고있어야 하는 이런 지식이 두 객체를 결합시키고 이 결합이 객체 사이의 의존성을 만든다.

훌륭한 객체지향 설계란 협력하는 객체 사이의 의존성을 적절하게 관리하는 설계이다. 의존성이 강할 수록 애플리케이션을 수정하기 어렵게 만든다. 그리고 협력하는 객체들 사이의 의존성을 적저라게 조절해서 변경에 용이한 설계를 만들어 나가야 한다.
