import { objShow } from "./util";

class Invitation {
    private when: Date;
    constructor(when: Date) {
        this.when = when;
    }
}

class Ticket {
    private fee: number;
    constructor(fee: number) {
        this.fee = fee;
    }
    getFee(): number {
        return this.fee;
    }
}

class Bag {
    private amount: number = 0;
    private invitation?: Invitation;
    private ticket?: Ticket;

    constructor(amount: number, invitaion?: Invitation) {
        this.invitation = invitaion;
        this.amount = amount;
    }

    hasInvitation(): boolean {
        return this.invitation !== undefined;
    }

    hasTicket(): boolean {
        return this.ticket !== undefined;
    }

    setTicket(ticket: Ticket): void {
        this.ticket = ticket;
    }
    minusAmount(amount: number) {
        this.amount -= amount;
    }
    plusAmount(amount: number): void {
        this.amount += amount;
    }
}

class Audience {
    private bag: Bag;

    constructor(bag: Bag) {
        this.bag = bag;
    }

    buy(ticket: Ticket): number {
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

class TicketOffice {
    private amount: number;
    private tickets: Array<Ticket>;

    constructor(amount: number, ...tickets: Array<Ticket>) {
        this.amount = amount;
        this.tickets = Array.from(tickets);
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

class TicketSeller {
    private ticketOffice: TicketOffice;

    constructor(ticketOffice: TicketOffice) {
        this.ticketOffice = ticketOffice;
    }

    getTicketOffice() {
        return this.ticketOffice;
    }
    sellTo(audience: Audience): void {
        if (this.ticketOffice.hasTicket()) {
            this.ticketOffice.plusAmount(audience.buy(this.ticketOffice.getTicket()!));
        }
    }
}

class Theater {
    private ticketSeller: TicketSeller;

    constructor(ticketSeller: TicketSeller) {
        this.ticketSeller = ticketSeller;
    }

    enter(audience: Audience): void {
        this.ticketSeller.sellTo(audience);
    }
}

export default function main() {
    const when20200920 = new Invitation(new Date("2020-09-20"));

    const ticket1 = new Ticket(10);
    const ticket2 = new Ticket(10);

    const bagWithoutInvitation = new Bag(100);
    const bagWithInvitaion = new Bag(100, when20200920);

    const audienceWithoutInvitaion = new Audience(bagWithoutInvitation);
    const audienceWithInvitaion = new Audience(bagWithInvitaion);

    objShow(audienceWithoutInvitaion);
    objShow(audienceWithInvitaion);

    const ticketOffice = new TicketOffice(100, ticket1, ticket2);
    const ticketSeller = new TicketSeller(ticketOffice);

    objShow(ticketOffice);

    const theater = new Theater(ticketSeller);

    theater.enter(audienceWithInvitaion);
    theater.enter(audienceWithoutInvitaion);

    objShow(audienceWithoutInvitaion);
    objShow(audienceWithInvitaion);

    objShow(ticketOffice);
}
