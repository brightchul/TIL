const enum DayOfWeek {
    SUNDAY = 0,
    MONDAY = 1,
    TUESDAY = 2,
    WEDNESDAY = 3,
    THURSDAY = 4,
    FRIDAY = 5,
    SATUREDAY = 6,
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

const enum MovieType {
    AMOUNT_DISCOUNT,
    PERCENT_DISCOUNT,
    NONE_DISCOUNT,
}

class Movie {
    private title: string;
    private runningTime: number;
    private fee: Money;
    private discountConditions: Array<DiscountCondition>;

    private movieType: MovieType;
    private discountAmount: Money;
    private discountPercent: number;

    public getMovieType(): MovieType {
        return this.movieType;
    }
    public setMovieType(movieType: MovieType): void {
        this.movieType = movieType;
    }
    public getFee(): Money {
        return this.fee;
    }
    public setFee(fee: Money): void {
        this.fee = fee;
    }
    public getDiscountConditions(): Array<DiscountCondition> {
        return Object.freeze(
            this.discountConditions.slice()
        ) as Array<DiscountCondition>;
    }
    public setDiscountConditions(
        discountConditions: Array<DiscountCondition>
    ): void {
        this.discountConditions = discountConditions;
    }
    public getDiscountAmount(): Money {
        return this.discountAmount;
    }
    public setDiscountAmount(discountAmount: Money): void {
        this.discountAmount = discountAmount;
    }
    public getDiscountPercent(): number {
        return this.discountPercent;
    }
    public setDiscountPercent(discountPercent: number): void {
        this.discountAmount = discountPercent;
    }
}

const enum DiscountConditionsType {
    SEQUENCE,
    PERIOD,
}

class DiscountCondition {
    private type: DiscountConditionsType;
    private sequence: number;
    private dayOfWeek: DayOfWeek;
    private startTime: Date;
    private endTime: Date;
}

class Screening {
    private movie: Movie;
    private sequence: number;
    private whenScreened: Date;

    public getMovie(): Movie {
        return this.movie;
    }
    public setMovie(movie: Movie): void {
        this.movie = movie;
    }
    public getSequence(): number {
        return this.sequence;
    }
    public setSequence(sequence: number): void {
        this.sequence = sequence;
    }
    public getWhenScreened(): Date {
        return this.whenScreened;
    }
    public setWhenScreened(whenScreened: Date): void {
        this.whenScreened = whenScreened;
    }
}

class Reservation {
    private customer: Customer;
    private screening: Screening;
    private fee: Money;
    private audienceCount: number;

    constructor(
        customer: Customer,
        screening: Screening,
        fee: Money,
        audienceCount: number
    ) {
        this.customer = customer;
        this.screening = screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }
    public getCustomer(): Customer {
        return this.customer;
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

class Customer {
    private name: string;
    private id: string;

    constructor(name: string, id: string) {
        this.id = id;
        this.name = name;
    }
}

class ReservationAgency {}
