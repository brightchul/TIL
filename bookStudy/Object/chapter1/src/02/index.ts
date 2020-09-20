class Invitation {
    private when?: Date;
}

class Ticket {
    private fee: number = 0;

    getFee(): number {
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

    private getTicket(): Ticket {
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
        this.ticketOffice.plusAmount(audience.buy(this.ticketOffice.getTicket()));
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
