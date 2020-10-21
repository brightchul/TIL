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

    constructor(
        title: string,
        runningTime: number,
        fee: Money,
        movieType: MovieType,
        discountAmount: Money,
        discountPercent: number
    ) {
        this.discountConditions = [];
        this.title = title;
        this.fee = fee;
        this.runningTime = runningTime;
        this.movieType = movieType;
        this.discountAmount = discountAmount;
        this.discountPercent = discountPercent;
    }

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
        this.discountPercent = discountPercent;
    }
}

const enum DiscountConditionType {
    SEQUENCE,
    PERIOD,
}

class DiscountCondition {
    private type: DiscountConditionType;
    private sequence: number;
    private dayOfWeek: DayOfWeek;
    private startTime: Date;
    private endTime: Date;

    constructor(
        type: DiscountConditionType,
        sequence: number,
        dayOfWeek: DayOfWeek,
        startTime: Date,
        endTime: Date
    ) {
        this.type = type;
        this.sequence = sequence;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public getType(): DiscountConditionType {
        return this.type;
    }
    public setType(type: DiscountConditionType): void {
        this.type = type;
    }
    public getDayOfWeek(): DayOfWeek {
        return this.dayOfWeek;
    }
    public setDayOfWeek(dayOfWeek: DayOfWeek): void {
        this.dayOfWeek = dayOfWeek;
    }
    public getStartTime(): Date {
        return this.startTime;
    }
    public setStartTime(startTime: Date): void {
        this.startTime = startTime;
    }
    public getEndTime(): Date {
        return this.endTime;
    }
    public setEndTime(endTime: Date): void {
        this.endTime = endTime;
    }
    public getSequence(): number {
        return this.sequence;
    }
    public setSequence(sequence: number): void {
        this.sequence = sequence;
    }
}

class Screening {
    private movie: Movie;
    private sequence: number;
    private whenScreened: Date;

    constructor(movie: Movie, sequence: number, whenScreened: Date) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }
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

class ReservationAgency {
    public reserve(
        screening: Screening,
        customer: Customer,
        audienceCount: number
    ) {
        let movie: Movie = screening.getMovie();

        let discountable: boolean = false;
        // DiscountCondition의 루프를 돌면서 할인 가능 여부를 확인
        for (const condition of movie.getDiscountConditions()) {
            if (
                condition.getType() === DiscountConditionType.PERIOD
            ) {
                discountable =
                    screening.getWhenScreened().getDay() ===
                        condition.getDayOfWeek() &&
                    condition.getStartTime() <=
                        screening.getWhenScreened() &&
                    condition.getEndTime() >=
                        screening.getWhenScreened();
            } else {
                // 할인 조건이 순번 조건
                discountable =
                    condition.getSequence() ===
                    screening.getSequence();
            }
            if (discountable) break;
        }

        let fee: Money;

        // discountable 변수의 값을 체크, 적절한 할인 정책에 따라 예매 요금 계산
        if (discountable) {
            let discountAmount: Money;
            // 할인 정책의 타입에 따라 할인 요금을 계산하는 로직을 분기
            switch (movie.getMovieType()) {
                case MovieType.AMOUNT_DISCOUNT:
                    discountAmount = movie.getDiscountAmount();
                    break;
                case MovieType.PERCENT_DISCOUNT:
                    discountAmount = movie
                        .getFee()
                        .times(movie.getDiscountPercent());
                    break;
                case MovieType.NONE_DISCOUNT:
                    discountAmount = Money.ZERO;
                    break;
            }
            fee = movie
                .getFee()
                .minus(discountAmount)
                .times(audienceCount);
        } else {
            fee = movie.getFee();
        }
        return new Reservation(
            customer,
            screening,
            fee,
            audienceCount
        );
    }
}
