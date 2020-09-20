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

    constructor(amount: number, invitaion?: Invitation) {
        this.invitation = invitaion;
        this.amount = amount;
    }

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

    buy(ticket: Ticket): number {
        return this.bag.hold(ticket);
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

class TicketSeller {
    private ticketOffice: TicketOffice;

    constructor(ticketOffice: TicketOffice) {
        this.ticketOffice = ticketOffice;
    }

    getTicketOffice() {
        return this.ticketOffice;
    }
    sellTo(audience: Audience): void {
        this.ticketOffice.sellTicketTo(audience);
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
