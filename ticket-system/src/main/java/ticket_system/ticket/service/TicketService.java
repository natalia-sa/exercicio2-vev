package ticket_system.ticket.service;

import ticket_system.exceptions.TicketAlreadySoldException;
import ticket_system.models.Ticket;
import ticket_system.models.TicketStatus;
import ticket_system.models.TicketType;

public class TicketService {

    public Ticket create(int id, TicketType ticketType, double price) {
        return new Ticket(id, ticketType, price);
    }

    public void sellTicket(Ticket ticket) throws TicketAlreadySoldException {
        if (ticket.getTicketStatus().equals(TicketStatus.SOLD)) {
            throw new TicketAlreadySoldException("Can't sell a ticket that already sold");
        }

        ticket.sell();
    }
}