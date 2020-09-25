// 상영 : 상영할 영화, 순번, 상영 시작시간
class Screening {
    private movie: Movie;
    private sequence: number;
    private whenScreened: Date;

    constructor(movie: Movie, sequence: number, whenScreened: Date) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    getStartTime() {
        return this.whenScreened;
    }

    isSequence(sequence: number): boolean {
        return this.sequence === sequence;
    }

    getMovieFee(): Money {
        return this.movie.getFee();
    }
    calculateFee(audienceCount: number) {
        return this.movie
            .calculateMovieFee(this)
            .times(audienceCount);
    }
    reserve(
        customer: CustomElementRegistry,
        audienceCount: number
    ): Reservation {
        return new Reservation(
            customer,
            this,
            this.calculateFee(audienceCount),
            audienceCount
        );
    }
}

class Money {
    static ZERO: Money = Money.wons(0);
    private amount: number;

    static wons(amount: number): Money {
        return new Money(amount);
    }

    constructor(amount: number) {
        this.amount = amount;
    }

    plus(amount: Money): Money {
        return new Money(this.amount + amount.amount);
    }

    minus(amount: Money): Money {
        return new Money(this.amount - amount.amount);
    }

    times(percent: number): Money {
        return new Money(this.amount * percent);
    }

    isLessThan(other: Money): boolean {
        return this.amount < other.amount;
    }

    isGreaterThanOrEqual(other: Money): boolean {
        return this.amount >= other.amount;
    }
}

class Reservation {
    private customer: CustomElementRegistry;
    private screening: Screening;
    private fee: Money;
    private audienceCount: number;

    constructor(
        customer: CustomElementRegistry,
        screening: Screening,
        fee: Money,
        audienceCount: number
    ) {
        this.customer = customer;
        this.screening = screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }
}

class Movie {
    private title: string;
    private runningTime: number;
    private fee: Money;
    private discountPolicy: DiscountPolicy;

    constructor(
        title: string,
        runningTime: number,
        fee: Money,
        discountPolicy: DiscountPolicy
    ) {
        this.title = title;
        this.runningTime = runningTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    getFee(): Money {
        return this.fee;
    }

    calculateMovieFee(screening: Screening): Money {
        if (!this.discountPolicy) return this.fee;

        return this.fee.minus(
            this.discountPolicy.calculateDiscountAmount(screening)
        );
    }

    changeDiscountPolicy(discountPolicy: DiscountPolicy): void {
        this.discountPolicy = discountPolicy;
    }
}

interface DiscountPolicy {
    calculateDiscountAmount(screening: Screening): Money;
}

class NoneDiscountPolicy implements DiscountPolicy {
    public calculateDiscountAmount(screening: Screening): Money {
        return Money.ZERO;
    }
}

abstract class DefaultDiscountPolicy implements DiscountPolicy {
    private conditions: Array<DiscountCondition> = [];

    constructor(...conditions: Array<DiscountCondition>) {
        this.conditions = conditions;
    }

    private hasSatisfiedCondition(screening: Screening) {
        return this.conditions.some((each: DiscountCondition) =>
            each.isSatisfiedBy(screening)
        );
    }

    calculateDiscountAmount(screening: Screening) {
        if (this.hasSatisfiedCondition(screening))
            return this.getDiscountAmount(screening);

        return Money.ZERO;
    }

    protected abstract getDiscountAmount(screening: Screening): Money;
}

interface DiscountCondition {
    isSatisfiedBy(screening: Screening): boolean;
}

class SequenceCondition implements DiscountCondition {
    private sequence: number;

    constructor(sequence: number) {
        this.sequence = sequence;
    }

    isSatisfiedBy(screening: Screening): boolean {
        return screening.isSequence(this.sequence);
    }
}

class PeriodCondition implements DiscountCondition {
    private dayOfWeek: DayOfWeek;
    private startTime: Date;
    private endTime: Date;

    constructor(
        dayOfWeek: DayOfWeek,
        startTime: Date,
        endTime: Date
    ) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    isSatisfiedBy(screening: Screening): boolean {
        return (
            screening.getStartTime().getDay() === this.dayOfWeek &&
            this.startTime.toLocaleTimeString() <=
                screening.getStartTime().toLocaleTimeString() &&
            this.endTime.toLocaleTimeString() >=
                screening.getStartTime().toLocaleTimeString()
        );
    }
}

class AmountDiscountPolicy extends DefaultDiscountPolicy {
    private discountAmount: Money;

    constructor(
        discountAmount: Money,
        ...conditions: Array<DiscountCondition>
    ) {
        super(...conditions);
        this.discountAmount = discountAmount;
    }

    protected getDiscountAmount(screening: Screening): Money {
        return this.discountAmount;
    }
}

class PercentDiscountPolicy extends DefaultDiscountPolicy {
    private percent: number;

    constructor(
        percent: number,
        ...conditions: Array<DiscountCondition>
    ) {
        super(...conditions);
        this.percent = percent;
    }

    protected getDiscountAmount(screening: Screening): Money {
        return screening.getMovieFee().times(this.percent);
    }
}

const enum DayOfWeek {
    SUNDAY = 0,
    MONDAY = 1,
    TUESDAY = 2,
    WEDNESDAY = 3,
    THURSDAY = 4,
    FRIDAY = 5,
    SATUREDAY = 6,
}

export default function () {
    const avartar: Movie = new Movie(
        "아바타",
        120,
        Money.wons(10000),
        new AmountDiscountPolicy(
            Money.wons(800),
            new SequenceCondition(1),
            new SequenceCondition(10),
            new PeriodCondition(
                1,
                new Date("2020-09-21 10:00"),
                new Date("2020-09-21 11:59")
            ),
            new PeriodCondition(
                4,
                new Date("2020-09-24 10:00"),
                new Date("2020-09-24 20:59")
            )
        )
    );

    avartar.changeDiscountPolicy(
        new PercentDiscountPolicy(
            0.1,
            new PeriodCondition(
                2,
                new Date("2020-09-22 14:00"),
                new Date("2020-09-22 16:59")
            ),
            new SequenceCondition(2),
            new PeriodCondition(
                4,
                new Date("2020-09-22 10:00"),
                new Date("2020-09-22 13:59")
            )
        )
    );

    const titanic: Movie = new Movie(
        "타이타닉",
        180,
        Money.wons(11000),
        new PercentDiscountPolicy(
            0.1,
            new PeriodCondition(
                2,
                new Date("2020-09-22 14:00"),
                new Date("2020-09-22 16:59")
            ),
            new SequenceCondition(2),
            new PeriodCondition(
                4,
                new Date("2020-09-22 10:00"),
                new Date("2020-09-22 13:59")
            )
        )
    );

    const starWars = new Movie(
        "스타워즈",
        210,
        Money.wons(10000),
        new NoneDiscountPolicy()
    );
    console.log(avartar);
    console.log(JSON.stringify(avartar));
    console.log(titanic);
    console.log(JSON.stringify(titanic));
    console.log(starWars);
    console.log(JSON.stringify(starWars));
}
