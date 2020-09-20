class Invitation {
    private when?: Date;

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
    private amount: number;
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

    getBag(): Bag {
        return this.bag;
    }
}

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

class TicketSeller {
    private ticketOffice: TicketOffice;

    constructor(ticketOffice: TicketOffice) {
        this.ticketOffice = ticketOffice;
    }

    getTicketOffice() {
        return this.ticketOffice;
    }
}

class Theater {
    private ticketSeller: TicketSeller;

    constructor(ticketSeller: TicketSeller) {
        this.ticketSeller = ticketSeller;
    }

    enter(audience: Audience): void {
        if (!this.ticketSeller.getTicketOffice().hasTicket()) return;

        const ticket: Ticket = this.ticketSeller.getTicketOffice().getTicket()!;
        if (audience.getBag().hasInvitation()) {
            audience.getBag().setTicket(ticket);
        } else {
            audience.getBag().minusAmount(ticket.getFee());
            this.ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}

function objShow(obj: object) {
    console.log(obj.constructor.name + " " + JSON.stringify(obj));
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
