class Invitation {
    private when?: Date;
}

class Ticket {
    private fee: number = 0;

    public getFee(): number {
        return this.fee;
    }
}

class Bag {
    private amount: number = 0;
    private invitation?: Invitation;
    private ticket?: Ticket;

    constructor(amount: number);
    constructor(amount: number, invitaion?: Invitation) {
        this.invitation = invitaion;
        this.amount = amount;
    }

    public hasInvitation(): boolean {
        return this.invitation !== undefined;
    }

    public hasTicket(): boolean {
        return this.ticket !== undefined;
    }

    public setTicket(ticket: Ticket): void {
        this.ticket = ticket;
    }
    public minusAmount(amount: number) {
        this.amount -= amount;
    }
    public plusAmount(amount: number): void {
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
        this.tickets = Array.from(tickets);
    }

    sellTicketTo(audience: Audience): void {
        this.plusAmount(audience.bug(this.getTicket()));
    }

    private getTicket(): Ticket {
        return this.tickets.splice(0, 1);
    }

    private plusAmount(amount: number): void {
        this.amount += amount;
    }
}

class TicketSeller {
    private ticketOffice: TicketOffice;

    constructor(ticketOffice: TicketOffice) {
        this.ticketOffice = ticketOffice;
    }

    sellTo(audience: Audience): void {
        this.ticketOffice.sellTicketTo(audience);
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
        if (audience.getBag().hasInvitation()) {
            const ticket: Ticket = this.ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            const ticket: Ticket = this.ticketSeller.getTicketOffice().getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            this.ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
